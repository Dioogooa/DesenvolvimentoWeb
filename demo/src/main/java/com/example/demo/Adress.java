package com.example.demo;

public class Adress {

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;

    public Adress(String cep, String rua, String bairro, String cidade) {
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public String getCep(){return cep;}
    public void setCep() {this.cep = cep;}

    public String getRua(){return rua;}
    public void setRua() {this.rua = rua;}

    public String getBairro(){return bairro;}
    public void setBairro() {this.bairro = bairro;}

    public String getCidade(){return cidade;}
    public void setCidade() {this.cidade = cidade;}



    
}
