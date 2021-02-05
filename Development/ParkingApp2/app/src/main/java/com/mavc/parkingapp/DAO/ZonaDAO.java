package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.ZonaDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class ZonaDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public ZonaDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(ZonaDTO zonaDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",zonaDTO.getNom_zona());
        database.insert("zona", "descripcion" ,valores );
    }

    public void editar(ZonaDTO zonaDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",zonaDTO.getNom_zona());
        // database.update("zona",valores,"codigo =?",new String[]{String.valueOf(zonaDTO.getId_zona())});
        database.update("zona",valores,"codigo = "+zonaDTO.getId_zona(),null);
    }

    public void eliminar(ZonaDTO zonaDTO){
        database.delete("zona","codigo = "+zonaDTO.getId_zona(),null);
    }

    public List<ZonaDTO> listar(){
        List<ZonaDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        //c = database.query("zona",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");
        c = database.query("zona",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");


        while (c.moveToNext())
        {
            lista.add(new ZonaDTO(c.getInt(0),c.getString(1)));

        }

        return lista;
    }

    public ZonaDTO buscarID(ZonaDTO zonaDTO){
        ZonaDTO dto = null;
        Cursor c = null;
        c = database.query("zona",new String[]{"codigo","descripcion"},"codigo = ?",new String[]{String.valueOf(zonaDTO.getId_zona())},null,null,null);
        if(c.moveToFirst()){
            dto = new ZonaDTO(c.getInt(0),c.getString(1));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
