package com.antonio.bandbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int duracion = 2500;//lo segundos que carga la pantalla

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Esto se ejecuta pasando los segundos establecidos
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                //nos dirige desde el MainActivity hasta la siguiente actvidad llamada Login
            }
        },duracion);
    }
}