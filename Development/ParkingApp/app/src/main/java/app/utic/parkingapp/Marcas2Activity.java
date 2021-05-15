package app.utic.parkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkingapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Marcas2Activity extends AppCompatActivity {

    EditText txt_codigo, txt_descripcion;
    Button btnRegistrar, btnModificar, btnEliminar, btnCancelar, btnBuscar;
    ListView listaMarcas;

    private String HOST="https://www.dfactorypy.com/disciplina/utic";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas2);

        txt_codigo = (EditText) findViewById(R.id.txt_codigo);
        txt_descripcion = (EditText) findViewById(R.id.txt_descripcion);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnModificar = (Button) findViewById(R.id.btnModificar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        listaMarcas = (ListView) findViewById(R.id.listaMarcas);
       // cargaLista();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = txt_codigo.getText().toString();
                String descripcion = txt_descripcion.getText().toString();

                String url = HOST + "/insert.php";

                if(descripcion.isEmpty()){
                    txt_descripcion.setError("Descripcion Obligatoria");
                } else {
                    Ion.with(Marcas2Activity.this)
                            .load(url)
                            .setBodyParameter("descripcion",descripcion)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("CREATE").getAsString().equals("OK")){
                                        int idRetornado = Integer.parseInt(result.get("ID").getAsString());
                                        limpiarCampos();
                                        Toast.makeText(Marcas2Activity.this,"Registrado, codigo "+ idRetornado,Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(Marcas2Activity.this,"Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            public void limpiarCampos(){
                txt_codigo.setText("");
                txt_descripcion.setText("");
            }
        }); //finregistrar

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = txt_codigo.getText().toString();
               // String descripcion = txt_descripcion.getText().toString();

                String url = HOST + "/delete.php";

                if(codigo.isEmpty()){
                    txt_codigo.setError("Codigo Obligatorio");
                } else {
                    Ion.with(Marcas2Activity.this)
                            .load(url)
                            .setBodyParameter("codigo",codigo)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("CREATE").getAsString().equals("OK")){
                                        //int idRetornado = Integer.parseInt(result.get("ID").getAsString());
                                        limpiarCampos();
                                        Toast.makeText(Marcas2Activity.this,"Eliminado",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(Marcas2Activity.this,"Error al Eliminar",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            public void limpiarCampos(){
                txt_codigo.setText("");
                txt_descripcion.setText("");
            }
        }); //finEliminar

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = txt_codigo.getText().toString();
                // String descripcion = txt_descripcion.getText().toString();

                String url = HOST + "/search.php";

                if(codigo.isEmpty()){
                    txt_codigo.setError("Codigo Obligatorio");
                } else {
                    Ion.with(Marcas2Activity.this)
                            .load(url)
                            .setBodyParameter("codigo",codigo)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("CREATE").getAsString().equals("OK")){
                                        txt_descripcion.setText(result.get("des").getAsString());
                                        //limpiarCampos();
                                        Toast.makeText(Marcas2Activity.this,"Recuperado",Toast.LENGTH_LONG).show();
                                    }else if (result.get("CREATE").getAsString().equals("NO EXISTE")){
                                        Toast.makeText(Marcas2Activity.this,"No Existe el registro",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Marcas2Activity.this,"Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        }); //fin buscar

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = txt_codigo.getText().toString();
                String descripcion = txt_descripcion.getText().toString();

                String url = HOST + "/update.php";

                if(codigo.isEmpty() || descripcion.isEmpty() ){
                    Toast.makeText(Marcas2Activity.this,"No debe haber campos vacios",Toast.LENGTH_LONG).show();
                } else {
                    Ion.with(Marcas2Activity.this)
                            .load(url)
                            .setBodyParameter("codigo",codigo)
                            .setBodyParameter("descripcion",descripcion)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {

                                    if(result.get("CREATE").getAsString().equals("OK")){
                                        //txt_descripcion.setText(result.get("des").getAsString());
                                        limpiarCampos();
                                        Toast.makeText(Marcas2Activity.this,"Modificado",Toast.LENGTH_LONG).show();
                                    }else if (result.get("CREATE").getAsString().equals("NO EXISTE")){
                                        Toast.makeText(Marcas2Activity.this,"No Existe el registro",Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Marcas2Activity.this,"Error",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            public void limpiarCampos(){
                txt_codigo.setText("");
                txt_descripcion.setText("");
            }
        }); //fin modificar






    }

    public void CancelarBoton(View v) {
        txt_descripcion.setText("");
        txt_codigo.setText("");
        btnRegistrar.setEnabled(true);
    }



   /* private void cargaLista() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Marcas2Activity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL = HOST + "/select.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String idzona = jsonObject.getString("idperfil");
                        String descripcion = jsonObject.getString("descripcion");

                        adapter.add(idzona+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listaMarcas.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
*/




   /* private void cargaLista() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Marcas2Activity.this,R.layout.support_simple_spinner_dropdown_item);
        String URL = HOST + "/select.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.size(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String idzona = jsonObject.getString("idperfil");
                        String descripcion = jsonObject.getString("descripcion");

                        adapter.add(idzona+" - "+descripcion);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }listaMarcas.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(Marcas2Activity.this);
        requestQueue.add(jsonArrayRequest);
    }*/
/*
    private void cargaLista() {

        //AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        //SQLiteDatabase db = helper.getWritableDatabase();
        //fila = db.rawQuery("SELECT mar_codigo, mar_descripcion FROM marca ORDER BY mar_codigo", null);

       // final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Marcas2Activity.this,R.layout.support_simple_spinner_dropdown_item);
        //app.cat = new ArrayList<Category>();
        String url = HOST + "/select.php";
        Ion.with(Marcas2Activity.this)
                .load(url)
                //.setBodyParameter("descripcion",descripcion)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        String status = result.get("CREATE").getAsString();

                        if (status.equals("OK")) {
                            JsonArray cat_list = result.get("categories").getAsJsonArray();

                            for(int i = 0; i < cat_list.size(); i++) {
                                JsonObject cat_obj = cat_list.get(i).getAsJsonObject();
                                app.cat.add(new Category(cat_obj.get("id").getAsString(), cat_obj.get("label_name").getAsString(), cat_obj.get("label_name_en").getAsString(), cat_obj.get("img").getAsString()));
                            }

                            setupPager();
                        } else if (result.get("error_code").equals("666")) {
                            createErrorBox(result.get("message").getAsString());
                        } else {
                            Toast.makeText(ctx, result.get("message").getAsString(), Toast.LENGTH_LONG).show();
                        }
*/


/*

                        JSONObject jsonObject = null;
                        for (int i = 0; i < result.size(); i++) {
                            try {
                                jsonObject = result.get("CREATE").getAsString()(i);

                                String idzona = jsonObject.getString("codigo");
                                String descripcion = jsonObject.getString("descripcion");

                                adapter.add(idzona+" - "+descripcion);

                            } catch (Exception e1) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

        listaMarcas.setAdapter(adapter);
    }*/
}

