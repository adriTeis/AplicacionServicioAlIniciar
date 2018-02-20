package com.example.adrianmontes.aplicacionservicioaliniciar;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MYService myService;
    //esto me permite tener la conexion con el servicio
    private ServiceConnection serviceConnection;
    private Intent servicioIntent;
    private boolean ServiceBound;
    private TextView texto;
    BroadcastReceiver BR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Servicio Demo","MainActivity thread id:"+Thread.currentThread().getId());
        servicioIntent= new Intent(this,MYService.class);
        texto=(TextView) findViewById(R.id.textoNumero);
    }

    //enlazarme al servicio
    public void FunBind(View view) {
        //le digo si la conexion no esta creada me crea 2 metodos abstractos de si estoy conectado o desconectado
        //en el metodo conectado obtengo la instancia del servicio, guardandolo en la variable myService, casteandolo
        //a mi clase.el nombre de mi clase Ibander, y con get service me da la instancia
        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    ServiceBound=true;
                    myService=((MYService.MyServiceBinder)service).getService();
                    Log.i("Servicio Demo","paso por el Bind");
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    ServiceBound=false;
                }
            };
        }
         //de esta manera me enlazo al servicio y lo lanzo, es como el start service, pero de esta manera me enlazo para tener
        //la instancia de el y recoger los datos que devuelve el servicio
        bindService(servicioIntent,serviceConnection, Context.BIND_AUTO_CREATE);

    }

    //desbincular el servicio
    public void FunUnbind(View view) {

        if(ServiceBound){
            ServiceBound=false;
            unbindService(serviceConnection);
        }
    }

    //boton obetener el numero aleatorio de la clase myservice
    public void FunObtNum(View view) {
        if(ServiceBound){
            Log.i("Servicio Demo","Servicio enlazado");
            texto.setText(String.valueOf(myService.getmRandomNumber()));

        }else{

            Log.i("Servicio Demo","Servicio no enlazado");

        }
    }

    //Boton start service
    public void FunStart(View view) {
        startService(servicioIntent);
        //ahora le voy a decir el tipo de evento que quiero que me avise, igualo BR a la clase que extiende de
        //BroadcastReceiver

        BR= new MYBroadcastReceiver();
        //Le decimos el tipo de evento que queremos escuchar, y de ese tipo cual queremos escuchar.
        IntentFilter Filter= new IntentFilter(ConnectivityManager.EXTRA_EXTRA_INFO);
        //este filtro es para si activo el wifi o modoavion
        Filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        //ahora aqui voy a recivir todos los mensages que recivo de la clase que tengo creada que extiende de BroadcastReceiver
        //que es una clase que sirve para escuchar eventos del sistema y le aplico un filtro para poder filtrar por lo que queramos
        //Este filtro se activa cuando enciendo el telefono
        Filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(BR,Filter);
        };


    //Boton parar
    public void FunStop(View view) {
        stopService(servicioIntent);
        Log.i("Servicio Demo","Paso por el Stop");
        //aqui nos desenlazamos del escuchador
        this.unregisterReceiver(BR);

    }
}
