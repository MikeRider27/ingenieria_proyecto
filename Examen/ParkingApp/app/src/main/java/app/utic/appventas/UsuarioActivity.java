package app.utic.appventas;

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
import com.example.appventas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioActivity extends AppCompatActivity {


    static String HOST = LoginActivity.HOST;
    String TABLA = "api/";
    String ENLACE = HOST+TABLA;
    RequestQueue request, requestQueue;
    private EditText et_codigo, et_nombre,et_email, et_usuario, et_clave;
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
        setContentView(R.layout.activity_usuario);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        et_email = (EditText)findViewById(R.id.txt_email);
        et_usuario = (EditText)findViewById(R.id.txtusu);
        et_clave = (EditText)findViewById(R.id.txtpass);
        lista = (ListView)findViewById(R.id.listaempleado);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnModificar = (Button)findViewById(R.id.btnModificar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        buscar = (Button)findViewById(R.id.bt_buscar);
        cargaLista();
        et_codigo.setEnabled(false);
        et_nombre.requestFocus();

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
        String nombre = et_nombre.getText().toString();
        String email = et_email.getText().toString();
        String usuario = et_usuario.getText().toString();
        String clave = et_clave.getText().toString();

        if(nombre.isEmpty() || email.isEmpty() || usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_nombre.requestFocus();
        } else {
            String URL = ENLACE+"usuario.php?accion=insert&nombre="+et_nombre.getText().toString()+
                                    "&email="+et_email.getText().toString()+
                                    "&nick="+et_usuario.getText().toString()+
                                    "&pass="+et_clave.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("status").equals("success")){
                            String message = parentObject.getString("message");
                            et_codigo.setText("");
                            et_nombre.setText("");
                            et_email.setText("");
                            et_usuario.setText("");
                            et_clave.setText("");
                            Toast.makeText(UsuarioActivity.this, message, Toast.LENGTH_SHORT).show();
                            cargaLista();

                        }else {
                            String message = parentObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioActivity.this);
        builder.setMessage("¿Desea Eliminar este registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    String URL = ENLACE+"usuario.php?accion=delete&id_usuario="+et_codigo.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                                if (parentObject.getString("status").equals("success")){
                                    String message = parentObject.getString("message");
                                    et_codigo.setText("");
                                    et_nombre.setText("");
                                    et_email.setText("");
                                    et_usuario.setText("");
                                    et_clave.setText("");
                                    Toast.makeText(UsuarioActivity.this, message, Toast.LENGTH_SHORT).show();
                                    cargaLista();

                                } else {
                                    String message = parentObject.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                    requestQueue = Volley.newRequestQueue(UsuarioActivity.this);
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
        final String URL = ENLACE+"usuario.php?accion=update&id_usuario="+et_codigo.getText().toString()+
                                            "&nombre="+et_nombre.getText().toString()+
                                            "&email="+et_email.getText().toString()+
                                            "&nick="+et_usuario.getText().toString()+
                                            "&pass="+et_clave.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String message = parentObject.getString("message");
                        et_codigo.setText("");
                        et_nombre.setText("");
                        et_email.setText("");
                        et_usuario.setText("");
                        et_clave.setText("");
                        Toast.makeText(UsuarioActivity.this, message, Toast.LENGTH_SHORT).show();
                        cargaLista();

                    } else {
                        String message = parentObject.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UsuarioActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"usuario.php?accion=select";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_usuario");
                        String nombre = jsonObject.getString("nombre");
                        String email = jsonObject.getString("email");
                        String usuario = jsonObject.getString("nick");

                        adapter.add(codigo+" - "+nombre+" "+email+" - "+usuario);

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
        String URL = ENLACE+"usuario.php?accion=search&id_usuario="+et_codigo.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String nom = parentObject.getString("nombre");
                        String email = parentObject.getString("email");
                        String usu = parentObject.getString("nick");
                        String pas = parentObject.getString("pass");
                        et_nombre.setText(nom);
                        et_email.setText(email);
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

                    } else {
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




    public void CancelarBoton(View view) {
        et_codigo.setText("");
        et_nombre.setText("");
        et_email.setText("");
        et_usuario.setText("");
        et_clave.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
    }

    public void Cancelar() {
        et_codigo.setText("");
        et_nombre.setText("");
        et_usuario.setText("");
        et_clave.setText("");
        btnRegistrar.setEnabled(true);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
    }


}
