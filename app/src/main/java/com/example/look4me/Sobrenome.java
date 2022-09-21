package com.example.look4me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sobrenome extends AppCompatActivity {

    private String id;
    private String sobrenome;
    private String pais;
    private String msgNm;

    public Sobrenome(String idSobrenome, String sobrenome, String pais, String msgNm) {
        this.id=idSobrenome;
        this.sobrenome=sobrenome;
        this.pais=pais;
        this.msgNm=msgNm;

    }

    public Sobrenome() {
    }

    public String getId() {

        return id;
    }
    public String getSobrenome() {

        return sobrenome;
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
    public void setSobrenome(String sobrenome){

        this.sobrenome = sobrenome;
    }

    public void setPais(String pais){
        this.pais = pais;
    }

    public void setMsgNm(String msgNm){
        this.msgNm = msgNm;
    }

}