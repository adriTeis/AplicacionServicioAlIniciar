package com.example.adrianmontes.aplicacionservicioaliniciar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by adrian.montes on 08/02/2018.
 */

public class MYService extends Service {
    private int mRandomNumber=111;
    private boolean NumeroAleatorioActivado;

    //uso esta clase para poder comunicarme con el servicio, y recoger el valor que lanzo en el
    //con esto lo que consigo es poder hacer una instacia de esta clase, y asi poder acceder a los metodos aleatorios
    //que tiene este servicio
    public IBinder mBinder=new MyServiceBinder();
    public class MyServiceBinder extends Binder {
        public MYService getService(){

            return MYService.this;
        }

    }
    //Este metodo me devuelve la instancia del servicio que lo recojo luego desde el onServiceConnected()
    //para obtener el servicio desde la otra clase y asi acceder a los metodos de este
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Service Demo","onbind");
        return mBinder;
    }

    //En este metodo lanzo un hilo que me crea un numero aleatorio y lo muestro por pantalla.
    //Se podria hacer tambein extendiendo de asinkTask
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        NumeroAleatorioActivado=true;
        Log.i("Servicio Demo","MainActivity thread id:"+Thread.currentThread().getId());
        //ahora creo un objeto hilo anonimo que se lanza sin tener que crear una clase de el
        new Thread(new Runnable() {
            @Override
            public void run() {

               GeneradorRandom();

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void GeneradorRandom() {
        while(NumeroAleatorioActivado){
            try{
                Thread.sleep(1000);
                mRandomNumber=new Random().nextInt(100);
                Log.i("Servicio Demo","thread id:"+Thread.currentThread().getId()+"Number:"+mRandomNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
    private void PararGeneradorRamdon(){
        NumeroAleatorioActivado=false;
    }

    //en este metodo abstracto entra cuando se detiene el hilo
    @Override
    public void onDestroy() {
        super.onDestroy();
        PararGeneradorRamdon();
        Log.i("Servicio Demo","Hilo interrumpido");

    }

    public int getmRandomNumber() {
        return mRandomNumber;
    }

}
