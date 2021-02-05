package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class MarcasDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public MarcasDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(MarcasDTO marcasDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",marcasDTO.getNom_marca());
        database.insert("marca", "descripcion" ,valores );
    }

    public void editar(MarcasDTO marcasDTO){
        ContentValues valores = new ContentValues();
        valores.put("descripcion",marcasDTO.getNom_marca());
       // database.update("marca",valores,"codigo =?",new String[]{String.valueOf(marcasDTO.getId_marca())});
        database.update("marca",valores,"codigo = "+marcasDTO.getId_marca(),null);
    }

    public void eliminar(MarcasDTO marcasDTO){
        database.delete("marca","codigo = "+marcasDTO.getId_marca(),null);
    }

    public List<MarcasDTO> listar(){
        List<MarcasDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        //c = database.query("marca",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");
        c = database.query("marca",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");


        while (c.moveToNext())
        {
            lista.add(new MarcasDTO(c.getInt(0),c.getString(1)));

        }

        return lista;
    }

    public MarcasDTO buscarID(MarcasDTO marcasDTO){
        MarcasDTO dto = null;
        Cursor c = null;
        c = database.query("marca",new String[]{"codigo","descripcion"},"codigo = ?",new String[]{String.valueOf(marcasDTO.getId_marca())},null,null,null);
        if(c.moveToFirst()){
            dto = new MarcasDTO(c.getInt(0),c.getString(1));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
