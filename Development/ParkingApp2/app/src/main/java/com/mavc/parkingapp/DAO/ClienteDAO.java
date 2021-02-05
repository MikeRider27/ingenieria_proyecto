package com.mavc.parkingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mavc.parkingapp.DTO.ClienteDTO;
import com.mavc.parkingapp.Database;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private SQLiteDatabase database;
    private Database dbHelper;
    private Context context;

    public ClienteDAO(Context context) {
        this.context = context;
        dbHelper = new Database(context);
        database = dbHelper.getWritableDatabase();
    }

    public void agregar(ClienteDTO clienteDTO){
        ContentValues valores = new ContentValues();
        valores.put("cedula",clienteDTO.getCedula());
        valores.put("nombre",clienteDTO.getNombre());
        valores.put("celular",clienteDTO.getCelular());
        valores.put("direccion",clienteDTO.getDireccion());
        valores.put("email",clienteDTO.getEmail());
        database.insert("cliente", "cedula, nombre, celular, direccion, email " ,valores );
    }

    public void editar(ClienteDTO clienteDTO){
        ContentValues valores = new ContentValues();
        valores.put("cedula",clienteDTO.getCedula());
        valores.put("nombre",clienteDTO.getNombre());
        valores.put("celular",clienteDTO.getCelular());
        valores.put("direccion",clienteDTO.getDireccion());
        valores.put("email",clienteDTO.getEmail());
        database.update("cliente" ,valores ,"id_cliente = "+clienteDTO.getId_cliente(),null);
    }

    public void eliminar(ClienteDTO clienteDTO){
        database.delete("cliente","id_cliente = "+clienteDTO.getId_cliente(),null);
    }

    public List<ClienteDTO> listar(){
        List<ClienteDTO> lista = new ArrayList<>();
        Cursor c = null;
        database = dbHelper.getReadableDatabase();
        c = database.query("cliente",new String[]{"id_cliente","cedula","nombre","celular","direccion","email"},null,null,null,null," id_cliente ASC ");


        while (c.moveToNext())
        {
            lista.add(new ClienteDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5)));

        }

        return lista;
    }

    public ClienteDTO buscarID(ClienteDTO clienteDTO){
        ClienteDTO dto = null;
        Cursor c = null;
        c = database.query("cliente",new String[]{"id_cliente","cedula","nombre","celular","direccion","email"},"id_cliente = ?",new String[]{String.valueOf(clienteDTO.getId_cliente())},null,null,null);
        if(c.moveToFirst()){
            dto = new ClienteDTO(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5));
        }
        return dto;
    }

    public void cerrarConexion(){
        dbHelper.close();
    }
}
