package Biblioteca;
import Livro.Livro;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo {
    private int id;
    private static int contadorId = 1;
    private Livro livro;
    private String nomeUsuario;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private boolean ativo;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Emprestimo(Livro livro, String nomeUsuario) {
        this.id = contadorId++;
        this.livro = livro;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = new Date();
        this.ativo = true;
    }

    public int getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }
    public Date getDataDevolucao() {
        return dataDevolucao;
    }
    public boolean isAtivo() {
        return ativo;
    }

    public void devolverLivro() {
        this.dataDevolucao = new Date();
        this.ativo = false;
        this.livro.setDisponivel(true);
    }

    public String getDataEmprestimoFormatada() {
        return dateFormat.format(dataEmprestimo);
    }
    public String getDataDevolucaoFormatada() {
        return dataDevolucao != null ? "Devolvido em " + dateFormat.format(dataDevolucao) : "Ainda não devolvido";
    }

}
