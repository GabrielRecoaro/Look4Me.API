package com.example.look4me;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static final String tbSobrenome = "tbSobrenome";
    public static final String IdSobrenome = "idsobrenome";
    public static final String Sobrenome = "sobrenome";
    public static final String PaisSm = "pais";
    public static final String MsgSm = "mensagem";

    public static final String tbNome = "tbNome";
    public static final String IdNome = "idnome";
    public static final String Nome = "nome";

    public static final String tbPais = "tbPais";
    public static final String IdPais = "idpais";
    public static final String Pais = "pais";
    public static final String Continente = "continente";
    public static final String SmComum = "smComum";


    private static final String DATABASE_Nome = "Look4Me.db";
    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DATABASE_Nome, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CriaSobrenome = "create table " + tbSobrenome + "( "
                + IdSobrenome+ " text primary key AUTOINCREMENT NOT NULL, "
                + Sobrenome + " text not null,"
                + PaisSm +  " text not null,"
                + MsgSm + " text not null);";
        db.execSQL(CriaSobrenome);

        String CriaNome = "create table " + tbNome + "( "
                + IdNome+ " text primary key AUTOINCREMENT NOT NULL, "
                + Nome + " text not null,"
                + PaisSm +  " text not null,"
                + MsgSm + " text not null);";
        db.execSQL(CriaNome);

        String CriaPais = "create table " + tbPais + "( "
                + IdPais+ " text primary key AUTOINCREMENT NOT NULL, "
                + Pais + " text not null,"
                + Continente +  " text not null,"
                + SmComum + " text not null);";
        db.execSQL(CriaPais);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addSobrenome(Sobrenome sobrenome){
        long resultado;
        //estancia para escrita no banco
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(IdSobrenome, sobrenome.getId());
        values.put(Sobrenome, sobrenome.getSobrenome());
        values.put(PaisSm, sobrenome.getPais());
        values.put(MsgSm, sobrenome.getMsgNm());


        //inseri no banco
        resultado = db.insert(tbSobrenome, null, values);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }else{
            return "Registro Inserido com sucesso";
        }
    }

    Sobrenome buscaSobrenome(String id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(tbSobrenome,
                new String[]{IdSobrenome, Sobrenome},
                IdSobrenome+"=?",new String[]{String.valueOf(id)},null, null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
        }

        else if(cursor.getCount() == 0){
            Sobrenome sobrenomeEspecifico = new Sobrenome(
                    "naoExiste",
                    "naoExiste",
                    "naoExiste",
                    "naoExiste");
            return sobrenomeEspecifico;
        }


        Sobrenome sobrenomeEspecifico= new Sobrenome(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        return sobrenomeEspecifico;

    }


    public String addNome(Nome nome){
        long resultado;
        //estancia para escrita no banco
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(IdNome, nome.getId());
        values.put(Nome, nome.getNome());
        values.put(PaisSm, nome.getPais());
        values.put(MsgSm, nome.getMsgNm());


        //inseri no banco
        resultado = db.insert(tbNome, null, values);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }else{
            return "Registro Inserido com sucesso";
        }
    }

    Nome buscaNome(String id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(tbNome,
                new String[]{IdNome, Nome},
                IdNome+"=?",new String[]{String.valueOf(id)},null, null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
        }

        else if(cursor.getCount() == 0){
            Nome nomeEspecifico = new Nome(
                    "naoExiste",
                    "naoExiste",
                    "naoExiste",
                    "naoExiste");
            return nomeEspecifico;
        }


        Nome nomeEspecifico= new Nome(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));;
        return nomeEspecifico;

    }

    public String addPais(Pais pais){
        long resultado;
        //estancia para escrita no banco
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(IdPais, pais.getId());
        values.put(Pais, pais.getPais());
        values.put(Continente, pais.getContinente());
        values.put(SmComum, pais.getSmComum());


        //inseri no banco
        resultado = db.insert(tbPais, null, values);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }else{
            return "Registro Inserido com sucesso";
        }
    }

    Pais buscaPais(String id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(tbPais,
                new String[]{IdPais, Pais},
                IdPais+"=?",new String[]{String.valueOf(id)},null, null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
        }

        else if(cursor.getCount() == 0){
            Pais paisEspecifico = new Pais(
                    "naoExiste",
                    "naoExiste",
                    "naoExiste",
                    "naoExiste");
            return paisEspecifico;
        }


        Pais paisEspecifico= new Pais(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        return paisEspecifico;

    }



}
