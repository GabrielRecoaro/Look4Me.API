package com.example.look4me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscaNome extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText nomeNome;
    private TextView nome;
    private TextView paisNm;
    private TextView msgNm;

    public String  stringId = null, stringNome = null, stringPaisnm = null, stringLink = null;

    Button btnVoltar;
    Button btnBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca_nome);
        nomeNome = findViewById(R.id.inputNome);
        nome = findViewById(R.id.txtmain1);
        paisNm = findViewById(R.id.txtmain2);
        msgNm = findViewById(R.id.txtmain3);

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


    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    public void buscaNome(View view) {
        String queryString = nomeNome.getText().toString();
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
            paisNm.setText(R.string.vazio);
            nome.setText(R.string.carregando);
        } else {
            if (queryString.length() == 0) {
                paisNm.setText(R.string.vazio);
                nome.setText(R.string.termo_vazio);
            } else {
                paisNm.setText(" ");
                nome.setText(R.string.sem_conexao);
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
        return new LoadNome(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String id = null;
            String nome = null;
            String paisnm = null;
            String msgnm = null;



            while (i < itemsArray.length() &&
                    (paisnm == null && nome == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                try {
                    nome = volumeInfo.getString("nome");
                    paisnm = volumeInfo.getString("pais");
                    msgnm = volumeInfo.getString("mensagem");

                    id = volumeInfo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }

        } catch (Exception e) {
            nome.setText(R.string.vazio);
            paisNm.setText(R.string.vazio);
            msgNm.setText(R.string.vazio);
            stringLink = null;
            Toast.makeText(getApplicationContext(),"Nenhum sobrenome encontrado", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_nome);
    }

    public void TelaMain(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}