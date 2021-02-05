package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mavc.parkingapp.DAO.BahiaDAO;
import com.mavc.parkingapp.DAO.MarcasDAO;
import com.mavc.parkingapp.DAO.TipobahiaDAO;
import com.mavc.parkingapp.DAO.TipovehiculoDAO;
import com.mavc.parkingapp.DAO.VehiculoDAO;
import com.mavc.parkingapp.DAO.ZonaDAO;
import com.mavc.parkingapp.DTO.BahiaDTO;
import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.DTO.TipobahiaDTO;
import com.mavc.parkingapp.DTO.TipovehiculoDTO;
import com.mavc.parkingapp.DTO.VehiculoDTO;
import com.mavc.parkingapp.DTO.ZonaDTO;

import java.util.Calendar;

public class EntradaActivity extends AppCompatActivity {
    Spinner cboBahia,cboMarca, cboTipo;
    MarcasDAO marcasDAO;
    MarcasDTO marcasDTO;

    TipovehiculoDAO tipovehiculoDAO;
    TipovehiculoDTO tipovehiculoDTO;

    BahiaDAO bahiaDAO;
    BahiaDTO bahiaDTO;
    EditText chapa, color, nombre, cedula, celular, email, direccion, fechaEntrada, horaEntrada,observacion;
    Button btnEntrar, Cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);
        btnEntrar = findViewById(R.id.btnEntrar);
        Cancelar = findViewById(R.id.Cancelar);
        cboBahia = findViewById(R.id.cbo_id_bahia);
        cboMarca = findViewById(R.id.cbo_id_marca);
        cboTipo = findViewById(R.id.cbo_id_tipovehiculo);

        cargarMarca();
        cargarTipos();
        cargarBahia();

        chapa = findViewById(R.id.chapa);

        color = findViewById(R.id.color);
        nombre = findViewById(R.id.nombre);
        cedula = findViewById(R.id.cedula);
        celular = findViewById(R.id.celular);
        direccion = findViewById(R.id.direccion);
        email = findViewById(R.id.email);
        fechaEntrada = findViewById(R.id.fecha_entrada);
        horaEntrada = findViewById(R.id.hora_entrada);
        observacion = findViewById(R.id.observacion);

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
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                limpiar();

            }
        });

    }
    public void cargarMarca(){
        marcasDAO = new MarcasDAO(getApplicationContext());
        ArrayAdapter<MarcasDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, marcasDAO.listar());
        cboMarca.setAdapter(adapter);

        //zonaDTO = (ZonaDTO) parent.getSelectedItem();
        //Toast.makeText(getApplicationContext(), "ZONA SELECCIONADA: "+zonaDTO.getNom_zona(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "CODIGO SELECCIONADO: "+zonaDTO.getId_zona(), Toast.LENGTH_LONG).show();

    }

    public void cargarTipos(){
        tipovehiculoDAO = new TipovehiculoDAO(getApplicationContext());
        ArrayAdapter<TipovehiculoDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tipovehiculoDAO.listar());
        cboTipo.setAdapter(adapter);
    }
    public void cargarBahia(){
        bahiaDAO = new BahiaDAO(getApplicationContext());
        ArrayAdapter<BahiaDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, bahiaDAO.listar());
        cboBahia.setAdapter(adapter);
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
    public void limpiar(){
        chapa.setText("");
        color.setText("");
        nombre.setText("");
        cedula.setText("");
        celular.setText("");
        direccion.setText("");
        email.setText("");
        observacion.setText("");
        Toast.makeText(this,"Se agrego la entrada correctamente",Toast.LENGTH_LONG).show();

    }




}