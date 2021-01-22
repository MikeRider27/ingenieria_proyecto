package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mavc.parkingapp.DAO.TipovehiculoDAO;
import com.mavc.parkingapp.DTO.TipovehiculoDTO;

import java.util.List;

public class TipovehiculoActivity extends AppCompatActivity {

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText id_tipvehiculo,nom_tipvehiculo,tarifa_hora,tarifa_dia;
    ListView lista;
    Integer operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipovehiculo);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        id_tipvehiculo = findViewById(R.id.id_tipvehiculo);
        nom_tipvehiculo = findViewById(R.id.nom_tipvehiculo);
        tarifa_hora = findViewById(R.id.tarifa_hora);
        tarifa_dia = findViewById(R.id.tarifa_dia);

        listarTipovehiculo();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                id_tipvehiculo.setText("0");
                nom_tipvehiculo.setText("");
                tarifa_hora.setText("");
                tarifa_dia.setText("");
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarTipovehiculo();
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
                id_tipvehiculo.setText(partes[0]);
                nom_tipvehiculo.setText(partes[1]);
                tarifa_hora.setText(partes[2]);
                tarifa_dia.setText(partes[3]);
                return true;
            }
        });

    }

    public void grabarTipovehiculo(){
        TipovehiculoDAO dao = new TipovehiculoDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new TipovehiculoDTO(Integer.parseInt(id_tipvehiculo.getText().toString()), nom_tipvehiculo.getText().toString(), tarifa_hora.getText().toString(), tarifa_dia.getText().toString()));
        }

        if(operacion == 2){
            dao.editar(new TipovehiculoDTO(Integer.parseInt(id_tipvehiculo.getText().toString()), nom_tipvehiculo.getText().toString(), tarifa_hora.getText().toString(), tarifa_dia.getText().toString()));
        }

        if(operacion == 3){
            dao.eliminar(new TipovehiculoDTO(Integer.parseInt(id_tipvehiculo.getText().toString()), nom_tipvehiculo.getText().toString(), tarifa_hora.getText().toString(), tarifa_dia.getText().toString()));
        }


        listarTipovehiculo();
    }

    public void listarTipovehiculo(){
        TipovehiculoDAO dao = new TipovehiculoDAO(getApplicationContext());
        List<TipovehiculoDTO> listaTipovehiculos = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (TipovehiculoDTO lm: listaTipovehiculos){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}