package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.TipobahiaDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class TipobahiaDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public TipobahiaDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(TipobahiaDTO tipobahiaDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",tipobahiaDTO.getNom_tbahia());
        database.insert("tipo_bahias", "descripcion" ,valores );
    }

    public void editar(TipobahiaDTO tipobahiaDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",tipobahiaDTO.getNom_tbahia());
        // database.update("marca",valores,"codigo =?",new String[]{String.valueOf(marcasDTO.getId_marca())});
        database.update("tipo_bahias",valores,"codigo = "+tipobahiaDTO.getId_tbahia(),null);
    }

    public void eliminar(TipobahiaDTO tipobahiaDTO){
        database.delete("tipo_bahias","codigo = "+tipobahiaDTO.getId_tbahia(),null);
    }

    public List<TipobahiaDTO> listar(){
        List<TipobahiaDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        //c = database.query("zona",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");
        c = database.query("tipo_bahias",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");


        while (c.moveToNext())
        {
            lista.add(new TipobahiaDTO(c.getInt(0),c.getString(1)));

        }

        return lista;
    }

    public TipobahiaDTO buscarID(TipobahiaDTO tipobahiaDTO){
        TipobahiaDTO dto = null;
        Cursor c = null;
        c = database.query("tipo_bahias",new String[]{"codigo","descripcion"},"codigo = ?",new String[]{String.valueOf(tipobahiaDTO.getId_tbahia())},null,null,null);
        if(c.moveToFirst()){
            dto = new TipobahiaDTO(c.getInt(0),c.getString(1));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
