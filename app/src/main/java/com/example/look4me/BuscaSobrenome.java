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

    public String  stringId = null, stringTitulo = null, stringAutor = null, stringLink = null;



    Button btnVoltar, btnBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_busca_sobrenome);
        sobrenomeSobresobrenome = findViewById(R.id.inputNome);
        sobrenome = findViewById(R.id.txtSobrenome);
        paisSm = findViewById(R.id.msgSm);
        msgSm = findViewById(R.id.msgSm);
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


    public void buscaSobreomes(View view) {
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
        return new LoadSobresobrenomes(this, queryString);
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


                sobrenome.setText(titulo);


                autor = autor.replaceAll("\\[", "");
                autor = autor.replaceAll("\\]", "");
                autor = autor.replaceAll("\\\"", "");
                paisSm.setText(autor);


                msgSm.setText("N° de páginas: " + pag);


                if(cat != null){
                    cat = cat.replaceAll("\\[", "");
                    cat = cat.replaceAll("\\]", "");
                    cat = cat.replaceAll("\\\"", "");
                    sobrenomeCat.setText("Categoria: " + cat);
                } else {
                    sobrenomeCat.setText("Categoria: Não Identificado");
                }


                stringId = id;
                stringTitulo = titulo;
                stringAutor = autor;
                stringLink = link;


                buttonSalvar.setOnClickListener(v ->{
                    Sobresobrenome sobresobrenomeSalvo = new Sobresobrenome(
                            stringId,
                            stringTitulo,
                            stringAutor,
                            stringLink
                    );
                    SobresobrenomeDAO sobresobrenomeDAO = new SobresobrenomeDAO(getApplicationContext());
                    sobresobrenomeDAO.cadastrarSobresobrenome(sobresobrenomeSalvo);


                    Gson gson = new Gson();
                    String usuarioJson = lerDados();
                    Usuario usuario = gson.fromJson(usuarioJson, Usuario.class);


                    int fkUsuario = usuario.getId();


                    Usuario usuarioFavorito = new Usuario(fkUsuario);
                    Sobresobrenome sobresobrenomeFavorito = new Sobresobrenome(stringId);


                    FavoritosDAO favoritos = new FavoritosDAO(getApplicationContext());


                    try{
                        favoritos.cadastrarFavorito(usuarioFavorito, sobresobrenomeFavorito);
                        Toast.makeText(getApplicationContext(), "Sobresobrenome favoritado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), FavoritosActivity.class));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } else {
                sobrenome.setText(R.string.sem_resultado);
                paisNm.setText(R.string.vazio);
                Toast.makeText(getApplicationContext(),"else", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            sobrenome.setText(R.string.vazio);
            paisNm.setText(R.string.vazio);
            msgNm.setText(R.string.vazio);
            stringLink = null;
            Toast.makeText(getApplicationContext(),"Nenhum sobresobrenome encontrado", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}

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