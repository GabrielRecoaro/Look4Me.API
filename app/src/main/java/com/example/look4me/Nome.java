package com.example.look4me;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Nome extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

        private String id;
        private String nome;
        private String pais;
        private String msgNm;

        public Nome(String idNome, String nome, String pais, String msgNm) {
            this.id=idNome;
            this.nome=nome;
            this.pais=pais;
            this.msgNm=msgNm;

        }

        public Nome() {
        }

        public String getId() {
            return id;
        }
        public String getNome() {
            return nome;
        }
        public String getPais() {

            return pais;
        }
        public String getMsgNm() {

        return msgNm;

        }



        public void setId(String id){
            this.id = id;
        }
        public void setNome(String nome){
            this.nome = nome;
        }
        public void setPais(String pais){
            this.pais = pais;
        }

        public void setMsgNm(String msgNm){
        this.msgNm = msgNm;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_nome);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void TelaMain(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}