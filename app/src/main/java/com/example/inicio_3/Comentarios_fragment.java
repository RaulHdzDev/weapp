package com.example.inicio_3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_SHORT;

public class Comentarios_fragment extends Fragment {

    View vista;
    Button enviar;
    EditText txtDestino, txtAsunto, txtDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_comments, container,false);
        final Fragment[] fragmentclick = {null};

        // perfil
        enviar  = (Button) vista.findViewById(R.id.btn_enviar);
        txtDestino= (EditText) vista.findViewById(R.id.txt_wecorreo);
        txtAsunto= (EditText) vista.findViewById(R.id.txt_asunto);
        txtDesc= (EditText) vista.findViewById(R.id.txt_desc);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String we = txtDestino.getText().toString().trim();
                String asun = txtAsunto.getText().toString().trim();
                String desc = txtDesc.getText().toString().trim();
                enviarCorreo(we,asun,desc);
            }
        });

        return vista;
    }

    private void enviarCorreo(String we, String asun, String desc) {

        Intent email = new Intent(Intent.ACTION_SEND);

        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");

        email.putExtra(Intent.EXTRA_EMAIL, new String[]{we});
        email.putExtra(Intent.EXTRA_SUBJECT, asun);
        email.putExtra(Intent.EXTRA_TEXT, desc);

        try {
            startActivity(Intent.createChooser(email,"Enviar con"));
        }catch (Exception e){

        }
    }

}
