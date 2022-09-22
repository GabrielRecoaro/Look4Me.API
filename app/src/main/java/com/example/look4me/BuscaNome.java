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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BuscaNome extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText nomeSobrenome;
    private TextView nome;
    private TextView paisNm;
    private TextView msgNm;

    public String  stringId = null, stringTitulo = null, stringAutor = null, stringLink = null;
    private static final String FILE_NAME = "usuarioLogado.json";


    ImageButton btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca_nome);
        nomeSobrenome = findViewById(R.id.inputNome);
        nome = findViewById(R.id.txtSobrenome);
        paisNm = findViewById(R.id.txtContinente);
        msgNm = findViewById(R.id.msgSm);
        btnVoltar = findViewById(R.id.btnVoltar);


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


    private String lerDados() {
        FileInputStream fis;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    public void buscaSobrenomes(View view) {
        String queryString = nomeSobrenome.getText().toString();
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
        return new LoadSobrenomes(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String id = null;
            String titulo = null;
            String autor = null;
            String pag = null;
            String cat = null;
            String link = null;


            while (i < itemsArray.length() &&
                    (autor == null && titulo == null)) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                try {
                    titulo = volumeInfo.getString("title");
                    autor = volumeInfo.getString("authors");
                    pag = volumeInfo.getString("pageCount");
                    cat = volumeInfo.getString("categories");
                    link = volumeInfo.getString("previewLink");
                    id = volumeInfo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            if (titulo != null && autor != null) {


                nome.setText(titulo);


                autor = autor.replaceAll("\\[", "");
                autor = autor.replaceAll("\\]", "");
                autor = autor.replaceAll("\\\"", "");
                paisNm.setText(autor);


                msgNm.setText("N° de páginas: " + pag);


                if(cat != null){
                    cat = cat.replaceAll("\\[", "");
                    cat = cat.replaceAll("\\]", "");
                    cat = cat.replaceAll("\\\"", "");
                    nomeCat.setText("Categoria: " + cat);
                } else {
                    nomeCat.setText("Categoria: Não Identificado");
                }


                stringId = id;
                stringTitulo = titulo;
                stringAutor = autor;
                stringLink = link;


                buttonSalvar.setOnClickListener(v ->{
                    Sobrenome sobrenomeSalvo = new Sobrenome(
                            stringId,
                            stringTitulo,
                            stringAutor,
                            stringLink
                    );
                    SobrenomeDAO sobrenomeDAO = new SobrenomeDAO(getApplicationContext());
                    sobrenomeDAO.cadastrarSobrenome(sobrenomeSalvo);


                    Gson gson = new Gson();
                    String usuarioJson = lerDados();
                    Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);


                    int fkUsuario = usuario.getId();


                    Usuario usuarioFavorito = new Usuario(fkUsuario);
                    Sobrenome sobrenomeFavorito = new Sobrenome(stringId);


                    FavoritosDAO favoritos = new FavoritosDAO(getApplicationContext());


                    try{
                        favoritos.cadastrarFavorito(usuarioFavorito, sobrenomeFavorito);
                        Toast.makeText(getApplicationContext(), "Sobrenome favoritado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), FavoritosActivity.class));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } else {
                nome.setText(R.string.sem_resultado);
                paisNm.setText(R.string.vazio);
                Toast.makeText(getApplicationContext(),"else", Toast.LENGTH_SHORT).show();


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