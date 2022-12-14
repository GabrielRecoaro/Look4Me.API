package com.example.look4me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BuscaPais extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText inputNome;
    private TextView Pais;
    private TextView Continente;
    private TextView SmComum;
    public String  stringId = null, stringNome = null, stringPais = null, stringContinente = null;

    Button btnVoltar;
    Button btnBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca_pais);
        inputNome = findViewById(R.id.inputNome);
        Pais = findViewById(R.id.txtmain1);
        Continente = findViewById(R.id.txtmain2);
        SmComum = findViewById(R.id.txtmain3);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnBusca = findViewById(R.id.btnBusca);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void buscaPais(View view) {

        Pais = findViewById(R.id.txtmain1);
        Continente = findViewById(R.id.txtmain2);
        SmComum = findViewById(R.id.txtmain3);

        String queryString = inputNome.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            Continente.setText(R.string.vazio);
            SmComum.setText(R.string.vazio);
            Pais.setText(R.string.carregando);
        } else {
            if (queryString.length() == 0) {
                Continente.setText(R.string.vazio);
                SmComum.setText(R.string.vazio);
                Pais.setText(R.string.termo_vazio);
            } else {
                Continente.setText(" ");
                SmComum.setText(" ");
                Pais.setText(R.string.sem_conexao);
            }
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new LoadPais(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String id = null;
            String pais = null;
            String continente = null;
            String msgSm = null;



            while (i < itemsArray.length() &&
                    (continente == null && pais == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    pais = volumeInfo.getString("nome");
                    continente = volumeInfo.getString("continente");
                    msgSm = volumeInfo.getString("mensagem");
                    id = volumeInfo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            if (pais != null && continente != null) {

                inputNome.setText(Pais); //Final

                continente = continente.replaceAll("\\[", "");
                continente = continente.replaceAll("\\]", "");
                continente = continente.replaceAll("\\\"", "");
                Continente.setText(continente);

                stringId = id;
                stringPais = pais;
                stringContinente = continente;

            }

        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader <String> loader) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_pais);

    }


    public void TelaMain(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}