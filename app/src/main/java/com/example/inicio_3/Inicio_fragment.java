package com.example.inicio_3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static androidx.core.content.ContextCompat.getSystemService;


public class Inicio_fragment extends Fragment {


    View vista;
    Button btnPerfil;
    Button btnHelp;

    //clima
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    public String grad,city,ubi;

    int MY_PERMISSION = 0;

    public datosdeClima datosdeClima = null;

    public void leer(){
        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    getContext().openFileInput("clima.txt")));
            txtCity.setText(fin.readLine());
            txtCelsius.setText(fin.readLine());
            txtDescription.setText(fin.readLine());
            fin.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }


    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        final Fragment[] fragmentclick = {null};
        vista = inflater.inflate(R.layout.fragment_home, container, false);


        // perfil

        btnHelp = (Button) vista.findViewById(R.id.btn_help);


        //clima/////////////////////////////////////
        //Control
        txtCity = (TextView) vista.findViewById(R.id.txtcity);
        txtDescription = (TextView) vista.findViewById(R.id.txtDescription);
        txtCelsius = (TextView) vista.findViewById(R.id.txtCelsius);
        imageView = (ImageView) vista.findViewById(R.id.imageView);

        leer();

        //txtDescription.setText(String.valueOf(datosdeClima.getDesc()));
        //txtCelsius.setText(String.valueOf(datosdeClima.getGrados()));

        /*btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentclick[0] = new Perfil_fragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.transition_der_izq, R.anim.exit_der_izq,
                                R.anim.transition_izq_der, R.anim.exit_izq_der)
                        .replace(R.id.fragment_container, fragmentclick[0])
                        .addToBackStack(null)
                        .commit();
            }
        });*/
        // comentarios
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentclick[0] = new Comentarios_fragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.transition_der_izq, R.anim.exit_der_izq,
                                R.anim.transition_izq_der, R.anim.exit_izq_der)
                        .replace(R.id.fragment_container, fragmentclick[0])
                        .addToBackStack(null)
                        .commit();
            }
        });



        return vista;


    }

}

