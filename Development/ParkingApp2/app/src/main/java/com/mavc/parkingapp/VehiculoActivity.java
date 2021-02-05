package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.mavc.parkingapp.DAO.VehiculoDAO;
import com.mavc.parkingapp.DTO.VehiculoDTO;

import java.util.List;

public class VehiculoActivity extends AppCompatActivity {

    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

       // listarVehiculo();
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