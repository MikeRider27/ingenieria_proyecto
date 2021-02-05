package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.UsuarioDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }






    public List<UsuarioDTO> listar(){
        List<UsuarioDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        c = database.query("usuario",new String[]{"id_usuario","nick","nombre","email","contrasena","estado"},null,null,null,null," id_usuario ASC ");


        while (c.moveToNext())
        {
            lista.add(new UsuarioDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)));

        }

        return lista;
    }

    public UsuarioDTO buscarID(UsuarioDTO usuarioDTO){
        UsuarioDTO dto = null;
        Cursor c = null;
        c = database.query("usuario",new String[]{"id_usuario","nick","nombre","email","contrasena","estado"},"id_usuario = ?",new String[]{String.valueOf(usuarioDTO.getId_usuario())},null,null,null);
        if(c.moveToFirst()){
            dto = new UsuarioDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
