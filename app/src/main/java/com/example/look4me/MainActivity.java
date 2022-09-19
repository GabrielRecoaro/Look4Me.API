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

                Intent info = new Intent(getApplicationContext(), Localizacao.class);
                startActivity(info);
            }
        });

        btnnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(getApplicationContext(), Nome.class);
                startActivity(info);
            }
        });

        btnsobrenome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(getApplicationContext(), Sobrenome.class);
                startActivity(info);
            }
        });

        btnpais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info = new Intent(getApplicationContext(), Pais.class);
                startActivity(info);
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


