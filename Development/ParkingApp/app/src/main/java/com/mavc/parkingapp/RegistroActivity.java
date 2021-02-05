package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.mavc.parkingapp.DAO.RegistroDAO;
import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.DTO.RegistroDTO;



public class RegistroActivity extends AppCompatActivity {

    Button btnRegistro;
    EditText id_usuario, nick, nombre, email, contrasena;
    Integer operacion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegistro = findViewById(R.id.btnRegistro);

        id_usuario = findViewById(R.id.reid_usuario);
        nick = findViewById(R.id.nick);
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarRegistro();
            }
        });



    }

    public void grabarRegistro(){
        RegistroDAO dao = new RegistroDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new RegistroDTO(Integer.parseInt(id_usuario.getText().toString()), nick.getText().toString(), nombre.getText().toString(), email.getText().toString(), contrasena.getText().toString()));
            Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_LONG).show();
        }



        limpiar();

    }
    public void limpiar(){
        nick.setText("");
        nombre.setText("");
        email.setText("");
        contrasena.setText("");
    }



    public void atras(View v)
    {
        Intent i = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(i);
    }
}