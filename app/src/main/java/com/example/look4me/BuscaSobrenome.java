package com.example.look4me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscaSobrenome extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText sobrenomeSobresobrenome;
    private TextView sobrenome;
    private TextView paisSm;
    private TextView msgSm;

    public String  stringId = null, stringSobrenome = null, stringPaisSm = null, stringMsgSm = null;

    Button btnVoltar;
    Button btnBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca_sobrenome);
        sobrenomeSobresobrenome = findViewById(R.id.inputNome);
        sobrenome = findViewById(R.id.txtmain1);
        paisSm = findViewById(R.id.txtmain2);
        msgSm = findViewById(R.id.txtmain3);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnBusca = findViewById(R.id.btnVoltar);


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


    public void buscaSobrenome(View view) {
        String queryString = sobrenomeSobresobrenome.getText().toString();
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
            paisSm.setText(R.string.vazio);
            sobrenome.setText(R.string.carregando);
        } else {
            if (queryString.length() == 0) {
                paisSm.setText(R.string.vazio);
                sobrenome.setText(R.string.termo_vazio);
            } else {
                paisSm.setText(" ");
                sobrenome.setText(R.string.sem_conexao);
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
        return new LoadSobrenome(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String id = null;
            String nome = null;
            String paissm = null;
            String msgsm = null;


            while (i < itemsArray.length() &&
                    (nome == null && paissm == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                try {
                    nome = volumeInfo.getString("nome");
                    paissm = volumeInfo.getString("pais");
                    msgsm = volumeInfo.getString("menasgem");
                    id = volumeInfo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }

        } catch (Exception e) {
            sobrenome.setText(R.string.vazio);
            paisSm.setText(R.string.vazio);
            msgSm.setText(R.string.vazio);

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
        setContentView(R.layout.activity_busca_sobrenome);

    }

    public void TelaMain(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}