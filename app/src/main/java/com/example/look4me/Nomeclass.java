package com.example.look4me;

public class Nomeclass {
    private String Id;
    private String Nome;

    public Nomeclass(String IdNome, String Nome) {
        this.Id=IdNome;
        this.Nome=Nome;

    }

    public Nomeclass() {

    }

    //métodos get
    public String getId() {
        return Id;
    }
    public String getNome() {
        return Nome;
    }

    //métodos set
    public void setId(String Id){
        this.Id = Id;
    }
    public void setPaisNm(String Nome){
        this.Nome = Nome;
    }

}


}
