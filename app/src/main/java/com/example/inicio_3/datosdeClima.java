package com.example.inicio_3;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class datosdeClima {

    public String Grados;
    public String Desc;
    public String Ubicacion;


    public datosdeClima(String grados, String desc, String ubicacion) {
        setGrados(grados);
        setDesc(desc);
        setUbicacion(ubicacion);
    }

    public datosdeClima() {
        Grados="";
        Desc="";
        Ubicacion="";
    }

    public String getGrados() {
        return Grados;
    }

    public void setGrados(String grados) {
        Grados = grados;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public  void save( Context ctx) {
        FileOutputStream fos;
        try {
            fos = ctx.openFileOutput("clima.txt", Context.MODE_PRIVATE);

            fos.write((Ubicacion.toString()+"\n"+Grados.toString()+"\n"+Desc.toString()).getBytes() );
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
