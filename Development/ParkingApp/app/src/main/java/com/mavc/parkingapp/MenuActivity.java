package com.mavc.parkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      int id = item.getItemId();
        if(id == R.id.nav_usuario){
            Intent i = new Intent(MenuActivity.this, UsuarioActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_marca){
            Intent i = new Intent(MenuActivity.this, MarcaActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_zona){
            Intent i = new Intent(MenuActivity.this, ZonaActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_tipobahia){
            Intent i = new Intent(MenuActivity.this, TipbahiaActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_cliente){
            Intent i = new Intent(MenuActivity.this, ClienteActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_tipovehiculo){
            Intent i = new Intent(MenuActivity.this, TipovehiculoActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_bahia){
            Intent i = new Intent(MenuActivity.this, BahiaActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_vehiculo){
            Intent i = new Intent(MenuActivity.this, VehiculoActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_entrada){
            Intent i = new Intent(MenuActivity.this, EntradaActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_salida){
            Intent i = new Intent(MenuActivity.this, SalidaActivity.class);
            startActivity(i);
        }
        return false;
    }
}
