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

    EditText et_codigo, fechaSalida, codtipob, et_chapa, et_cliente, et_tiempo;


    Integer idEliminar, idTipoB, idZona;

    Button registrar, buscar, eliminar, modificar;
    RequestQueue request, requestQueue;

    @Override
    public void onBackPressed (){
                finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida);

        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_chapa = (EditText) findViewById(R.id.txt_chapa);
        et_cliente = (EditText) findViewById(R.id.txt_cliente);
        et_tiempo = (EditText) findViewById(R.id.txt_tiempo);
        fechaSalida = findViewById(R.id.txt_fecha);


        fechaSalida.setText(ParametrosGlobales.completarFecha("dd/MM/yyyy"));



        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codigo.setEnabled(false);
        fechaSalida.setEnabled(false);






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

                                    et_codigo.setText("");

                                    Toast.makeText(SalidaActivity.this, message, Toast.LENGTH_SHORT).show();

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

        String chapa = et_chapa.getText().toString();
        String tiempo = et_tiempo.getText().toString();



        if(chapa.isEmpty() || tiempo.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_chapa.requestFocus();
        }  else {

        final String URL = ENLACE+"entrada.php?accion=update&chapa="+et_chapa.getText().toString()+
                "&tiempo="+et_tiempo.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String message = parentObject.getString("message");
                        et_codigo.setText("");
                        et_chapa.setText("");
                        et_cliente.setText("");
                        et_tiempo.setText("");

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


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

    public void BuscarV(View view) {
        String URL = ENLACE+"entrada.php?accion=searchSalida&chapa="+et_chapa.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        String tiempo = parentObject.getString("horas");
                        String nombres = parentObject.getString("nombres");

                        et_cliente.setText(nombres);
                        et_tiempo.setText(tiempo);

                        //txtDescripcion.setText("");
                        Toast.makeText(SalidaActivity.this, "Recuperado", Toast.LENGTH_SHORT).show();

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
        modificar.setEnabled(false);
        eliminar.setEnabled(false);

    }

    public void CancelarBoton(View view) {
        et_codigo.setText("");

        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);

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



                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

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













}
