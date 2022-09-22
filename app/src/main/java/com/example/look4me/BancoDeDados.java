package com.example.look4me;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {
    public static final String Tabela_Nome = "tbNome";
    public static final String Coluna_Id = "IdNome";
    public static final String Coluna_Nome = "Nome";
    public static final String Coluna_PaisNm = "PaisNm";
    public static final String Coluna_MsgNm = "lv_titulo";


    private static final String DATABASE_Nome = "DBLook4MeAPI.db";
    private static final int DATABASE_VERSION = 1;

    public BancoDeDados(Context context) {
        super(context, DATABASE_Nome, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CriaNome = "create table " + Tabela_Nome + "( "
                + Coluna_Id + " text primary key AUTOINCREMENT NOT NULL, "
                + Coluna_Nome + " text not null,"
                + Coluna_PaisNm + "text not null,"
                + Coluna_MsgNm + "text not null);";
        db.execSQL(CriaNome);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addLivro(Nomeclass nome){
        long resultado;
        //estancia para escrita no banco
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(Coluna_Id, Nomeclass.getId());
        values.put(Coluna_PaisNm, Nomeclass.getNome());


        //inseri no banco
        resultado = db.insert(Tabela_Nome, null, values);
        db.close();

        if (resultado ==-1) {
            return "Erro ao inserir registro";
        }else{
            return "Registro Inserido com sucesso";
        }
    }

    Nomeclass buscanome(String id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Tabela_Nome,
                new String[]{Coluna_Id, Coluna_Nome},
                Coluna_Id+"=?",new String[]{String.valueOf(id)},null, null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
        }

        else if(cursor.getCount() == 0){
            Nomeclass NomeEspecifico = new Nomeclass(
                    "naoExiste",
                    "naoExiste");
            return NomeEspecifico;
        }


        Nomeclass NomeEspecifico= new Nomeclass(
                cursor.getString(0),
                cursor.getString(1));
        return NomeEspecifico;

    }


}
