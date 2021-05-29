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
import android.widget.Spinner;
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

public class CobrosActivity extends AppCompatActivity {

    private String usuario; //se declara variable a recibir usuario del login
    static String HOST = LoginActivity.HOST;
    String TABLA = "api/";
    String ENLACE = HOST+TABLA;

    EditText et_codigo, et_descripcion, et_cliente, et_clinom, et_entrada, et_monto, et_tipopago, fechaPago, et_celular, et_direc;

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
        setContentView(R.layout.activity_cobros);

        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_cliente = (EditText) findViewById(R.id.txt_cedula);
        et_clinom = (EditText) findViewById(R.id.txt_cliente);
        et_entrada = (EditText) findViewById(R.id.txt_entrada);
        et_monto = (EditText) findViewById(R.id.txt_monto);
        et_direc = (EditText) findViewById(R.id.txt_dir);
        et_celular = (EditText) findViewById(R.id.txt_celular);

        fechaPago = findViewById(R.id.txt_fecha);


        fechaPago.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy"));



        lista = (ListView) findViewById(R.id.listaBahia);
        listatipob = (ListView) findViewById(R.id.listaTipoBa);
        listazona = (ListView) findViewById(R.id.listaZonas);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codigo.setEnabled(false);
        fechaPago.setEnabled(false);






    }

    public void Registrar(View view) {


            String URL = ENLACE+"cobro.php?accion=insert&entrada="+et_entrada.getText().toString()+"&cliente="+et_cliente.getText().toString()+"&monto="+et_monto.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("status").equals("success")){
                            String message = parentObject.getString("message");
                            et_descripcion.setText("");
                            et_codigo.setText("");

                            Toast.makeText(CobrosActivity.this, message, Toast.LENGTH_SHORT).show();


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

    public void Eliminar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CobrosActivity.this);
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
                                    Toast.makeText(CobrosActivity.this, message, Toast.LENGTH_SHORT).show();

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
                    requestQueue = Volley.newRequestQueue(CobrosActivity.this);
                    requestQueue.add(stringRequest);
                    Cancelar();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Cancelar();
                    break;
            }
        }
    };




    public void Cancelar() {
        et_codigo.setText("");


        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_cliente.requestFocus();
    }

    public void CancelarBoton(View view) {
        et_codigo.setText("");


        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_cliente.requestFocus();
    }



    public void BuscarCliente(View view) {
        String URL = ENLACE+"cobros.php?accion=searchcliente&cedula="+et_cliente.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String nombre = parentObject.getString("nombre");
                        String celular = parentObject.getString("celular");
                        String direccion = parentObject.getString("direccion");
                        et_clinom.setText(nombre);
                        et_celular.setText(celular);
                        et_direc.setText(direccion);

                        //txtDescripcion.setText("");
                        Toast.makeText(CobrosActivity.this, "Recuperado", Toast.LENGTH_SHORT).show();

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
    public void BuscarEntrada(View view) {
        String URL = ENLACE+"cobros.php?accion=searchEntrada&entrada="+et_entrada.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String monto = parentObject.getString("monto");
                        et_monto.setText(monto);

                        //txtDescripcion.setText("");
                        Toast.makeText(CobrosActivity.this, "Recuperado", Toast.LENGTH_SHORT).show();

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












}
