package com.mavc.parkingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    //CREACION DE BASE DE DATOS
    public Database(Context context) { super(context,"parking.bd", null,1); }


    @Override public void onCreate(SQLiteDatabase database)
    {
        //creacion de tablas
        database.execSQL("create table zona(codigo integer primary key autoincrement,descripcion text);");
        database.execSQL("create table tipo_bahias(codigo integer primary key autoincrement,descripcion text);");
        database.execSQL("create table marca(codigo integer primary key autoincrement,descripcion text)");
        database.execSQL("create table tipo_vehiculo(id_tipvehiculo integer primary key autoincrement,nom_tipvehiculo text,tarifa_hora text,tarifa_dia text);");

        database.execSQL("create table bahias(id_bahia integer primary key autoincrement,nom_bahia text,id_tipbahia integer,id_zona integer);");

        database.execSQL("create table cliente(id_cliente integer primary key autoincrement,cedula text,nombre text,celular text,direccion text,email text);");


        database.execSQL("create table vehiculo(chapa text primary key ,color text,id_tipvehiculo integer,id_marca integer,id_cliente integer)");

        database.execSQL("create table entrada_salida(id_entradasalida integer primary key autoincrement,chapa text, fecha_entrada text,hora_entrada text, fecha_salida text,hora_salida text,id_bahia integer,monto integer,tiempo_totaL text,observaciones text,estado integer,id_usuario integer)");

        database.execSQL("create table usuario(id_usuario integer primary key autoincrement,nick text,nombre text,contrasena text,estado integer)");
        database.execSQL("insert into usuario(nick,nombre,contrasena,estado) values('admin','Miguel Villalba','123', 1)");

        database.execSQL("insert into zona(descripcion) values('Zona A')");
        database.execSQL("insert into tipo_bahias(descripcion) values('Tipo A')");
        database.execSQL("insert into marca(descripcion) values('Toyota')");
        database.execSQL("insert into tipo_vehiculo(nom_tipvehiculo,tarifa_hora,tarifa_dia) values('Auto','5000','120000')");
        database.execSQL("insert into bahias(nom_bahia, id_tipbahia, id_zona) values('Bahia Norte',1,1)");
        database.execSQL("insert into cliente(cedula,nombre,celular,direccion,email) values('123456','Miguel Villalba','0955 123 123','Villa Elisa', 'mvillalba@utic.com')");
        database.execSQL("insert into vehiculo(chapa,color,id_tipvehiculo,id_marca,id_cliente) values('BRL773','Rojo',1, 1,1)");
    }


    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

