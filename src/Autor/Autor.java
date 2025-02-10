package Autor;

import java.util.Date;

public class Autor {
    private int idAutor;
    private static int idCount = 1;
    private String nome;
    private Date dataNascimento;

    public Autor(String nome, Date dataNascimento) {
        //contador no id para que tenha um incremento automaticamente quando for instanciado
        this.idAutor = idCount++;

        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }


    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}

