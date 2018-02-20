package com.example.adrianmontes.aplicacionservicioaliniciar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by adrian.montes on 15/02/2018.
 */

    //Esta clase sirve para escuchar eventos del sistema, ya sea cuando se inicie android o como el propio whatsapp cuando
    //le llega un mensage, o cuando activas el wifi o el modo avion, crear una variable BroadcastReceiver en la otra clase
    //igualas esa variable a esta calse y le aplicas un filtro para decirle que quieres escuchar, esta en el metodo FunStart de Main
    //<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>

//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
//<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"></uses-permission>
//<uses-permission android:name="android.permission.INTERNET"></uses-permission>
//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>       estas lineas van en el manifest

    //En el intent.getAction nos devuelve un mensage con el evento que se produjo, ya que tenemos 2 filtros en la otra clase
public class MYBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //ahora le digo que si el intento esta lleno y que si la accion es igual a BOOT osea el inicio me lance el servicio
        //MYService que extiende de servicio y me crea numeros aleatorios
        if(intent!=null){
            if(intent.getAction().contains("BOOT_COMPLETED")){
                Intent service=new Intent(context,MYService.class);
                context.startService(service);
            }
        }
    }
}
