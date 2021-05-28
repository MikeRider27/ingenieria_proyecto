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

public class VehiculoActivity extends AppCompatActivity {

    private String usuario; //se declara variable a recibir usuario del login
    static String HOST = LoginActivity.HOST;
    String TABLA = "api/";
    String ENLACE = HOST+TABLA;

    EditText et_chapa, et_codmarca, et_descrimarca, et_codtipov, et_descritipov, et_codcliente, et_nombre;

    ListView lista, listamarca, listatipov, listacliente;

    Integer idEliminar, idMarca, idTipov, idCliente;

    Button registrar, buscar, eliminar, modificar;
    RequestQueue request, requestQueue;

    @Override
    public void onBackPressed (){
                finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        et_chapa = (EditText) findViewById(R.id.txt_chapa);
        et_codmarca = (EditText) findViewById(R.id.txt_codmarca);
        et_descrimarca = (EditText) findViewById(R.id.txt_descrimarca);

        et_codtipov = (EditText) findViewById(R.id.txt_codtipoveh);
        et_descritipov = (EditText) findViewById(R.id.txt_descritipo);

        et_codcliente = (EditText) findViewById(R.id.txt_codcliente);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);

        lista = (ListView) findViewById(R.id.listaVehiculo);
        listamarca = (ListView) findViewById(R.id.listaMarcas);
        listatipov = (ListView) findViewById(R.id.listaTipovehiculo);
        listacliente = (ListView) findViewById(R.id.listaCliente);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codmarca.setEnabled(false);
        et_descrimarca.setEnabled(false);
        et_codtipov.setEnabled(false);
        et_descritipov.setEnabled(false);
        et_codcliente.setEnabled(false);
        et_nombre.setEnabled(false);
        Cancelar();
        cargaLista();
        cargaMarca();
        cargaTipoV();
        cargaCliente();



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) lista.getItemAtPosition(i);

                idEliminar = Integer.parseInt(listItem.split(" - ")[0]);

                et_chapa.setText(idEliminar.toString());
                Recuperar();
            }
        });

        listamarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listamarca.getItemAtPosition(i);

                idMarca = Integer.parseInt(listItem.split(" - ")[0]);

                et_codmarca.setText(idMarca.toString());
                RecuperarMarca();
            }
        });
        listatipov.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listatipov.getItemAtPosition(i);

                idTipov = Integer.parseInt(listItem.split(" - ")[0]);

                et_codtipov.setText(idTipov.toString());
                RecuperarTipoV();
            }
        });
        listacliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) listacliente.getItemAtPosition(i);

                idCliente = Integer.parseInt(listItem.split(" - ")[0]);

                et_codcliente.setText(idCliente.toString());
                RecuperarCliente();
            }
        });
    }

    public void Registrar(View view) {


        String marca = et_codmarca.getText().toString();


        if( marca.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();

        } else {
            String URL = ENLACE+"insert.php?marca="+et_codmarca.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("CREATE").equals("OK")){
                            String cod = parentObject.getString("ID");

                            et_chapa.setText("");
                            et_codmarca.setText("");
                            et_descrimarca.setText("");

                            Toast.makeText(getApplicationContext(), "Registro numero "+cod+" insertado", Toast.LENGTH_SHORT).show();
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

    public void Eliminar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VehiculoActivity.this);
        builder.setMessage("¿Desea Eliminar este registro?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    String URL = ENLACE+"delete.php?codigo="+et_chapa.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject parentObject = new JSONObject(response);
                                if (parentObject.getString("CREATE").equals("OK")){

                                    et_chapa.setText("");
                                    et_codmarca.setText("");
                                    et_descrimarca.setText("");

                                    Toast.makeText(VehiculoActivity.this, "Registro eliminado con exito!!!", Toast.LENGTH_SHORT).show();
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
                    requestQueue = Volley.newRequestQueue(VehiculoActivity.this);
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


        String marca = et_codmarca.getText().toString();


        if(marca.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();

        }  else {

        final String URL = ENLACE+"update.php?codigo="+et_chapa.getText().toString()+"&marca="+et_codmarca.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){

                        et_chapa.setText("");
                        et_codmarca.setText("");
                        et_descrimarca.setText("");

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

    public void Cancelar() {
        et_chapa.setText("");


        et_codmarca.setText("");
        et_descrimarca.setText("");
        et_codcliente.setText("");
        et_nombre.setText("");
        et_codtipov.setText("");
        et_descritipov.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);

    }

    public void CancelarBoton(View view) {
        et_chapa.setText("");


        et_codmarca.setText("");
        et_descrimarca.setText("");
        et_codcliente.setText("");
        et_nombre.setText("");
        et_codtipov.setText("");
        et_descritipov.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);

    }

    private void cargaLista() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(VehiculoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"select.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("cod");
                        String descripcion = jsonObject.getString("des");
                        String marca = jsonObject.getString("mar");
                        String marcod = jsonObject.getString("marcod");
                        String costo= jsonObject.getString("cos");
                        String precio = jsonObject.getString("pre");
                        //et_codmarca.setText(marcod);

                        adapter.add(codigo+" - "+descripcion+" - "+marca+" - "+costo+" - "+precio);

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
        String URL = ENLACE+"search.php?codigo="+et_chapa.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){

                        String descripcion = parentObject.getString("des");
                        String marca = parentObject.getString("mar");
                        int costo = parentObject.getInt("cos");
                        int precio = parentObject.getInt("pre");

                        et_codmarca.setText(marca);


                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();
                        RecuperarMarca();
                        modificar.setEnabled(true);
                        eliminar.setEnabled(true);
                        registrar.setEnabled(false);
                        // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    } else if (parentObject.getString("CREATE").equals("EXISTE")){
                        Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                    }else {
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

    public void RecuperarMarca() {
        String URL = ENLACE+"vehiculo.php?accion=searchMarca&id_marca="+et_codmarca.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descri = parentObject.getString("nom_marca");
                        et_descrimarca.setText(descri);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                       // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    }else {
                        Toast.makeText(getApplicationContext(), "No Existe", Toast.LENGTH_LONG).show();
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
    public void RecuperarTipoV() {
        String URL = ENLACE+"vehiculo.php?accion=searchTipoV&id_tipvehiculo="+et_codtipov.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descritipov = parentObject.getString("nom_tipvehiculo");
                        et_descritipov.setText(descritipov);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                       // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    }else {
                        Toast.makeText(getApplicationContext(), "No Existe", Toast.LENGTH_LONG).show();
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
    public void RecuperarCliente() {
        String URL = ENLACE+"vehiculo.php?accion=searchCliente&id_cliente="+et_codcliente.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("status").equals("success")){
                        //int descri = parentObject.getInt("des");
                        String descricliente = parentObject.getString("nombres");
                        et_nombre.setText(descricliente);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                       // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    }else {
                        Toast.makeText(getApplicationContext(), "No Existe", Toast.LENGTH_LONG).show();
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

        String codigo = et_chapa.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery
                    ("select pro_descripcion, pro_costo, mar_codigo, pro_precio from producto where pro_codigo=" + codigo, null);

            if (((Cursor) fila).moveToFirst()) {


                et_codmarca.setText(fila.getString(2));



                registrar.setEnabled(false);

                RecuperarMarca();
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No existe El Producto", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        } else {
            Toast.makeText(this, "Ingrese el Codigo", Toast.LENGTH_LONG).show();
        }
    }

    private void cargaMarca() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(VehiculoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"vehiculo.php?accion=selectMarca";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_marca");
                        String descripcion = jsonObject.getString("nom_marca");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listamarca.setAdapter(adapter);
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
    private void cargaTipoV() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(VehiculoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"vehiculo.php?accion=selectTipoV";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_tipvehiculo");
                        String descripcion = jsonObject.getString("nom_tipvehiculo");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listatipov.setAdapter(adapter);
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
    private void cargaCliente() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(VehiculoActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"vehiculo.php?accion=selectCliente";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("id_cliente");
                        String descripcion = jsonObject.getString("nombres");

                        adapter.add(codigo+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listacliente.setAdapter(adapter);
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
