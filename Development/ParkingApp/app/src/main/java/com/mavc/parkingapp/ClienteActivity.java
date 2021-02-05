package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mavc.parkingapp.DAO.ClienteDAO;
import com.mavc.parkingapp.DTO.ClienteDTO;

import java.util.List;

public class ClienteActivity extends AppCompatActivity {

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText id_cliente, cedula, nombre, celular, direccion, email;
    ListView lista;
    Integer operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        id_cliente = findViewById(R.id.id_cliente);
        cedula = findViewById(R.id.cedula);
        nombre = findViewById(R.id.nombre);
        celular = findViewById(R.id.celular);
        direccion = findViewById(R.id.direccion);
        email = findViewById(R.id.email);

        listarCliente();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                id_cliente.setText("0");
                cedula.setText("");
                nombre.setText("");
                celular.setText("");
                direccion.setText("");
                email.setText("");
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarCliente();
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
                id_cliente.setText(partes[0]);
                cedula.setText(partes[1]);
                nombre.setText(partes[2]);
                celular.setText(partes[3]);
                direccion.setText(partes[4]);
                email.setText(partes[5]);
                return true;
            }
        });

    }

    public void grabarCliente(){
        ClienteDAO dao = new ClienteDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new ClienteDTO(Integer.parseInt(id_cliente.getText().toString()), cedula.getText().toString(), nombre.getText().toString(), celular.getText().toString(), direccion.getText().toString(), email.getText().toString()));
            Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_LONG).show();
        }

        if(operacion == 2){
            dao.editar(new ClienteDTO(Integer.parseInt(id_cliente.getText().toString()), cedula.getText().toString(), nombre.getText().toString(), celular.getText().toString(), direccion.getText().toString(), email.getText().toString()));
            Toast.makeText(this,"Se modifico correctamente",Toast.LENGTH_LONG).show();
        }

        if(operacion == 3){
            dao.eliminar(new ClienteDTO(Integer.parseInt(id_cliente.getText().toString()), cedula.getText().toString(), nombre.getText().toString(), celular.getText().toString(), direccion.getText().toString(), email.getText().toString()));
            Toast.makeText(this,"Se elimino correctamente",Toast.LENGTH_LONG).show();
        }


        listarCliente();
    }

    public void listarCliente(){
        ClienteDAO dao = new ClienteDAO(getApplicationContext());
        List<ClienteDTO> listaClientes = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (ClienteDTO lm: listaClientes){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}