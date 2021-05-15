package app.utic.parkingapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EmpleadosActivity extends AppCompatActivity {


    static String HOST = LoginActivity.HOST;
    String TABLA = "empleado/";
    String ENLACE = HOST+TABLA;
    RequestQueue request, requestQueue;
    private EditText et_codigo, et_cedula, et_nombre,
            et_apellido, et_salario, et_telefono, et_usuario, et_clave;
    private ListView lista;
    private Integer idEliminar;
    private Cursor fila;
    private Button btnRegistrar, btnModificar, btnEliminar;
    private Button buscar;

    @Override
    public void onBackPressed (){
        finish();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_cedula = (EditText)findViewById(R.id.txt_cedula);
        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        et_apellido = (EditText)findViewById(R.id.txt_apellido);
        et_salario = (EditText)findViewById(R.id.txt_salario);
        et_telefono = (EditText)findViewById(R.id.txt_telefono);
        et_usuario = (EditText)findViewById(R.id.txtusu);
        et_clave = (EditText)findViewById(R.id.txtpass);
        lista = (ListView)findViewById(R.id.listaempleado);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        buscar = (Button)findViewById(R.id.bt_buscar);
        cargaLista();
        et_codigo.setEnabled(false);
        et_cedula.requestFocus();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) lista.getItemAtPosition(i);

                idEliminar = Integer.parseInt(listItem.split(" - ")[0]);

                et_codigo.setText(idEliminar.toString());
                Recuperar();
            }
        });
    }

    public void Registrar(View view){
        String cedula = et_cedula.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String salario = et_salario.getText().toString();
        String telefono = et_telefono.getText().toString();
        String usuario = et_usuario.getText().toString();
        String clave = et_clave.getText().toString();

        if(cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || salario.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_cedula.requestFocus();
        } else {
            String URL = ENLACE+"insert.php?cedula="+et_cedula.getText().toString()+
                                "&nombre="+et_nombre.getText().toString()+
                                "&apellido="+et_apellido.getText().toString()+
                                "&salario="+et_salario.getText().toString()+
                                "&telefono="+et_telefono.getText().toString()+
                                "&usuario="+et_usuario.getText().toString()+
                                "&clave="+et_clave.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("CREATE").equals("OK")){
                            String cod = parentObject.getString("ID");
                            et_codigo.setText("");
                            et_cedula.setText("");
                            et_nombre.setText("");
                            et_apellido.setText("");
                            et_salario.setText("");
                            et_telefono.setText("");
                            et_usuario.setText("");
                            et_clave.setText("");
                            Toast.makeText(EmpleadosActivity.this, "Registro numero "+cod+" insertado", Toast.LENGTH_SHORT).show();
                            cargaLista();

                        } else if (parentObject.getString("CREATE").equals("EXISTE")){
                            Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Probable registro existente", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }


    public void Eliminar(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(EmpleadosActivity.this);
        builder.setMessage("¿Desea Eliminar este registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    String URL = ENLACE+"delete.php?codigo="+et_codigo.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                                if (parentObject.getString("CREATE").equals("OK")){
                                    et_codigo.setText("");
                                    et_cedula.setText("");
                                    et_nombre.setText("");
                                    et_apellido.setText("");
                                    et_salario.setText("");
                                    et_telefono.setText("");
                                    et_usuario.setText("");
                                    et_clave.setText("");
                                    Toast.makeText(EmpleadosActivity.this, "Registro eliminado con exito!!!", Toast.LENGTH_SHORT).show();
                                    cargaLista();

                                } else if (parentObject.getString("operacion").equals("unico")){
                                    Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "ERROR AL INSERTAR", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue = Volley.newRequestQueue(EmpleadosActivity.this);
                    requestQueue.add(stringRequest);
                    Cancelar();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Cancelar();
                    break;
            }
        }
    };





    public void Modificar(View view){
        final String URL = ENLACE+"update.php?codigo="+et_codigo.getText().toString()+
                                            "&cedula="+et_cedula.getText().toString()+
                                            "&nombre="+et_nombre.getText().toString()+
                                            "&apellido="+et_apellido.getText().toString()+
                                            "&salario="+et_salario.getText().toString()+
                                            "&telefono="+et_telefono.getText().toString()+
                                            "&usuario="+et_usuario.getText().toString()+
                                            "&clave="+et_clave.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){
                        et_codigo.setText("");
                        et_cedula.setText("");
                        et_nombre.setText("");
                        et_apellido.setText("");
                        et_salario.setText("");
                        et_telefono.setText("");
                        et_usuario.setText("");
                        et_clave.setText("");
                        Toast.makeText(EmpleadosActivity.this, "Registro modificado con exito!!!", Toast.LENGTH_SHORT).show();
                        cargaLista();

                    } else if (parentObject.getString("CREATE").equals("EXISTE")){
                        Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "ERROR AL INSERTAR", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void cargaLista(){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmpleadosActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"select.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("cod");
                        String nombre = jsonObject.getString("nom");
                        String apellido = jsonObject.getString("ape");
                        String usuario = jsonObject.getString("usu");

                        adapter.add(codigo+" - "+nombre+" "+apellido+" - "+usuario);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }lista.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void Recuperar(){
        String URL = ENLACE+"search.php?codigo="+et_codigo.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){
                        //int descri = parentObject.getInt("des");
                        String ci = parentObject.getString("ci");
                        String nom = parentObject.getString("nom");
                        String ape = parentObject.getString("ape");
                        String sal = parentObject.getString("sal");
                        String tel = parentObject.getString("tel");
                        String usu = parentObject.getString("usu");
                        String pas = parentObject.getString("pas");
                        et_cedula.setText(ci);
                        et_nombre.setText(nom);
                        et_apellido.setText(ape);
                        et_salario.setText(sal);
                        et_telefono.setText(tel);
                        et_usuario.setText(usu);
                        et_clave.setText(pas);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();
                        cargaLista();
                        btnRegistrar.setEnabled(false);
                        btnEliminar.setEnabled(true);
                        btnModificar.setEnabled(true);
                        et_nombre.requestFocus();
                        et_nombre.setSelection(et_nombre.getText().toString().length());

                    } else if (parentObject.getString("operacion").equals("unico")){
                        Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "No Existe", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "No Existe", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula = et_cedula.getText().toString();

        if(!cedula.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("select emp_codigo, emp_nombre, emp_apellido, emp_salario, emp_telefono, emp_usuario, emp_clave from empleado where emp_ci=" + cedula, null);

            if(((Cursor) fila).moveToFirst()){
                et_codigo.setText(fila.getString(0));
                et_nombre.setText(fila.getString(1));
                et_apellido.setText(fila.getString(2));
                et_salario.setText(fila.getString(3));
                et_telefono.setText(fila.getString(4));
                et_usuario.setText(fila.getString(5));
                et_clave.setText(fila.getString(6));
                btnRegistrar.setEnabled(false);
                et_nombre.requestFocus();
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe El Empleado", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Ingrese el Numero de Documento", Toast.LENGTH_LONG).show();
        }
    }


    public void CancelarBoton(View view) {
        et_codigo.setText("");
        et_cedula.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_salario.setText("");
        et_telefono.setText("");
        et_usuario.setText("");
        et_clave.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
    }

    public void Cancelar() {
        et_codigo.setText("");
        et_cedula.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_salario.setText("");
        et_telefono.setText("");
        et_usuario.setText("");
        et_clave.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
    }


}
