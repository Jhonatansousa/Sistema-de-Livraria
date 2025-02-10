package Livro;

import Autor.Autor;

import java.util.Date;

public class Livro {
    private int id;
    private static int idCounter = 1;
    private String titulo;
    private Autor autor;
    private boolean disponivel;
    private Date dataCadastro;
    private Date dataAtualizacao;

    public Livro(String titulo, Autor autor) {
        this.id = idCounter++;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
        this.dataCadastro = new Date();
        this.dataAtualizacao = new Date();
    }

    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public Autor getAutor() {
        return autor;
    }
    public boolean isDisponivel() {
        return disponivel;
    }
    public Date getDataCadastro() {
        return dataCadastro;
    }
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.dataAtualizacao = new Date();
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }


}
