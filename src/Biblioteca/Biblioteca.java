package Biblioteca;

import Cliente.Cliente;
import Livro.Livro;
import Autor.Autor;

import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();


    //==============MÉTODOS PARA LIVROS ===========================
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public List<Livro> listarLivrosDisponiveis(){
        List<Livro> livrosDisponiveis = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                livrosDisponiveis.add(livro);
            }
        }
        return livrosDisponiveis;
    }

    public Livro buscarLivroPorId(int id) {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                return livro;
            }
        }
        return null;
    }

    public void atualizarLivro(int id, String novoTitulo) {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                livro.setTitulo(novoTitulo);
                break;
            }
        }
    }


    public List<Livro> filtrarLivros(int metodoBusca, String valor) {
        return switch (metodoBusca) {
            case 1 ->
                    livros.stream().filter(livro -> livro.getTitulo().toLowerCase().contains(valor.toLowerCase())).collect(Collectors.toList());
            case 2 ->
                    livros.stream().filter(livro -> livro.getAutor().getNome().toLowerCase().contains(valor.toLowerCase())).collect(Collectors.toList());
            case 3 ->
                    livros.stream().sorted(Comparator.comparing(Livro::getDataCadastro)).limit(5).collect(Collectors.toList());
            default -> {
                System.out.println("critério inválido!");
                yield Collections.emptyList();
            }
        };

    }

    public void removerLivro(int id) {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                livros.remove(livro);
                break;
            }
        }
    }

    // ================MÉTODOS PARA AUTORES========================
    public void adicionarAutor(Autor autor) {
        autores.add(autor);
    }

    public List<Autor> listarAutores() {
        return autores;
    }

    public void atualizarAutor(int id, String novoNome) {
        for (Autor autor : autores) {
            if (autor.getIdAutor() == id) {
                autor.setNome(novoNome);
                break;
            }
        }
    }

    public void removerAutor(int id) {
        autores.removeIf(autor -> autor.getIdAutor() == id);
    }

    // ====================MÉTODOS PARA EMPRÉSTIMO=================================
    public void emprestarLivro(Livro livro, String nomeUsuario) {
        if (livro.isDisponivel()) {
            Emprestimo emprestimo = new Emprestimo(livro, nomeUsuario);
            emprestimos.add(emprestimo);
            livro.setDisponivel(false);
        } else {
            System.out.println("Livro não disponível para empréstimo.");
        }
    }



    public void devolverLivro(int idEmprestimo) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getLivro().getId() == idEmprestimo && emprestimo.isAtivo()) {
                emprestimo.devolverLivro();
                break;
            }
        }
    }



    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }



    public void consultarHistoricoEmprestimos() {
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado.");
            return;
        }

        /* Comentários explicativos sobre HashSet(tive que aprender para resolver um certo problema):
         * problema: quando fazia um for of, o nome do usuário aparecia sempre, gerando uma verbosidade grande no output,
         * em uma situação onde um mesmo usuário pegasse vários livros, seria como imprimir uma nota fiscal pra
         * cada produto individualmente ao invés de agrupar os livros emprestados.
         *
         * Solução: uso do HashSet pra verificar mais rápido e eficiente e que o cliente apareça somente 1 vez.
         * */
        System.out.println("\n===== HISTÓRICO DE EMPRÉSTIMOS =====\n");

        Set<String> clientesExibidos = new HashSet<>();

        for (Emprestimo emprestimo : emprestimos) {
            String cliente = emprestimo.getNomeUsuario();

            //o cliente de primeira não existe, então assim q percorrer eu adiciono no hash pra q ele n imprima dnv
            if (!clientesExibidos.contains(cliente)) {
                System.out.printf("CLIENTE: %s%n", cliente);
                clientesExibidos.add(cliente);
            }

            String status = emprestimo.isAtivo() ? "Ativo" : "Finalizado";
            System.out.printf("ID Livro: %d | Título: %s | Autor: %s%n",
                    emprestimo.getLivro().getId(),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getLivro().getAutor().getNome());
            System.out.printf("Status: %s | Data/Hora Empréstimo: %s%n",
                    status, emprestimo.getDataEmprestimoFormatada());
            System.out.printf("Status de Devolução: %s%n", emprestimo.getDataDevolucaoFormatada());
            System.out.println();
        }

        System.out.println("==================================\n");
    }




    // ==========métodos para clientes=================
    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public boolean clienteExiste(String nomeCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getNomeCliente().equalsIgnoreCase(nomeCliente)) {
                return true;
            }
        }
        return false;
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n===== CLIENTES CADASTRADOS =====\n");
        for (Cliente cliente : clientes) {
            System.out.printf("ID: %d | Nome: %s | E-mail: %s%n%n",
                    cliente.getIdCliente(), cliente.getNomeCliente(), cliente.getEmailCliente());
        }
    }
}
