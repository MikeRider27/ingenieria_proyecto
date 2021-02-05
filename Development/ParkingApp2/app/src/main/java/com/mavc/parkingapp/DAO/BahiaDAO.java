package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mavc.parkingapp.DTO.BahiaDTO;
import com.mavc.parkingapp.DTO.TipobahiaDTO;
import com.mavc.parkingapp.DTO.ZonaDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class BahiaDAO {
    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public BahiaDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(BahiaDTO bahiaDTO){
        ContentValues valores = new ContentValues();
        valores.put("nom_bahia",bahiaDTO.getNom_bahia());
        valores.put("id_tipbahia",bahiaDTO.getTipobahiaDTO().getId_tbahia());
        valores.put("id_zona",bahiaDTO.getZonaDTO().getId_zona());
        database.insert("bahias", "nom_bahia,id_tipbahia,id_zona" ,valores );
    }



    public List<BahiaDTO> listar(){
        List<BahiaDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        //c = database.query("zona",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");
        c = database.query("bahias",new String[]{"id_bahia","nom_bahia", "id_tipbahia", "id_zona"},null,null,null,null," id_bahia ASC ");


        while (c.moveToNext())
        {
            ZonaDAO zdao = new ZonaDAO(context);
            TipobahiaDAO tbdao = new TipobahiaDAO(context);
            ZonaDTO zdto = zdao.buscarID(new ZonaDTO(c.getInt(3),null));
            TipobahiaDTO tbdto = tbdao.buscarID(new TipobahiaDTO(c.getInt(2),null));
            lista.add(new BahiaDTO(
                    c.getInt(0),
                    c.getString(1),
                    tbdto,
                    zdto
            ));

        }

        return lista;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }


}
