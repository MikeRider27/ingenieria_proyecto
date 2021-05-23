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

import java.text.DecimalFormat;

public class BahiaActivity extends AppCompatActivity {

    private String usuario; //se declara variable a recibir usuario del login
    static String HOST = LoginActivity.HOST;
    String TABLA = "producto/";
    String ENLACE = HOST+TABLA;

    EditText et_codigo, et_descripcion, et_costo, et_precio, et_codmarca, et_descrimarca;

    ListView lista, listamarca;

    Integer idEliminar, idMarca;

    Button registrar, buscar, eliminar, modificar;
    RequestQueue request, requestQueue;

    @Override
    public void onBackPressed (){
                finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bahia);

        et_codigo = (EditText) findViewById(R.id.txt_codigo);
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_costo = (EditText) findViewById(R.id.txt_costo);
        et_precio = (EditText) findViewById(R.id.txt_precio);
        et_codmarca = (EditText) findViewById(R.id.txt_codmarca);
        et_descrimarca = (EditText) findViewById(R.id.txt_descrimarca);

        lista = (ListView) findViewById(R.id.listaproducto);
        listamarca = (ListView) findViewById(R.id.listaMarcas);
        registrar = (Button) findViewById(R.id.btnRegistrar);
        modificar = (Button) findViewById(R.id.btnModificar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
        buscar = (Button) findViewById(R.id.bt_buscar);
        et_codigo.setEnabled(false);
        et_codmarca.setEnabled(false);
        et_descrimarca.setEnabled(false);
        Cancelar();
        cargaLista();
        cargaMarca();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listItem = (String) lista.getItemAtPosition(i);

                idEliminar = Integer.parseInt(listItem.split(" - ")[0]);

                et_codigo.setText(idEliminar.toString());
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
    }

    public void Registrar(View view) {

        String descri = et_descripcion.getText().toString();
        String marca = et_codmarca.getText().toString();
        String costo = et_costo.getText().toString();
        String precio = et_precio.getText().toString();
        if(descri.isEmpty() || marca.isEmpty() || costo.isEmpty() || precio.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_descripcion.requestFocus();
        } else if ((Integer.parseInt(precio) <= Integer.parseInt(costo))){
            Toast.makeText(this, "El precio no puede ser menor o igual que el costo!!", Toast.LENGTH_LONG).show();
        } else {
            String URL = ENLACE+"insert.php?descripcion="+et_descripcion.getText().toString()+"&marca="+et_codmarca.getText().toString()+"&costo="+et_costo.getText().toString()+"&precio="+et_precio.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject parentObject = new JSONObject(response);
                        if (parentObject.getString("CREATE").equals("OK")){
                            String cod = parentObject.getString("ID");
                            et_descripcion.setText("");
                            et_codigo.setText("");
                            et_codmarca.setText("");
                            et_descrimarca.setText("");
                            et_costo.setText("");
                            et_precio.setText("");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(BahiaActivity.this);
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
                                    et_descripcion.setText("");
                                    et_codigo.setText("");
                                    et_codmarca.setText("");
                                    et_descrimarca.setText("");
                                    et_costo.setText("");
                                    et_precio.setText("");
                                    Toast.makeText(BahiaActivity.this, "Registro eliminado con exito!!!", Toast.LENGTH_SHORT).show();
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
                    requestQueue = Volley.newRequestQueue(BahiaActivity.this);
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
        String marca = et_codmarca.getText().toString();
        String costo = et_costo.getText().toString();
        String precio = et_precio.getText().toString();

        if(descri.isEmpty() || marca.isEmpty() || costo.isEmpty() || precio.isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
            et_descripcion.requestFocus();
        } else if ((Integer.parseInt(precio) <= Integer.parseInt(costo))){
            Toast.makeText(this, "El precio no puede ser menor o igual que el costo!!", Toast.LENGTH_LONG).show();
        } else {

        final String URL = ENLACE+"update.php?codigo="+et_codigo.getText().toString()+"&descripcion="+et_descripcion.getText().toString()+"&marca="+et_codmarca.getText().toString()+"&costo="+et_costo.getText().toString()+"&precio="+et_precio.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){
                        et_descripcion.setText("");
                        et_codigo.setText("");
                        et_codmarca.setText("");
                        et_descrimarca.setText("");
                        et_costo.setText("");
                        et_precio.setText("");
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
        et_codigo.setText("");
        et_descripcion.setText("");
        et_costo.setText("");
        et_precio.setText("");
        et_codmarca.setText("");
        et_descrimarca.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_descripcion.requestFocus();
    }

    public void CancelarBoton(View view) {
        et_codigo.setText("");
        et_descripcion.setText("");
        et_costo.setText("");
        et_precio.setText("");
        et_codmarca.setText("");
        et_descrimarca.setText("");
        registrar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        et_descripcion.requestFocus();
    }

    private void cargaLista() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(BahiaActivity.this,R.layout.support_simple_spinner_dropdown_item);
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
        String URL = ENLACE+"search.php?codigo="+et_codigo.getText().toString();
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
                        et_descripcion.setText(descripcion);
                        et_codmarca.setText(marca);
                        et_precio.setText(Integer.toString(precio));
                        et_costo.setText(Integer.toString(costo));
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
        String URL = ENLACE+"searchmarca.php?codigo="+et_codmarca.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parentObject = new JSONObject(response);
                    if (parentObject.getString("CREATE").equals("OK")){
                        //int descri = parentObject.getInt("des");
                        String descri = parentObject.getString("des");
                        et_descrimarca.setText(descri);
                        //txtDescripcion.setText("");
                        //Toast.makeText(Marcas3Activity.this, "Recuperado", Toast.LENGTH_SHORT).show();

                       // et_descrimarca.setSelection(et_descrimarca.getText().toString().length());

                    } else if (parentObject.getString("CREATE").equals("EXISTE")){
                        Toast.makeText(getApplicationContext(), "El Registro ya existe!!", Toast.LENGTH_SHORT).show();
                    }else {
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
                et_costo.setText(fila.getString(1));
                et_codmarca.setText(fila.getString(2));
                et_precio.setText(fila.getString(3));


                registrar.setEnabled(false);
                et_descripcion.requestFocus();
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(BahiaActivity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL=ENLACE+"selectmarca.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String codigo = jsonObject.getString("cod");
                        String descripcion = jsonObject.getString("des");

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


}
