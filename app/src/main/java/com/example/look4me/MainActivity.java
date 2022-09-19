package com.example.look4me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    Button btnlocalizacao, btnnome, btnsobrenome, btnpais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        IniciarComponentes();


        btnlocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(map);
            }
        });


        btnnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nome = new Intent(getApplicationContext(), Nome.class);
                startActivity(nome);
            }
        });

        btnsobrenome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sobrenome = new Intent(getApplicationContext(), Sobrenome.class);
                startActivity(sobrenome);
            }
        });

        btnpais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pais = new Intent(getApplicationContext(), Pais.class);
                startActivity(pais);
            }
        });
        }

    private void IniciarComponentes(){
        btnlocalizacao = findViewById(R.id.btnlocalizacao);
        btnnome = findViewById(R.id.btnnome);
        btnsobrenome = findViewById(R.id.btnsobrenome);
        btnpais = findViewById(R.id.btnpais);
    }
}


