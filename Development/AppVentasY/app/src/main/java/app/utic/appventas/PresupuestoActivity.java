package app.utic.appventas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appventas.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PresupuestoActivity extends AppCompatActivity {

    static String HOST = LoginActivity.HOST;
    String TABLA = "presupuesto/";
    String ENLACE = HOST+TABLA;

    private EditText et_codigo, et_fecha, et_usuario, et_cliente, et_cedula, et_codproducto,
            et_cantidad, et_producto, et_precio, et_total, et_codcliente, et_subtotal;
    private ListView lista;
    private Integer idEliminar;
    private Cursor fila;
    private Button registrar, buscar, agregar, finalizar, buscliente, busproducto, cancelar;
    String usuario;

    @Override



    public void onBackPressed (){



            AlertDialog.Builder builder = new AlertDialog.Builder(PresupuestoActivity.this);
            builder.setMessage("Desea Salir?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                   finish();

                /*} else {
                    Toast.makeText(MarcasActivity.this, "Seleccione Elemento", Toast.LENGTH_LONG).show();
                }*/
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_fecha = (EditText)findViewById(R.id.txt_fecha);
        et_usuario = (EditText)findViewById(R.id.txtusu);
        et_cedula = (EditText)findViewById(R.id.txt_cedula);
        et_codcliente = (EditText)findViewById(R.id.txt_codcliente);
        et_cliente = (EditText)findViewById(R.id.txt_cliente);
        et_codproducto = (EditText)findViewById(R.id.txt_codproducto);
        et_producto = (EditText)findViewById(R.id.txt_producto);
        et_total = (EditText)findViewById(R.id.txt_total);
        et_cantidad = (EditText)findViewById(R.id.txt_cantidad);
        et_precio = (EditText)findViewById(R.id.txt_precio);
        et_subtotal = (EditText)findViewById(R.id.txt_subtotal);

        lista = (ListView)findViewById(R.id.listadetalle);
        registrar = (Button)findViewById(R.id.btnRegistrar);
        buscar = (Button)findViewById(R.id.bt_buscar);
        agregar = (Button)findViewById(R.id.bt_agregar);
        finalizar = (Button)findViewById(R.id.bt_finalizar);
        busproducto = (Button)findViewById(R.id.bt_busproducto);
        buscliente = (Button)findViewById(R.id.btnModificar);
        cancelar = (Button)findViewById(R.id.btnEliminar);
        et_cedula.requestFocus();
        et_codproducto.setEnabled(false);
        et_producto.setEnabled(false);
        et_cantidad.setEnabled(false);
        et_precio.setEnabled(false);
        et_total.setEnabled(false);
        finalizar.setEnabled(false);
        et_fecha.setEnabled(false);
        et_usuario.setEnabled(false);
        et_cliente.setEnabled(false);
        busproducto.setEnabled(false);
        agregar.setEnabled(false);

        Date d = new Date();
        CharSequence s = DateFormat.format(" d, MMMM yyyy", d.getTime());
        et_fecha.setText(s);

        Bundle bundle = this.getIntent().getExtras(); //recupera datos adjuntos al intent
        if(bundle != null){
           usuario = (bundle.getString("usu")); //carga en la variable usuario el nombre del usuario logueado
        }


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String listItem = (String) lista.getItemAtPosition(position);

                idEliminar = Integer.parseInt(listItem.split(" / ")[0]);
                Eliminar();

               // Toast.makeText(PresupuestoActivity.this, "Id es; "+idEliminar, Toast.LENGTH_LONG).show();
                return true;

            }
        });
    }



    private void Eliminar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        final String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PresupuestoActivity.this);
            builder.setMessage("Esta seguro de eliminar el registro");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if (!codigo.isEmpty()) {
                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(PresupuestoActivity.this, "administracion", null, 1);
                    SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

                        int cantidad = BaseDeDatos.delete("presupuesto_detalle", "pdet_codigo=" + idEliminar, null);
                        BaseDeDatos.close();

                       if (cantidad == 1) {
                            Toast.makeText(PresupuestoActivity.this, "Registro Eliminado", Toast.LENGTH_LONG).show();
                            cargaDetalle();
                            CalcuTotal();
                        } else {
                        }
                    }

                /*} else {
                    Toast.makeText(MarcasActivity.this, "Seleccione Elemento", Toast.LENGTH_LONG).show();
                }*/
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(PresupuestoActivity.this, "Seleccione Elemento", Toast.LENGTH_LONG).show();
        }
       /* if(!codigo.isEmpty())

    {
        fila = BaseDeDatos.rawQuery("SELECT * FROM producto WHERE mar_codigo= " + codigo + "", null);
        if (fila.getCount() > 0) {
            Toast.makeText(this, "El registro esta siendo usado en otra tabla", Toast.LENGTH_LONG).show();
        } else {
            int cantidad = BaseDeDatos.delete("marca", "mar_codigo=" + codigo, null);
            BaseDeDatos.close();

            Cancelar();
            registrar.setEnabled(true);
            et_descripcion.requestFocus();
            if (cantidad == 1) {
                Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_LONG).show();
                cargaLista();
            } else {
            }
        }

    }else

    {
        Toast.makeText(this, "Seleccione Elemento", Toast.LENGTH_LONG).show();
    }*/

    }


    public void BuscarCliente(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String cedula = et_cedula.getText().toString();

        if(!cedula.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("select cli_codigo, cli_nombre||' '||cli_apellido as cliente from cliente where cli_ci=" + cedula, null);

            if(((Cursor) fila).moveToFirst()){
                et_codcliente.setText(fila.getString(0));
                et_cliente.setText(fila.getString(1));

                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe El Cliente", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Ingrese el Numero de Documento", Toast.LENGTH_LONG).show();
        }
    }

    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String codcliente = et_codcliente.getText().toString();
        //String fecha = et_fecha.getText().toString();
        String usuario = et_usuario.getText().toString();



        if(!codcliente.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("pre_fecha", date);

            registro.put("cli_codigo", codcliente);
            registro.put("emp_usuario", usuario);


            BaseDeDatos.insert("presupuesto", null, registro);
            BaseDeDatos.close();
            et_cedula.setEnabled(false);
            et_codigo.setEnabled(false);
            buscar.setEnabled(false);
            buscliente.setEnabled(false);
            registrar.setEnabled(false);
            cancelar.setEnabled(false);
            et_codproducto.setEnabled(true);
            busproducto.setEnabled(true);
            et_cantidad.setEnabled(true);
            agregar.setEnabled(true);
            et_cantidad.setText("1");
            et_codproducto.requestFocus();
            RecuperarID();
            Toast.makeText(this, "Guardado Correctamente", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Hay Campos Vacios, VERIFIQUE!!!", Toast.LENGTH_LONG).show();
        }
    }


    public void BuscarProducto(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codproducto = et_codproducto.getText().toString();

        if(!codproducto.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("select pro_descripcion, pro_precio from producto where pro_codigo=" + codproducto, null);

            if(((Cursor) fila).moveToFirst()){
                et_producto.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));

                BaseDeDatos.close();

                Calcular();
            }else{
                Toast.makeText(this, "No existe El Producto", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Ingrese el Codigo del Producto", Toast.LENGTH_LONG).show();
        }
    }

    public void RegistrarDetalle(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        String codproducto = et_codproducto.getText().toString();
        String precio = et_precio.getText().toString();
        String producto = et_producto.getText().toString();
        String cantidad = et_cantidad.getText().toString();
        String codigo = et_codigo.getText().toString();
        String subto = et_subtotal.getText().toString();

        if (!codproducto.isEmpty() && !producto.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty()) {
            fila = BaseDeDatos.rawQuery("SELECT * FROM presupuesto_detalle WHERE pro_codigo= '" + codproducto + "' AND pre_codigo="+codigo+"", null);
            if (fila.getCount() > 0) {
                Toast.makeText(this, "El registro ya existe...", Toast.LENGTH_LONG).show();
            } else {
                ContentValues registro = new ContentValues();
                registro.put("pre_codigo", codigo);
                registro.put("pro_codigo", codproducto);
                registro.put("pdet_cantidad", precio);
                registro.put("pdet_precio", cantidad);
                registro.put("pdet_subtotal", subto);


                BaseDeDatos.insert("presupuesto_detalle", null, registro);
                BaseDeDatos.close();
                cargaDetalle();
                finalizar.setEnabled(true);
                et_codproducto.setText("");
                et_cantidad.setText("");
                et_precio.setText("");
                et_producto.setText("");
                et_cantidad.setText("1");
                et_codproducto.requestFocus();
                CalcuTotal();

                Toast.makeText(this, "Guardado Correctamente", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Hay Campos Vacios, VERIFIQUE!!!", Toast.LENGTH_LONG).show();
        }

       /* if(!codproducto.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("pre_codigo", codigo);
            registro.put("pro_codigo", codproducto);
            registro.put("pdet_cantidad", precio);
            registro.put("pdet_precio", cantidad);
            registro.put("pdet_subtotal", subto);


            BaseDeDatos.insert("presupuesto_detalle", null, registro);
            BaseDeDatos.close();
            cargaDetalle();
            finalizar.setEnabled(true);
            et_codproducto.setText("");
            et_cantidad.setText("");
            et_precio.setText("");
            et_producto.setText("");
            et_cantidad.requestFocus();

            Toast.makeText(this, "Guardado Correctamente", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Hay Campos Vacios, VERIFIQUE!!!", Toast.LENGTH_LONG).show();
        }*/
    }

    private void cargaDetalle(){

        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper(this, "administracion", null, 1 );
        SQLiteDatabase db = helper.getWritableDatabase();
        String codigo = et_codigo.getText().toString();

        fila = db.rawQuery("SELECT pd.pdet_codigo, p.pro_descripcion, pd.pdet_precio, pd.pdet_cantidad, pd.pdet_subtotal FROM presupuesto_detalle pd, producto p WHERE pd.pro_codigo=p.pro_codigo AND pd.pre_codigo ="+codigo, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        while(fila.moveToNext()){
            //System.out.println("coco:"+fila.getString(0));
            adapter.add(fila.getString(0) +" / "+fila.getString(1)+" / "+fila.getString(2)+" / "+fila.getString(3)+" / "+fila.getString(4));



    }
        lista.setAdapter(adapter);
    }

    public void finalizar(View view){
        et_cedula.setText("");
        et_cliente.setText("");
        et_codcliente.setText("");
        et_codproducto.setText("");
        et_cantidad.setText("");
        et_producto.setText("");
        et_precio.setText("");
        et_codigo.setText("");
        et_total.setText("");
        cancelar.setEnabled(true);
        buscar.setEnabled(true);
        et_codigo.setEnabled(true);
        registrar.setEnabled(true);
        et_cedula.setEnabled(true);
        agregar.setEnabled(false);
        et_cedula.requestFocus();
        et_codproducto.setEnabled(false);
        et_producto.setEnabled(false);
        et_cantidad.setEnabled(false);
        et_precio.setEnabled(false);
        et_total.setEnabled(false);
        finalizar.setEnabled(false);
        et_fecha.setEnabled(false);
        et_usuario.setEnabled(false);
        et_cliente.setEnabled(false);
        busproducto.setEnabled(false);
        buscliente.setEnabled(true);

        String nombres [] = {""};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_geekipedia, nombres);
        lista.setAdapter(adapter);
    }

    public void RecuperarID(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        fila = BaseDeDatos.rawQuery("SELECT max(pre_codigo) FROM presupuesto",null);

        if(((Cursor) fila).moveToFirst()){
            et_codigo.setText(fila.getString(0));

            BaseDeDatos.close();
        }

    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            fila = BaseDeDatos.rawQuery
                    ("SELECT p.pre_fecha, p.emp_usuario, c.cli_nombre||' '||c.cli_apellido as cliente, c.cli_ci FROM presupuesto p, cliente c WHERE p.cli_codigo=c.cli_codigo AND p.pre_codigo ="+codigo, null);

            if(((Cursor) fila).moveToFirst()){

                //String date = new SimpleDateFormat("dd-MM-yyyy").format(fila.getString(0));

                et_fecha.setText(fila.getString(0));
                et_usuario.setText(fila.getString(1));
                et_cliente.setText(fila.getString(2));
                et_cedula.setText(fila.getString(3));

                cargaDetalle();
                CalcuTotal();
                registrar.setEnabled(false);
                lista.setEnabled(false);

                et_cedula.setEnabled(false);
                buscliente.setEnabled(false);

                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe El Presupuesto", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Ingrese el Codigo", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar(View view){
        registrar.setEnabled(true);
        et_cedula.setEnabled(true);
        buscliente.setEnabled(true);
        et_cedula.requestFocus();
        et_codigo.setText("");
        et_cliente.setText("");
        et_cedula.setText("");
        et_total.setText("");
        lista.setEnabled(true);

        Date d = new Date();
        CharSequence s = DateFormat.format(" d, MMMM yyyy", d.getTime());
        et_fecha.setText(s);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            et_usuario.setText(bundle.getString("usu"));
        }

         String nombres [] = {""};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_geekipedia, nombres);
        lista.setAdapter(adapter);


    }

    public void Calcular(){
        String valor1_string = et_precio.getText().toString();
        String valor2_string = et_cantidad.getText().toString();

        int valor1_int = Integer.parseInt(valor1_string);
        int valor2_int = Integer.parseInt(valor2_string);




            int multi = valor1_int * valor2_int;

        String resultado = String.valueOf(multi);

        et_subtotal.setText(resultado);
    }

    public void CalcuTotal(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

                   fila = BaseDeDatos.rawQuery
                    ("SELECT sum(pdet_subtotal) FROM presupuesto_detalle WHERE pre_codigo ="+codigo, null);

            if(((Cursor) fila).moveToFirst()){
                et_total.setText(fila.getString(0));


                BaseDeDatos.close();
            }

    }


}
