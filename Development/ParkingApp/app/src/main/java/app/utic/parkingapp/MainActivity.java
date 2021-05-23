package app.utic.parkingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String usuario; //se declara variable a recibir usuario del login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = this.getIntent().getExtras(); //recupera datos adjuntos al intent
        if(bundle != null){
            usuario = bundle.getString("usu"); //carga en la variable usuario el nombre del usuario logueado
        }
        //Toast.makeText(getApplicationContext(), "Sesión de "+usuario+" iniciada.",Toast.LENGTH_LONG).show();

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.usuario) {
            Intent siguiente = new Intent(this, EmpleadosActivity.class);
            startActivity(siguiente);
        } else if (id == R.id.marcas) {

            Intent siguiente = new Intent(this, Marcas3Activity.class);
            startActivity(siguiente);

        }else if (id == R.id.zona) {

            Intent siguiente = new Intent(this, ZonaActivity.class);
            startActivity(siguiente);

        }else if (id == R.id.vehiculo) {

            Intent siguiente = new Intent(this, VehiculoActivity.class);
            startActivity(siguiente);

        }else if (id == R.id.tipobahia) {

            Intent siguiente = new Intent(this, TipobahiaActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.tipoveh) {

            Intent siguiente = new Intent(this, TipovehiculoActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.clientes) {

            Intent siguiente = new Intent(this, ClientesActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.entrada) {

            Intent siguiente = new Intent(this, EntradaActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.salida) {

            Intent siguiente = new Intent(this, SalidaActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.cobros) {

            Intent siguiente = new Intent(this, CobrosActivity.class);
            startActivity(siguiente);

        } else if (id == R.id.acerca) {

            Intent siguiente = new Intent(this, AcercaActivity.class);
            startActivity(siguiente);

        }else if (id == R.id.sesion) {





                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Esta seguro que desea Cerrar la Sesion?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();










            //finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
