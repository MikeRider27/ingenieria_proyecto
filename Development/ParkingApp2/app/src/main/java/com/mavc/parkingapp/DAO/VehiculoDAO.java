package com.mavc.parkingapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.ClienteDTO;
import com.mavc.parkingapp.DTO.MarcasDTO;
import com.mavc.parkingapp.DTO.TipovehiculoDTO;
import com.mavc.parkingapp.DTO.VehiculoDTO;
import com.mavc.parkingapp.DTO.ZonaDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public VehiculoDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<VehiculoDTO> listar(){
        List<VehiculoDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        //c = database.query("zona",new String[]{"codigo","descripcion"},null,null,null,null," codigo ASC ");
        c = database.query("vehiculo",new String[]{"chapa","color", "id_tipvehiculo", "id_marca","id_cliente"},null,null,null,null," chapa ASC ");


        while (c.moveToNext())
        {
            TipovehiculoDAO tvdao = new TipovehiculoDAO(context);
            MarcasDAO mdao = new MarcasDAO(context);
            ClienteDAO cdao = new ClienteDAO(context);

            TipovehiculoDTO tvdto = tvdao.buscarID(new TipovehiculoDTO(c.getInt(2)));
            MarcasDTO mdto = mdao.buscarID(new MarcasDTO(c.getInt(3)));
            ClienteDTO cdto = cdao.buscarID(new ClienteDTO(c.getInt(4)));

            lista.add(new VehiculoDTO(
                    c.getString(0),
                    c.getString(1),
                    tvdto,
                    mdto,
                    cdto
            ));

        }

        return lista;
    }


    public VehiculoDTO buscarID(VehiculoDTO vehiculoDTO){
        VehiculoDTO dto = new VehiculoDTO(vehiculoDTO.getChapa());
        Cursor c = null;
        c = database.query("vehiculo",new String[]{"chapa","color", "id_tipvehiculo", "id_marca","id_cliente"},"chapa = '"+vehiculoDTO.getChapa()+"'",null,null,null,null);
        if(c.moveToFirst()){
            TipovehiculoDAO tvdao = new TipovehiculoDAO(context);
            MarcasDAO mdao = new MarcasDAO(context);
            ClienteDAO cdao = new ClienteDAO(context);

            TipovehiculoDTO tvdto = tvdao.buscarID(new TipovehiculoDTO(c.getInt(2)));
            MarcasDTO mdto = mdao.buscarID(new MarcasDTO(c.getInt(3)));
            ClienteDTO cdto = cdao.buscarID(new ClienteDTO(c.getInt(4)));

            dto = new VehiculoDTO(c.getString(0),
                    c.getString(1),
                    tvdto,
                    mdto,
                    cdto);
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
