package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;


import com.mavc.parkingapp.DAO.ClienteDAO;
import com.mavc.parkingapp.DAO.MarcasDAO;
import com.mavc.parkingapp.DAO.TipovehiculoDAO;


import com.mavc.parkingapp.DAO.VehiculoDAO;
import com.mavc.parkingapp.DTO.ClienteDTO;
import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.DTO.TipovehiculoDTO;
import com.mavc.parkingapp.DTO.VehiculoDTO;

import java.util.List;

public class VehiculoActivity extends AppCompatActivity {

    Spinner cboMarca, cboTipo, cboCliente;
    MarcasDAO marcasDAO;
    MarcasDTO marcasDTO;

    TipovehiculoDAO tipovehiculoDAO;
    TipovehiculoDTO tipovehiculoDTO;

    ClienteDAO clienteDAO;
    ClienteDTO clienteDTO;

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText chapa, color;
    ListView lista;
    Integer operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        cboMarca = findViewById(R.id.cboMarca);
        cboTipo = findViewById(R.id.cboTipo);
        cboCliente = findViewById(R.id.cboCliente);

        cargarMarcas();
        cargarTipos();
        cargarClientes();

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        chapa = findViewById(R.id.chapa);
        color = findViewById(R.id.color);

        listarVehiculo();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                chapa.setText("");
                color.setText("");

            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarVehiculo();
            }
        });
    }


    public void cargarMarcas(){
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
    public void cargarClientes(){
        clienteDAO = new ClienteDAO(getApplicationContext());
        ArrayAdapter<ClienteDTO> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, clienteDAO.listar());
        cboCliente.setAdapter(adapter);
    }
    public void grabarVehiculo(){
        VehiculoDAO dao = new VehiculoDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new VehiculoDTO(chapa.getText().toString(), color.getText().toString(), (TipovehiculoDTO)cboTipo.getSelectedItem(), (MarcasDTO)cboMarca.getSelectedItem(), (ClienteDTO)cboCliente.getSelectedItem()));
        }
/*
        if(operacion == 2){
            dao.editar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }
        if(operacion == 3){
            dao.eliminar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }
        listarZona();*/
        listarVehiculo();
    }
    public void listarVehiculo(){
        VehiculoDAO dao = new VehiculoDAO(getApplicationContext());
        List<VehiculoDTO> listarVehiculos = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (VehiculoDTO lm: listarVehiculos){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}