package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mavc.parkingapp.DAO.TipobahiaDAO;
import com.mavc.parkingapp.DTO.TipobahiaDTO;

import java.util.List;

public class TipbahiaActivity extends AppCompatActivity {

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText txtCodigo, txtDescri;
    ListView lista;
    Integer operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipbahia);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        txtCodigo = findViewById(R.id.txt_id_tbahia);
        txtDescri = findViewById(R.id.txt_nom_tbahia);

        listarTipbahia();

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
                grabarTipbahia();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 2;
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 3;
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String[] partes = item.split(" - ");
                txtCodigo.setText(partes[0]);
                txtDescri.setText(partes[1]);
                return true;
            }
        });

    }

    public void grabarTipbahia(){
        TipobahiaDAO dao = new TipobahiaDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new TipobahiaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        if(operacion == 2){
            dao.editar(new TipobahiaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        if(operacion == 3){
            dao.eliminar(new TipobahiaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }


        listarTipbahia();
    }

    public void listarTipbahia(){
        TipobahiaDAO dao = new TipobahiaDAO(getApplicationContext());
        List<TipobahiaDTO> listaTbahia = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (TipobahiaDTO lm: listaTbahia){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}