package com.example.inicio_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Perfil_fragment extends Fragment {

    public static String usuario;
    public static String numero;
    public static String correo;
    private TextView txt_user;
    private TextView txt_phone;
    private TextView txt_email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);
        txt_user = vista.findViewById(R.id.txt_user);
        txt_phone = vista.findViewById(R.id.txt_phone);
        txt_email = vista.findViewById(R.id.txt_email);

        txt_user.setText(usuario);
        txt_phone.setText(numero);
        txt_email.setText(correo);

        return vista;
    }
}
