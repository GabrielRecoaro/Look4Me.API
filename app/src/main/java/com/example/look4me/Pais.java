package com.example.look4me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Pais {

    private String id;
    private String pais;
    private String continente;
    private String smComum;

    public Pais(String idPais, String pais, String continente, String smComum) {
        this.id=idPais;
        this.pais=pais;
        this.continente=continente;
        this.smComum=smComum;

    }

    public Pais() {

    }

    public String getId() {
        return id;
    }

    public String getPais() {
        return pais;

    }

    public String getContinente() {
        return continente;

    }
    public String getSmComum() {
        return smComum;
    }

    public void setId(String id){
        this.id = id;

    }

    public void setPais(String pais){
        this.pais = pais;
    }

    public void setContinente(String continente){
        this.continente = pais;
    }

    public void setSmComum(String titulo){
        this.smComum = smComum;
    }

}