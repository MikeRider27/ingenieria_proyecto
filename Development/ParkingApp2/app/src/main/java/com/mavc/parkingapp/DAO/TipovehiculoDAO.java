package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.TipovehiculoDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class TipovehiculoDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public TipovehiculoDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(TipovehiculoDTO tipovehiculoDTO){
        ContentValues valores = new ContentValues();
        valores.put("nom_tipvehiculo",tipovehiculoDTO.getNom_tipvehiculo());
        valores.put("tarifa_hora",tipovehiculoDTO.getTarifa_hora());
        valores.put("tarifa_dia",tipovehiculoDTO.getTarifa_dia());
        database.insert("tipo_vehiculo", "nom_tipvehiculo, tarifa_hora, tarifa_dia" ,valores );
    }

    public void editar(TipovehiculoDTO tipovehiculoDTO){
        ContentValues valores = new ContentValues();
        valores.put("nom_tipvehiculo",tipovehiculoDTO.getNom_tipvehiculo());
        valores.put("tarifa_hora",tipovehiculoDTO.getTarifa_hora());
        valores.put("tarifa_dia",tipovehiculoDTO.getTarifa_dia());
        database.update("tipo_vehiculo" ,valores ,"id_tipvehiculo = "+tipovehiculoDTO.getId_tipvehiculo(),null);
    }

    public void eliminar(TipovehiculoDTO tipovehiculoDTO){
        database.delete("tipo_vehiculo","id_tipvehiculo = "+tipovehiculoDTO.getId_tipvehiculo(),null);
    }

    public List<TipovehiculoDTO> listar(){
        List<TipovehiculoDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        c = database.query("tipo_vehiculo",new String[]{"id_tipvehiculo","nom_tipvehiculo","tarifa_hora","tarifa_dia"},null,null,null,null," id_tipvehiculo ASC ");


        while (c.moveToNext())
        {
            lista.add(new TipovehiculoDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));

        }

        return lista;
    }

    public TipovehiculoDTO buscarID(TipovehiculoDTO tipovehiculoDTO){
        TipovehiculoDTO dto = null;
        Cursor c = null;
        c = database.query("tipo_vehiculo",new String[]{"id_tipvehiculo","nom_tipvehiculo","tarifa_hora","tarifa_dia"},"id_tipvehiculo = ?",new String[]{String.valueOf(tipovehiculoDTO.getId_tipvehiculo())},null,null,null);
        if(c.moveToFirst()){
            dto = new TipovehiculoDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
