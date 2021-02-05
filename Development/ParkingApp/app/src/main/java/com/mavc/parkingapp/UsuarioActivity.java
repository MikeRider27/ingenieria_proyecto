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
import android.widget.CheckBox;


import com.mavc.parkingapp.DAO.UsuarioDAO;
import com.mavc.parkingapp.DTO.UsuarioDTO;

import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText id_usuario, nick, nombre, email, contrasena;
    CheckBox estado;
    ListView lista;
    Integer operacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnGrabar = findViewById(R.id.btnGrabar);

        id_usuario = findViewById(R.id.id_usuario);
        nick = findViewById(R.id.nick);
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);
        estado = findViewById(R.id.check_estado);

        listarUsuario();


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                id_usuario.setText("0");
                nick.setText("");
                nombre.setText("");
                email.setText("");
                contrasena.setText("");

            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarUsuario();
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
                id_usuario.setText(partes[0]);
                nick.setText(partes[1]);
                nombre.setText(partes[2]);
                email.setText(partes[3]);
                contrasena.setText(partes[4]);
                estado.setText(partes[5]);
                return true;
            }
        });

    }

    public void grabarUsuario(){
        UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new UsuarioDTO(Integer.parseInt(id_usuario.getText().toString()), nick.getText().toString(), nombre.getText().toString(), email.getText().toString(), contrasena.getText().toString(), estado.getText().toString()));
            Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_LONG).show();
        }

        if(operacion == 2){
            dao.editar(new UsuarioDTO(Integer.parseInt(id_usuario.getText().toString()), nick.getText().toString(), nombre.getText().toString(), email.getText().toString(), contrasena.getText().toString(), estado.getText().toString()));

            Toast.makeText(this,"Se modifico correctamente",Toast.LENGTH_LONG).show();
        }

        if(operacion == 3){
            dao.eliminar(new UsuarioDTO(Integer.parseInt(id_usuario.getText().toString()), nick.getText().toString(), nombre.getText().toString(), email.getText().toString(), contrasena.getText().toString(), estado.getText().toString()));
            Toast.makeText(this,"Se elimino correctamente",Toast.LENGTH_LONG).show();
        }


        listarUsuario();
    }

    public void listarUsuario(){
        UsuarioDAO dao = new UsuarioDAO(getApplicationContext());
        List<UsuarioDTO> listarUsuarios = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (UsuarioDTO lm: listarUsuarios){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}