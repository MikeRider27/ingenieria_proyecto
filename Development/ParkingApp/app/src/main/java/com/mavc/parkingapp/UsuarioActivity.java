package com.mavc.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.mavc.parkingapp.DAO.ZonaDAO;
import com.mavc.parkingapp.DTO.ZonaDTO;

import java.util.List;

public class UsuarioActivity extends AppCompatActivity {

    Button btnAgregar, btnEditar, btnEliminar, btnGrabar;
    EditText txtCodigo, txtNombre, txtNick, txtEmail, txtContrasena, txtestado;
    CheckBox checEstado;
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

        txtCodigo = findViewById(R.id.txt_id_usuario);
        txtNombre = findViewById(R.id.txt_nom_usuario);
        txtNick = findViewById(R.id.txt_nick);
        txtEmail = findViewById(R.id.txt_usu_email);
        txtContrasena = findViewById(R.id.txt_usu_clave);
        checEstado = findViewById(R.id.check_estado);


        listarZona();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacion = 1;
                txtCodigo.setText("0");
                txtNombre.setText("");
                txtNick.setText("");
                txtEmail.setText("");
                txtContrasena.setText("");
                checEstado.setChecked(true);
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarZona();
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

    public void grabarZona(){
        ZonaDAO dao = new ZonaDAO(getApplicationContext());
        if(operacion == 1){
            dao.agregar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        if(operacion == 2){
            dao.editar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }

        if(operacion == 3){
            dao.eliminar(new ZonaDTO(Integer.parseInt(txtCodigo.getText().toString()), txtDescri.getText().toString()));
        }


        listarZona();
    }

    public void listarZona(){
        ZonaDAO dao = new ZonaDAO(getApplicationContext());
        List<ZonaDTO> listaZonas = dao.listar();
        lista = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);
        for (ZonaDTO lm: listaZonas){
            adapter.add(lm.toString());
        }
        lista.setAdapter(adapter);
        dao.cerrarConexion();
    }
}