package com.mavc.parkingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mavc.parkingapp.DAO.BahiaDAO;
import com.mavc.parkingapp.DAO.TipobahiaDAO;
import com.mavc.parkingapp.DAO.ZonaDAO;
import com.mavc.parkingapp.DTO.BahiaDTO;
import com.mavc.parkingapp.DTO.TipobahiaDTO;
import com.mavc.parkingapp.DTO.ZonaDTO;

import java.util.List;


public class BahiaActivity extends AppCompatActivity {

    Spinner cboZona, cboTipo;
    ZonaDAO zonaDAO;
    ZonaDTO zonaDTO;

    TipobahiaDAO tipobahiaDAO;
    TipobahiaDTO tipobahiaDTO;

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText txtCodigo, txtDescri;
    ListView lista;
    Integer operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bahia);

        cboZona = findViewById(R.id.cbo_id_zona);
        cboTipo = findViewById(R.id.cbo_id_tipo_bahia);

        cargarZonas();
        cargarTipos();

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        txtCodigo = findViewById(R.id.txt_id_bahia);
        txtDescri = findViewById(R.id.txt_nom_bahia);

        listarBahias();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                txtCodigo.setText("0");
                txtDescri.setText("");
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarBahias();
            }
        });

    }

    public void cargarZonas(){
        zonaDAO = new ZonaDAO(getApplicationContext());
        ArrayAdapter<ZonaDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, zonaDAO.listar());
        cboZona.setAdapter(adapter);

        //zonaDTO = (ZonaDTO) parent.getSelectedItem();
        //Toast.makeText(getApplicationContext(), "ZONA SELECCIONADA: "+zonaDTO.getNom_zona(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "CODIGO SELECCIONADO: "+zonaDTO.getId_zona(), Toast.LENGTH_LONG).show();

    }

    public void cargarTipos(){
        tipobahiaDAO = new TipobahiaDAO(getApplicationContext());
        ArrayAdapter<TipobahiaDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tipobahiaDAO.listar());
        cboTipo.setAdapter(adapter);
    }

    public void grabarBahias(){
        BahiaDAO dao = new BahiaDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new BahiaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString(), (TipobahiaDTO)cboTipo.getSelectedItem(), (ZonaDTO)cboZona.getSelectedItem()));
            Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_LONG).show();
        }
/*
        if(operacion == 2){
            dao.editar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        if(operacion == 3){
            dao.eliminar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        listarZona();*/
        listarBahias();
    }

    public void listarBahias(){
        BahiaDAO dao = new BahiaDAO(getApplicationContext());
        List<BahiaDTO> listaBahias = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (BahiaDTO lm: listaBahias){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }


}