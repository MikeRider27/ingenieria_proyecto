package app.utic.appventas;

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
import com.example.appventas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class SalidaActivity extends AppCompatActivity {

    private String usuario; //se declara variable a recibir usuario del login
    static String HOST = LoginActivity.HOST;
    String TABLA = "api/";
    String ENLACE = HOST+TABLA;

    EditText et_codigo, et_descripcion, et_codtipob, et_descritipob, et_codzona, et_descrizona, fechaEntrada, horaEntrada, observacion;

    ListView lista, listatipob, listazona;

    Integer idEliminar, idTipoB, idZona;

    Button registrar, buscar, eliminar, modificar;
    RequestQueue request, requestQueue;

    @Override
    public void onBackPressed (){
                finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_codtipob = (EditText) findViewById(R.id.txt_codtipoBa);
        et_descritipob = (EditText) findViewById(R.id.txt_descritipob);
        et_codzona = (EditText) findViewById(R.id.txt_codzona);
        et_descrizona = (EditText) findViewById(R.id.txt_descrizona);
        fechaEntrada = findViewById(R.id.txt_fecha);


        fechaEntrada.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy HH:mm"));



        lista = (ListView) findViewById(R.id.listaBahia);
        listatipob = (ListView) findViewById(R.id.listaTipoBa);
        listazona = (ListView) findViewById(R.id.listaZonas);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codigo.setEnabled(false);
//        et_codtipob.setEnabled(false);
        et_descritipob.setEnabled(false);
        et_codzona.setEnabled(false);
        et_descrizona.setEnabled(false);
        Cancelar();
        cargaLista();
        cargaTipoBA();
        cargaZona();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) lista.getItemAtPosition(i);

                idEliminar = Integer.parseInt(listItem.split(" - ")[0]);

                et_codigo.setText(idEliminar.toString());
                Recuperar();
            }
        });

        listatipob.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listatipob.getItemAtPosition(i);

                idTipoB = Integer.parseInt(listItem.split(" - ")[0]);

                et_codtipob.setText(idTipoB.toString());
                RecuperarTipoB();
            }
        });
        listazona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listazona.getItemAtPosition(i);

                idZona = Integer.parseInt(listItem.split(" - ")[0]);

                et_codzona.setText(idZona.toString());
                RecuperarZona();
            }
        });
    }

    public void Registrar(View view) {

        String descri = et_descripcion.getText().toString();
        String tipo_bahia = et_codtipob.getText().toString();
        String zona = et_codzona.getText().toString();

        if(descri.isEmpty() || tipo_bahia.isEmpty() || zona.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_descripcion.requestFocus();
        } else {
            String URL = ENLACE+"bahia.php?accion=insert&nom_bahia="+et_descripcion.getText().toString()+"&tipo_bahia="+et_codtipob.getText().toString()+"&zona="+et_codzona.getText().toString()+"&estado_bahia=LIBRE";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("status").equals("success")){
                            String message = parentObject.getString("message");
                            et_descripcion.setText("");
                            et_codigo.setText("");
                            et_codtipob.setText("");
                            et_descritipob.setText("");
                            et_codzona.setText("");
                            et_descrizona.setText("");
                            Toast.makeText(SalidaActivity.this, message, Toast.LENGTH_SHORT).show();
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
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void Eliminar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SalidaActivity.this);
        builder.setMessage("¿Desea Eliminar este registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    String URL = ENLACE+"bahia.php?accion=delete&id_bahia="+et_codigo.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                                if (parentObject.getString("status").equals("success")){
                                    String message = parentObject.getString("message");
                                    et_descripcion.setText("");
                                    et_codigo.setText("");
                                    et_codtipob.setText("");
                                    et_descritipob.setText("");
                                    et_codzona.setText("");
                                    et_descrizona.setText("");
                                    Toast.makeText(SalidaActivity.this, message, Toast.LENGTH_SHORT).show();
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
                    requestQueue = Volley.newRequestQueue(SalidaActivity.this);
                    requestQueue.add(stringRequest);
                    Cancelar();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Cancelar();
                    break;
            }
        }
    };


    public void Modificar(View view) {

        String descri = et_descripcion.getText().toString();
        String tipo_bahia = et_codtipob.getText().toString();
        String zona = et_codzona.getText().toString();


        if(descri.isEmpty() || tipo_bahia.isEmpty() || zona.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_descripcion.requestFocus();
        }  else {

        final String URL = ENLACE+"bahia.php?accion=update&id_bahia="+et_codigo.getText().toString()+
                "&nom_bahia="+et_descripcion.getText().toString()+
                "&tipo_bahia="+et_codtipob.getText().toString()+
                "&zona="+et_codzona.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String message = parentObject.getString("message");
                        et_descripcion.setText("");
                        et_codigo.setText("");
                        et_codtipob.setText("");
                        et_descritipob.setText("");
                        et_codzona.setText("");
                        et_descrizona.setText("");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        cargaLista();
                        Cancelar();
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
                Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        }
    }

    public void Cancelar() {
        et_codigo.setText("");
        et_descripcion.setText("");
        et_codtipob.setText("");
        et_descritipob.setText("");
        et_codzona.setText("");
        et_descrizona.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_descripcion.requestFocus();
    }

    public void CancelarBoton(View view) {
        et_codigo.setText("");
        et_descripcion.setText("");
        et_codtipob.setText("");
        et_descritipob.setText("");
        et_codzona.setText("");
        et_descrizona.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_descripcion.requestFocus();
    }

    private void cargaLista() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SalidaActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"bahia.php?accion=select";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_bahia");
                        String descripcion = jsonObject.getString("nom_bahia");
                        String tipo_bahiades = jsonObject.getString("nom_tipbahia");
                        String zonades = jsonObject.getString("nom_zona");
                        //et_codmarca.setText(marcod);

                        adapter.add(codigo+" - "+descripcion+" - "+tipo_bahiades+" - "+zonades);

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

    public void Recuperar() {
        String URL = ENLACE+"bahia.php?accion=search&id_bahia="+et_codigo.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){

                        String descripcion = parentObject.getString("nom_bahia");
                        String tipo_bahia = parentObject.getString("id_tipbahia");
                        String zona = parentObject.getString("id_zona");

                        et_descripcion.setText(descripcion);
                        et_codtipob.setText(tipo_bahia);
                        et_codzona.setText(zona);

                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();
                       RecuperarTipoB();
                        RecuperarZona();
                        modificar.setEnabled(true);
                        eliminar.setEnabled(true);
                        registrar.setEnabled(false);
                        // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    } else {
                        Toast.makeText(getApplicationContext(), "No Existe1", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "No Existe2", Toast.LENGTH_SHORT).show();
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

    public void RecuperarTipoB() {
        String URL = ENLACE+"bahia.php?accion=searchTipobahia&tipo_bahia="+et_codtipob.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descri = parentObject.getString("nom_tipbahia");
                        et_descritipob.setText(descri);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                       // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    } else {
                        Toast.makeText(getApplicationContext(), "No Existe3", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "No Existe4", Toast.LENGTH_SHORT).show();
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

    public void RecuperarZona() {
        String URL = ENLACE+"bahia.php?accion=searchZona&zona="+et_codzona.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descri = parentObject.getString("nom_zona");
                        et_descrizona.setText(descri);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                        // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    } else {
                        Toast.makeText(getApplicationContext(), "No Existe3", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "No Existe4", Toast.LENGTH_SHORT).show();
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


    public void Buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery
                    ("select pro_descripcion, pro_costo, mar_codigo, pro_precio from producto where pro_codigo=" + codigo, null);

            if (((Cursor) fila).moveToFirst()) {
                et_descripcion.setText(fila.getString(0));
                et_codtipob.setText(fila.getString(1));
                et_codzona.setText(fila.getString(2));


                registrar.setEnabled(false);
                et_descripcion.requestFocus();
                RecuperarTipoB();
                RecuperarZona();
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No existe El Producto", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "Ingrese el Codigo", Toast.LENGTH_LONG).show();
        }
    }

    private void cargaTipoBA() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SalidaActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"bahia.php?accion=selectTipobahia";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_tipbahia");
                        String descripcion = jsonObject.getString("nom_tipbahia");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listatipob.setAdapter(adapter);
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

    private void cargaZona() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SalidaActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"bahia.php?accion=selectZona";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_zona");
                        String descripcion = jsonObject.getString("nom_zona");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listazona.setAdapter(adapter);
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


}
