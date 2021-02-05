package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.RegistroDTO;
import com.mavc.parkingapp.Database;



public class RegistroDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public RegistroDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(RegistroDTO registroDTO){
        ContentValues valores = new ContentValues();
        valores.put("nick",registroDTO.getNick());
        valores.put("nombre",registroDTO.getNombre());
        valores.put("email",registroDTO.getEmail());
        valores.put("contrasena",registroDTO.getContrasena());
        valores.put("estado",1);
        database.insert("usuario", "nick, nombre, email, contrasena, estado" ,valores );
    }



    public RegistroDTO buscarID(RegistroDTO registroDTO){
        RegistroDTO dto = null;
        Cursor c = null;
        c = database.query("usuario",new String[]{"id_usuario","nick","nombre","email","contrasena"},"id_usuario = ?",new String[]{String.valueOf(registroDTO.getId_usuario())},null,null,null);
        if(c.moveToFirst()){
            dto = new RegistroDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
