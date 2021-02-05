package com.mavc.parkingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Controlador   extends AppCompatActivity
{
    String tabla = "";
    String[] cols = new String[]{};
    int position = 0;
    Button grabar, agregar, editar, limpiar, eliminar;
    EditText codigo, descripcion, nombre, login, clave, email;
    EditText precio;

    Spinner idgrupo,idmarca;

    private Cursor fila;
    ListView lista;

    int accion=1;
    String printResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras() != null)
        {
            tabla=getIntent().getExtras().getString("tabla");

            if(tabla.equalsIgnoreCase("marca"))
            {
                setContentView(R.layout.activity_marca);
                agregar = (Button) findViewById(R.id.btnAgregar);
                grabar = (Button) findViewById(R.id.btnGrabar);
                editar = (Button) findViewById(R.id.btnEditar);
                eliminar = (Button) findViewById(R.id.btnEliminar);

                codigo = (EditText) findViewById(R.id.txt_id_marca);
                descripcion = (EditText) findViewById(R.id.txt_nom_marca);

                codigo.setEnabled(false);
                cols = Modelo.colsDefault;
                position = 1;
                cargaLista();
            }
            else if(tabla.equalsIgnoreCase("usuarios"))
            {
                setContentView(R.layout.activity_usuario);

                agregar = (Button) findViewById(R.id.btnAgregar);
                grabar = (Button) findViewById(R.id.btnGrabar);
                editar = (Button) findViewById(R.id.btnEditar);
                eliminar = (Button) findViewById(R.id.btnEliminar);

                codigo = (EditText) findViewById(R.id.txt_id_usuario);
                nombre = (EditText) findViewById(R.id.txt_nom_usuario);
                login = (EditText) findViewById(R.id.txt_nick);
                clave = (EditText) findViewById(R.id.txt_usu_clave);

                codigo.setEnabled(false);
                cols = Modelo.colsPat;
                position = 2;
                cargaLista_usuarios();
            }

            //metodo grabar
            grabar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Modelo mod = new Modelo(getBaseContext());
                    mod.open();
                    if(tabla.equalsIgnoreCase("grupo")||tabla.equalsIgnoreCase("marcas"))
                    {
                        if(accion==1)
                        {
                            if(descripcion.getText().toString().trim().length() > 0)
                            {
                                mod.crearOActualizarDefaultMod(tabla, null, descripcion.getText().toString().trim());
                                printResult = "Agregado con éxito!!";
                                cargaLista();
                            }else
                            {
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }else if(accion==2)
                        {
                            if(descripcion.getText().toString().trim().length() > 0
                                    && codigo.getText().toString().trim().length() > 0){
                                mod.crearOActualizarDefaultMod(tabla, codigo.getText().toString(), descripcion.getText().toString().trim());
                                printResult = "Actualizado con éxito!!";
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }else if(accion==3)
                        {
                            if(descripcion.getText().toString().trim().length() > 0
                                    && codigo.getText().toString().trim().length() > 0){
                                mod.eliminar(tabla, Integer.valueOf(codigo.getText().toString().trim()));
                                printResult = "Eliminado con éxito!!";
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }
                    }else if(tabla.equalsIgnoreCase("usuarios"))
                    {
                        if(accion==1)
                        {
                            if(nombre.getText().toString().trim().length() > 0 &&
                                    login.getText().toString().trim().length() > 0 &&
                                    clave.getText().toString().trim().length() > 0){
                                mod.crearOActualizarPatrullero(tabla, null,
                                        nombre.getText().toString().trim(),
                                        login.getText().toString().trim(),
                                        clave.getText().toString().trim());
                                printResult = "Usuario Agregado con éxito!!";
                                cargaLista_usuarios();
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }else if(accion==2)
                        {
                            if(nombre.getText().toString().trim().length() > 0 &&
                                    login.getText().toString().trim().length() > 0 &&
                                    clave.getText().toString().trim().length() > 0 &&
                                    codigo.getText().toString().trim().length() > 0 ){
                                mod.crearOActualizarPatrullero(tabla,
                                        codigo.getText().toString().trim(),
                                        nombre.getText().toString().trim(),
                                        login.getText().toString().trim(),
                                        clave.getText().toString().trim());
                                printResult = "Usuario Actualizado con éxito!!";
                                cargaLista_usuarios();
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }else if(accion==3)
                        {
                            if(nombre.getText().toString().trim().length() > 0 &&
                                    login.getText().toString().trim().length() > 0 &&
                                    clave.getText().toString().trim().length() > 0 &&
                                    codigo.getText().toString().trim().length() > 0 ){
                                mod.eliminar(tabla, Integer.valueOf(codigo.getText().toString().trim()));
                                printResult = "Usuario Eliminado con éxito!!";
                                cargaLista_usuarios();
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }
                    }else if(tabla.equalsIgnoreCase("articulos"))
                    {
                        if(accion==1)//insertar
                        {
                            //validaciones
                            if(descripcion.getText().toString().trim().length() > 0 &&
                                    precio.getText().toString().trim().length() > 0)
                            {
                                mod.crearOActualizarArticulos(tabla, null,
                                        descripcion.getText().toString().trim(),precio.getText().toString().trim(),
                                        idgrupo.getSelectedItem().toString(), idmarca.getSelectedItem().toString());
                                printResult = "Articulo Ha Sido Agregado con éxito!!";
                                cargaLista_articulos();
                            }else
                            {
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }
                        else if(accion==2)//editar
                        {
                            if(descripcion.getText().toString().trim().length() > 0 &&
                                    precio.getText().toString().trim().length() > 0 &&
                                    idgrupo.getSelectedItem().toString().trim().length() > 0 &&
                                    codigo.getText().toString().trim().length() > 0){
                                mod.crearOActualizarArticulos(tabla, codigo.getText().toString().trim(),
                                        descripcion.getText().toString().trim(),precio.getText().toString().trim(),idgrupo.getSelectedItem().toString().trim(),
                                        idmarca.getSelectedItem().toString());
                                printResult = "Articulo Actualizado con éxito!!";
                                cargaLista_articulos();
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }
                        else if(accion==3)//borrar
                        {
                            if(codigo.getText().toString().trim().length() > 0 &&
                                    precio.getText().toString().trim().length() > 0 &&
                                    descripcion.getText().toString().trim().length() > 0)
                            {
                                mod.eliminar(tabla, Integer.valueOf(codigo.getText().toString().trim()));
                                printResult = "Articulo Eliminado con éxito!!";
                                cargaLista_articulos();
                            }else{
                                printResult = "Todos los campos son requeridos!!";
                            }
                        }
                    }

                    //si grabo sin problemas
                    if(!printResult.equalsIgnoreCase("Todos los campos son requeridos!!")){
                        accion =1;
                        if(tabla.equalsIgnoreCase("grupo")|| tabla.equalsIgnoreCase("marcas"))
                        {
                            if(accion == 1)
                            {
                                codigo.setText("");
                                descripcion.setText("");
                                descripcion.requestFocus();
                                codigo.setEnabled(false);
                            }
                        }else if(tabla.equalsIgnoreCase("usuarios"))
                        {
                            if(accion ==1)
                            {
                                codigo.setText("");
                                nombre.setText("");
                                login.setText("");
                                clave.setText("");
                                nombre.requestFocus();
                                codigo.setEnabled(false);
                            }
                        }else if(tabla.equalsIgnoreCase("articulos"))
                        {
                            if(accion == 1)
                            {
                                codigo.setText("");
                                descripcion.setText("");
                                precio.setText("");
                                descripcion.requestFocus();
                                idgrupo.setSelection(0);
                                idmarca.setSelection(0);
                                codigo.setEnabled(false);
                            }
                        }
                        grabar.setEnabled(true);
                        eliminar.setEnabled(true);
                        editar.setEnabled(true);
                    }
                    Toast.makeText(getBaseContext(), printResult,Toast.LENGTH_LONG).show();
                    mod.close();
                }
            });//FIN GRABAR

            //BUSCAR
           /* buscar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(codigo.getText().toString().trim().length() > 0)
                    {
                        Modelo mod = new Modelo(getBaseContext());
                        mod.open();
                        mod.buscarDefaultMod(tabla, codigo.getText().toString().trim(), cols, position, true);
                        if(mod.codigo != null)
                        {
                            if(tabla.equalsIgnoreCase("grupo")|| tabla.equalsIgnoreCase("marcas"))
                            {
                                descripcion.setText(mod.descripcion);
                            }else if(tabla.equalsIgnoreCase("usuarios"))
                            {
                                nombre.setText(mod.nombre);
                                login.setText(mod.login);
                                clave.setText(mod.clave);
                            }else if(tabla.equalsIgnoreCase("articulos"))
                            {
                                descripcion.setText(mod.descripcion);
                                precio.setText(mod.precio.toString());

                                int posicionGrupo = Modelo.getIndex(idgrupo, mod.id_grupo);
                                idgrupo.setSelection(posicionGrupo);

                                int posicionMarca =  Modelo.getIndex(idmarca, mod.id_marca);
                                idmarca.setSelection(posicionMarca);

                            }
                        }else{
                            accion =1;
                            if(tabla.equalsIgnoreCase("grupo")||tabla.equalsIgnoreCase("marcas"))
                            {
                                if(accion == 1)
                                {
                                    codigo.setText("");
                                    descripcion.setText("");
                                    descripcion.requestFocus();
                                    codigo.setEnabled(false);
                                }
                            }else if(tabla.equalsIgnoreCase("usuarios"))
                            {
                                if(accion ==1)
                                {
                                    codigo.setText("");
                                    nombre.setText("");
                                    login.setText("");
                                    clave.setText("");
                                    nombre.requestFocus();
                                    codigo.setEnabled(false);
                                }
                            }else if(tabla.equalsIgnoreCase("articulos"))
                            {
                                if(accion == 1)
                                {
                                    codigo.setText("");
                                    descripcion.setText("");
                                    precio.setText("");
                                    descripcion.requestFocus();

                                    idgrupo.setSelection(0);
                                    idmarca.setSelection(0);
                                    codigo.setEnabled(false);
                                }
                            }

                            buscar.setEnabled(false);
                            grabar.setEnabled(true);
                            eliminar.setEnabled(true);
                            editar.setEnabled(true);
                            Toast.makeText(getBaseContext(), "No se encontró ningún valor!!",Toast.LENGTH_LONG).show();
                        }
                        mod.close();
                        buscar.setEnabled(true);
                        grabar.setEnabled(true);
                        eliminar.setEnabled(false);
                        editar.setEnabled(false);
                    }else{
                        Toast.makeText(getBaseContext(), "El campo codigo es requerido",Toast.LENGTH_LONG).show();
                    }
                }
            });//FIN BUSCAR
*/
            limpiar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    accion =1;
                    if(tabla.equalsIgnoreCase("grupo")|| tabla.equalsIgnoreCase("marcas"))
                    {
                        if(accion == 1)
                        {
                            codigo.setText("");
                            descripcion.setText("");
                            descripcion.requestFocus();
                            codigo.setEnabled(false);
                        }
                    }else if(tabla.equalsIgnoreCase("usuarios"))
                    {
                        if(accion ==1)
                        {
                            codigo.setText("");
                            nombre.setText("");
                            login.setText("");
                            clave.setText("");
                            nombre.requestFocus();
                            codigo.setEnabled(false);
                        }
                    }else if(tabla.equalsIgnoreCase("articulos"))
                    {
                        if(accion == 1)
                        {
                            codigo.setText("");
                            descripcion.setText("");
                            precio.setText("");

                            descripcion.requestFocus();

                            idgrupo.setSelection(0);
                            idmarca.setSelection(0);

                            codigo.setEnabled(false);
                        }
                    }

                    grabar.setEnabled(true);
                    eliminar.setEnabled(true);
                    editar.setEnabled(true);
                }
            });
            editar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    accion =2;
                    if(tabla.equalsIgnoreCase("grupo")|| tabla.equalsIgnoreCase("marcas"))
                    {
                        if(accion == 2)
                        {
                            codigo.setText("");
                            descripcion.setText("");
                            codigo.setEnabled(true);
                            codigo.requestFocus();
                        }
                    }else if(tabla.equalsIgnoreCase("usuarios"))
                    {
                        if(accion == 2)
                        {
                            codigo.setText("");
                            nombre.setText("");
                            login.setText("");
                            clave.setText("");
                            codigo.setEnabled(true);
                            codigo.requestFocus();
                        }
                    }else if(tabla.equalsIgnoreCase("articulos"))
                    {
                        if(accion == 2)
                        {
                            codigo.setText("");
                            descripcion.setText("");
                            precio.setText("");


                            codigo.setEnabled(true);
                            codigo.requestFocus();
                        }
                    }


                    grabar.setEnabled(false);
                    eliminar.setEnabled(false);
                    editar.setEnabled(false);
                }
            });
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accion =3;
                    if(tabla.equalsIgnoreCase("grupo")|| tabla.equalsIgnoreCase("marcas"))
                    {
                        if(accion == 3)
                        {
                            codigo.setText("");
                            descripcion.setText("");
                            codigo.setEnabled(true);
                            codigo.requestFocus();
                        }
                    }else if(tabla.equalsIgnoreCase("usuarios"))
                    {
                        if(accion ==3)
                        {
                            codigo.setText("");
                            nombre.setText("");
                            login.setText("");
                            clave.setText("");
                            codigo.setEnabled(true);
                            codigo.requestFocus();
                        }
                    }else if(tabla.equalsIgnoreCase("articulos"))
                    {
                        codigo.setText("");
                        descripcion.setText("");
                        precio.setText("");


                        codigo.setEnabled(true);
                        codigo.requestFocus();
                    }


                    grabar.setEnabled(false);
                    eliminar.setEnabled(false);
                    editar.setEnabled(false);
                }
            });
        }
    }
    public static TextWatcher insertData(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";
            public void onTextChanged(CharSequence s,int start,int before,int count){String str=s.toString().replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", ""); String ma = ""; if (isUpdating) { old = str;isUpdating = false;return;  } int i = 0; for (char m : mask.toCharArray()) { if (m != '#' && str.length() > old.length()) {  ma += m;  continue;}    try { ma += str.charAt(i);  } catch (Exception e) {   break; }  i++; }  isUpdating = true;  ediTxt.setText(ma); ediTxt.setSelection(ma.length());}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }
    public void initButton()
    {
        grabar = (Button) findViewById(R.id.btnGrabar);
        editar = (Button) findViewById(R.id.btnEditar);
        //limpiar = (Button) findViewById(R.id.limpiar);
        eliminar = (Button) findViewById(R.id.btnEliminar);
    }

    //para tablas de 2 columnas, codigo y descripcion
    private void cargaLista()
    {
        lista = (ListView)findViewById(R.id.listView2);

        registerForContextMenu(lista);

        Database database = new Database(this);

        fila =database.getReadableDatabase().rawQuery("SELECT codigo,descripcion FROM "+ tabla +" ORDERBY codigo ", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);

        while(fila.moveToNext())
        {
            adapter.add(fila.getString(0)+"-"+fila.getString(1));
        }
        lista.setAdapter(adapter);
    }//fin lista 2 columnas


    private void cargaLista_articulos()
    {
        lista = (ListView)findViewById(R.id.listView2);

        registerForContextMenu(lista);

        Database database = new Database(this);

        fila =database.getReadableDatabase().rawQuery("SELECT codigo,descripcion,precio FROM articulos ORDER BY codigo", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);

        while(fila.moveToNext())
        {
            adapter.add(fila.getString(0)+"-"+fila.getString(1)+"-"+fila.getString(2));
        }
        lista.setAdapter(adapter);
    }//fin lista articulos

    private void cargaLista_usuarios()
    {
        lista = (ListView)findViewById(R.id.listView2);

        registerForContextMenu(lista);

        Database database = new Database(this);

        fila =database.getReadableDatabase().rawQuery("SELECT codigo,nombre,user FROM usuarios ORDER BY codigo", null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.mytextview);

        while(fila.moveToNext())
        {
            adapter.add(fila.getString(0)+"-"+fila.getString(1)+"-"+fila.getString(2));
        }
        lista.setAdapter(adapter);
    }//fin lista usuarios

}


