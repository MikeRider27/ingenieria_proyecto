package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mavc.parkingapp.DAO.VehiculoDAO;
import com.mavc.parkingapp.DTO.VehiculoDTO;

import java.util.Calendar;

public class EntradaActivity extends AppCompatActivity {
    Spinner cboBahia,cboMarca, cboTipo;
    EditText chapa, color, nombre, cedula, celular, email, direccion, fechaEntrada, horaEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        chapa = findViewById(R.id.chapa);
        cboBahia = findViewById(R.id.cbo_id_bahia);
        cboMarca = findViewById(R.id.cbo_id_marca);
        cboTipo = findViewById(R.id.cbo_id_tipovehiculo);
        color = findViewById(R.id.color);
        nombre = findViewById(R.id.nombre);
        cedula = findViewById(R.id.cedula);
        celular = findViewById(R.id.celular);
        direccion = findViewById(R.id.direccion);
        email = findViewById(R.id.email);
        fechaEntrada = findViewById(R.id.fecha_entrada);
        horaEntrada = findViewById(R.id.hora_entrada);

        fechaEntrada.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy"));
        horaEntrada.setText(ParametrosGlobales.completarFecha("HH:mm"));

        chapa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && chapa.getText().toString().trim() != ""){
                    getVehiculo();
                }
            }
        });

    }


    public void getVehiculo(){
        VehiculoDAO vdao = new VehiculoDAO(getApplicationContext());
        VehiculoDTO vdto = vdao.buscarID(new VehiculoDTO(chapa.getText().toString()));
        color.setText(vdto.getColor());
        nombre.setText(vdto.getCliente().getNombre());
        cedula.setText(vdto.getCliente().getCedula());
        celular.setText(vdto.getCliente().getCelular());
        direccion.setText(vdto.getCliente().getDireccion());
        email.setText(vdto.getCliente().getEmail());

    }




}