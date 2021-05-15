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

public class ClientesActivity extends AppCompatActivity {

    static String HOST = LoginActivity.HOST;
    String TABLA = "cliente/";
    String ENLACE = HOST+TABLA;

    private EditText et_codigo, et_cedula, et_nombre,
            et_apellido, et_ruc, et_telefono;
    private ListView lista;
    private Integer idEliminar;
    private Cursor fila;
    private Button btnRegistrar, btnModificar, btnEliminar;
    RequestQueue request, requestQueue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_cedula = (EditText)findViewById(R.id.txt_cedula);
        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        et_apellido = (EditText)findViewById(R.id.txt_apellido);
        et_ruc = (EditText)findViewById(R.id.txt_ruc);
        et_telefono = (EditText)findViewById(R.id.txt_telefono);

        lista = (ListView)findViewById(R.id.listaempleado);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
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

    @Override
    public void onBackPressed (){
        finish();
    }

    public void Registrar(View view){
        String cedula = et_cedula.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String ruc = et_ruc.getText().toString();
        String telefono = et_telefono.getText().toString();

        if(cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || ruc.isEmpty() || telefono.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_cedula.requestFocus();
        } else {
            String URL = ENLACE+"insert.php?cedula="+et_cedula.getText().toString()+
                    "&nombre="+et_nombre.getText().toString()+
                    "&apellido="+et_apellido.getText().toString()+
                    "&ruc="+et_ruc.getText().toString()+
                    "&telefono="+et_telefono.getText().toString();
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
                            et_ruc.setText("");
                            et_telefono.setText("");

                            Toast.makeText(ClientesActivity.this, "Registro numero "+cod+" insertado", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ClientesActivity.this);
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
                                    et_ruc.setText("");
                                    et_telefono.setText("");
                                    Toast.makeText(ClientesActivity.this, "Registro eliminado con exito!!!", Toast.LENGTH_SHORT).show();
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
                    requestQueue = Volley.newRequestQueue(ClientesActivity.this);
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
        String codigo = et_codigo.getText().toString();
        String cedula = et_cedula.getText().toString();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String ruc = et_ruc.getText().toString();
        String telefono = et_telefono.getText().toString();


        if(cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || ruc.isEmpty() || telefono.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_cedula.requestFocus();
        } else {

            final String URL = ENLACE+"update.php?codigo="+et_codigo.getText().toString()+
                                                "&cedula="+et_cedula.getText().toString()+
                                                "&nombre="+et_nombre.getText().toString()+
                                                "&apellido="+et_apellido.getText().toString()+
                                                "&ruc="+et_ruc.getText().toString()+
                                                "&telefono="+et_telefono.getText().toString();
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
                            et_ruc.setText("");
                            et_telefono.setText("");
                            Toast.makeText(getApplicationContext(), "Registro modificado con exito!!!", Toast.LENGTH_SHORT).show();
                            cargaLista();
                            Cancelar();
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
    }

    public void Cancelar(){
        et_codigo.setText("");
        et_cedula.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_ruc.setText("");
        et_telefono.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        et_cedula.requestFocus();
    }

    public void CancelarBoton(View view){
        et_codigo.setText("");
        et_cedula.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_ruc.setText("");
        et_telefono.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        et_cedula.requestFocus();
    }

    private void cargaLista(){

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClientesActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"select.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("cod");
                        String cedula = jsonObject.getString("ced");
                        String nombre = jsonObject.getString("nom");
                        String apellido = jsonObject.getString("ape");
                        String ruc = jsonObject.getString("ruc");
                        String telefono = jsonObject.getString("tel");

                        adapter.add(codigo+" - "+cedula+" - "+nombre+" - "+apellido+" - "+ruc+" - "+telefono);

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
                        String cedula = parentObject.getString("ced");
                        String nombre = parentObject.getString("nom");
                        String apellido = parentObject.getString("ape");
                        String ruc = parentObject.getString("ruc");
                        String telefono = parentObject.getString("tel");
                        et_cedula.setText(cedula);
                        et_nombre.setText(nombre);
                        et_apellido.setText(apellido);
                        et_ruc.setText(ruc);
                        et_telefono.setText(telefono);
                        cargaLista();
                        btnRegistrar.setEnabled(false);
                        btnEliminar.setEnabled(true);
                        btnModificar.setEnabled(true);
                        et_cedula.requestFocus();
                        et_cedula.setSelection(et_cedula.getText().toString().length());


                    } else if (parentObject.getString("CREATE").equals("EXISTE")){
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
                    ("select cli_codigo, cli_nombre, cli_apellido, cli_ruc, cli_telefono from cliente where cli_ci=" + cedula, null);

            if(((Cursor) fila).moveToFirst()){
                et_codigo.setText(fila.getString(0));
                et_nombre.setText(fila.getString(1));
                et_apellido.setText(fila.getString(2));
                et_ruc.setText(fila.getString(3));
                et_telefono.setText(fila.getString(4));

                btnRegistrar.setEnabled(false);
                et_nombre.requestFocus();
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe El Cliente", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Ingrese el Numero de Documento", Toast.LENGTH_LONG).show();
        }
    }
}
