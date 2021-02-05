package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mavc.parkingapp.DAO.VehiculoDAO;
import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.DTO.VehiculoDTO;

public class SalidaActivity extends AppCompatActivity {
    EditText chapa, monto, tiempo_total, fecha_salida, hora_salida;
    Button btnSalir, Cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida);
        btnSalir = findViewById(R.id.btnSalir);
        Cancelar = findViewById(R.id.Cancelar);

        chapa = findViewById(R.id.chapa);
        monto = findViewById(R.id.monto);
        tiempo_total = findViewById(R.id.tiempo_total);


        fecha_salida = findViewById(R.id.fecha_salida);
        hora_salida = findViewById(R.id.hora_salida);

        fecha_salida.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy"));
        hora_salida.setText(ParametrosGlobales.completarFecha("HH:mm"));
        chapa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && chapa.getText().toString().trim() != ""){
                    getVehiculo();
                }
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                limpiar();

            }
        });

    }
    public void getVehiculo(){
      //  color.setText(vdto.getColor());
        monto.setText("50000");
        tiempo_total.setText("10");

    }
    public void limpiar(){
        chapa.setText("");
        monto.setText("");
        tiempo_total.setText("");
        Toast.makeText(this,"Se agrego la salida correctamente",Toast.LENGTH_LONG).show();

    }
}