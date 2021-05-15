package app.utic.parkingapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table marca(mar_codigo integer primary key autoincrement, mar_descripcion text)");
        BaseDeDatos.execSQL("create table empleado(emp_codigo integer primary key autoincrement, emp_ci integer, emp_nombre text, emp_apellido text, emp_salario integer, emp_telefono, emp_usuario text, emp_clave text)");
        BaseDeDatos.execSQL("insert into empleado values(01,'1234567', 'JUAN', 'PEREZ', '1234567','0971123456', 'admin','123')");
        BaseDeDatos.execSQL("create table cliente(cli_codigo integer primary key autoincrement, cli_ci integer, cli_nombre text, cli_apellido text, cli_ruc text, cli_telefono integer)");
        BaseDeDatos.execSQL("create table producto(pro_codigo integer primary key autoincrement, pro_descripcion text, mar_codigo integer, pro_costo integer, pro_precio integer)");
        BaseDeDatos.execSQL("create table presupuesto(pre_codigo integer primary key autoincrement, pre_fecha date, emp_usuario text, cli_codigo integer)");
        BaseDeDatos.execSQL("create table presupuesto_detalle(pdet_codigo integer primary key autoincrement, pre_codigo integer, pro_codigo integer, pdet_cantidad integer, pdet_precio integer, pdet_subtotal integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il) {

    }
}
