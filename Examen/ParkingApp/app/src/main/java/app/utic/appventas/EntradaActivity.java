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

public class EntradaActivity extends AppCompatActivity {

    private String usuario; //se declara variable a recibir usuario del login
    static String HOST = LoginActivity.HOST;
    String TABLA = "api/";
    String ENLACE = HOST+TABLA;

    EditText et_codigo, et_chapa, et_codbahia, et_describahia, et_cliente, et_descrizona, fechaEntrada, et_obs, et_marca, et_tipovehiculo;

    ListView listabahia;

    Integer idBahia, iusuario;

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
        et_chapa = (EditText) findViewById(R.id.txt_chapa);
        et_codbahia = (EditText) findViewById(R.id.txt_codbahia);
        et_marca = (EditText) findViewById(R.id.txt_marca);
        et_tipovehiculo = (EditText) findViewById(R.id.txt_tipovehiculo);
        et_describahia = (EditText) findViewById(R.id.txt_describahia);
        et_cliente = (EditText) findViewById(R.id.txt_cliente);
        et_obs = (EditText) findViewById(R.id.txt_observacio);
        fechaEntrada = findViewById(R.id.txt_fecha);


        fechaEntrada.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy"));



        listabahia = (ListView) findViewById(R.id.listaBahias);

        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codigo.setEnabled(false);
        fechaEntrada.setEnabled(false);
        cargaBahia();





        listabahia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listabahia.getItemAtPosition(i);

                idBahia = Integer.parseInt(listItem.split(" - ")[0]);

                et_codbahia.setText(idBahia.toString());
                RecuperarBahia();
            }
        });


    }

    public void Registrar(View view) {

        String chapa = et_chapa.getText().toString();
        String bahia = et_codbahia.getText().toString();
        String observaciones = et_obs.getText().toString();

        if(chapa.isEmpty() || bahia.isEmpty() || observaciones.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_chapa.requestFocus();
        } else {
            String URL = ENLACE+"entrada.php?accion=insert&chapa="+et_chapa.getText().toString()+"&bahia="+et_codbahia.getText().toString()+"&observaciones="+et_obs.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("status").equals("success")){
                            String message = parentObject.getString("message");

                            et_codigo.setText("");
                            et_chapa.setText("");
                            et_codbahia.setText("");
                            et_marca.setText("");
                            et_tipovehiculo.setText("");
                            et_describahia.setText("");
                            et_cliente.setText("");
                            et_obs.setText("");


                            Toast.makeText(EntradaActivity.this, message, Toast.LENGTH_SHORT).show();
                            cargaBahia();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(EntradaActivity.this);
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

                                    et_codigo.setText("");

                                    et_descrizona.setText("");
                                    Toast.makeText(EntradaActivity.this, message, Toast.LENGTH_SHORT).show();
                                    //cargaLista();
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
                    requestQueue = Volley.newRequestQueue(EntradaActivity.this);
                    requestQueue.add(stringRequest);
                    Cancelar();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Cancelar();
                    break;
            }
        }
    };


    public void BuscarV(View view) {
        String URL = ENLACE+"entrada.php?accion=searchV&chapa="+et_chapa.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String tipo_vehiculo = parentObject.getString("nom_tipvehiculo");
                        String marca = parentObject.getString("nom_marca");
                        String nombres = parentObject.getString("nombres");
                        et_tipovehiculo.setText(tipo_vehiculo);
                        et_marca.setText(marca);
                        et_cliente.setText(nombres);

                        //txtDescripcion.setText("");
                       Toast.makeText(EntradaActivity.this, "Recuperado", Toast.LENGTH_SHORT).show();

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




    public void Cancelar() {
        et_codigo.setText("");

        registrar.setEnabled(true);

        eliminar.setEnabled(false);

    }

    public void CancelarBoton(View view) {
        et_codigo.setText("");


        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);

    }

    private void cargaBahia() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(EntradaActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"entrada.php?accion=selectBahia";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_bahia");
                        String descripcion = jsonObject.getString("nom_bahia");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listabahia.setAdapter(adapter);
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



    public void RecuperarBahia() {
        String URL = ENLACE+"entrada.php?accion=searchBahia&bahia="+et_codbahia.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descri = parentObject.getString("nom_bahia");
                        et_describahia.setText(descri);
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












}
