import Autor.Autor;
import Biblioteca.Biblioteca;
import Cliente.Cliente;
import Livro.Livro;
import Biblioteca.Emprestimo;

import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);


        Autor autor1 = new Autor("Fiódor Dostoiévski", new Date());
        Autor autor2 = new Autor("Isaac Asimov", new Date());

        biblioteca.adicionarAutor(autor1);
        biblioteca.adicionarAutor(autor2);


        Livro livro1 = new Livro("Crime e Castigo", autor1);
        Livro livro2 = new Livro("Os Irmãos Karamázov", autor1);
        Livro livro3 = new Livro("O Fim da Eternidade", autor2);

        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.adicionarLivro(livro3);

        System.out.println("Olá!! Bem Vindo(a) à Biblioteca Java!");


        while(true) {
            try{
                System.out.println("\nMenu de Funcionalidades:");
                System.out.println("1 - Listar todos os livros.");
                System.out.println("2 - Buscar Livro."); //filtros => titulo, autor, gênero, adicionado recentemente.
                System.out.println("3 - Realizar Empréstimo.");
                System.out.println("4 - Devolver Livro."); //consultar nome e ver quais livros foram pegos para devolver listando-os
                System.out.println("5 - Cadastrar Novo Livro.");
                System.out.println("6 - Excluir Livro.");
                System.out.println("7 - Cadastrar Cliente.");
                System.out.println("8 - Listar Clientes Cadastrados.");
                System.out.println("9 - Consultar Histórico de Empréstimos."); // emprestimo dia/hora xxxx, para cliente xxx
                System.out.println("0 - Sair.");

                System.out.print("Digite a função: ");
                String opcao = scanner.nextLine().toLowerCase();


                List<Livro> livrosDisponiveis = biblioteca.listarLivrosDisponiveis();
                List<Livro> listaLivros = biblioteca.listarLivros();
                List<Emprestimo> listaEmprestimos = biblioteca.listarEmprestimos();



                switch(opcao) {

                    //listando todos os livros e a disponibilidade padronizado - FINALIZADO
                    case "1": {
                        for (Livro livro : listaLivros) {
                            String status = livro.isDisponivel() ? "Disponível" : "Indisponível";

                            System.out.printf("ID: %03d Título: %-27.27s | Autor: %-27.27s | Status: %s%n",
                                    //fato curioso: o %n é um quebra-linha mais confiável pra funcionar em qualquer sistema operacional
                                    livro.getId(),
                                    livro.getTitulo(),
                                    livro.getAutor().getNome(),
                                    status);

                        }
                        continue;
                    }


                    case "2": {
                        System.out.println("Por favor, digite o número referente ao método de busca:");
                        System.out.println("1- Busca por título\n2- busca por autor\n3- adicionados recentemente");
                        int tipoDeBusca = scanner.nextInt();
                        scanner.nextLine();
                        String valor = "";

                        if(tipoDeBusca != 3) {
                            System.out.print("Digite o valor da busca: ");
                            valor = scanner.nextLine();
                        }

                        List<Livro> livrosFiltrados = biblioteca.filtrarLivros(tipoDeBusca, valor);

                        if(livrosFiltrados.isEmpty()) {
                            System.out.println("Nenhum livro encontrado");
                        } else {
                            System.out.println("\n\n\n======LIVROS FILTRADOS======");
                            for (Livro livro : livrosFiltrados) {
                                System.out.printf("ID: %03d Título: %-27.27s | Autor: %-27.27s | Adicionado em: %s%n",
                                        livro.getId(), livro.getTitulo(), livro.getAutor().getNome(), livro.getDataCadastro());
                            }
                        }
                        continue;
                    }

                    //realizar empréstimo e possível cadastro - FINALIZADO
                    case "3": {
                        if (livrosDisponiveis.isEmpty()) {
                            System.out.println("Nenhum livro disponível para empréstimo no momento!");
                            continue;
                        } else {

                            System.out.println("Livros Disponíveis no momento:");
                            for (Livro livro : livrosDisponiveis) {
                                System.out.printf("ID: %03d Título: %-27.27s | Autor: %-27.27s%n",
                                        livro.getId(), livro.getTitulo(), livro.getAutor().getNome());
                            }

                            System.out.println("Digite o ID do livro desejado: ");
                            int idLivro = scanner.nextInt();
                            scanner.nextLine();

                            Livro livroSelecionado = biblioteca.buscarLivroPorId(idLivro);

                            //verifico se existe e se ele está disponível
                            if (livroSelecionado != null && livroSelecionado.isDisponivel()) {
                                System.out.printf("Livro Selecionado: %s%n", livroSelecionado.getTitulo());

                                System.out.print("Digite seu Nome: ");
                                String nomeCliente = scanner.nextLine();

                                if(biblioteca.clienteExiste(nomeCliente)) {
                                    biblioteca.emprestarLivro(livroSelecionado, nomeCliente);
                                }
                                else {
                                    System.out.println("Notamos que ainda não possui cadastro," +
                                            " vamos primeiramente realizar o cadastro?");
                                    System.out.printf("%s por gentileza, digite seu email: ", nomeCliente);
                                    String emailCliente = scanner.nextLine();
                                    Cliente novoCliente = new Cliente(nomeCliente, emailCliente);

                                    biblioteca.cadastrarCliente(novoCliente);
                                    System.out.println("Cadastrado com sucesso!");
                                    biblioteca.emprestarLivro(livroSelecionado, nomeCliente);
                                    System.out.println("Empréstimo realizado, Boa leitura!!");
                                }




                                //digitar nome e verificar se existe esse nome, caso contrário, cadastrar
                                // e chamar o metodo de cadastro

                            }
                        }
                        continue;

                    }

                    //devolver livro - FINALIZADO
                    case "4": {
                        System.out.println("Por favor, digite seu nome:");
                        String nomeCliente = scanner.nextLine();
                        if(biblioteca.clienteExiste(nomeCliente) && !listaEmprestimos.isEmpty()) {
                            System.out.printf("Olá %s!, Estamos verificando se há algum livro emprestado... %n", nomeCliente);

                            boolean encontrouLivros = false;
                            for (Emprestimo emprestimo: listaEmprestimos) {
                                if (emprestimo.getNomeUsuario().equals(nomeCliente) && emprestimo.isAtivo()) {

                                    System.out.printf("ID: %03d Título: %-27.27s | Autor: %-27.27s%n",
                                            emprestimo.getLivro().getId(),
                                            emprestimo.getLivro().getTitulo(),
                                            emprestimo.getLivro().getAutor().getNome());
                                    encontrouLivros = true;
                                }
                            }

                            if (!encontrouLivros) {
                                System.out.printf("Nenhum livro foi emprestado para %s%n", nomeCliente);
                                continue;
                            }

                            System.out.println("Por favor, digite o ID do livro que deseja devolver: ");
                            int idLivro = scanner.nextInt();
                            scanner.nextLine();
                            biblioteca.devolverLivro(idLivro);
                            System.out.println("Devolvido com sucesso!");
                            continue;

                        }

                        if(!biblioteca.clienteExiste(nomeCliente)) {
                            System.out.println("Usuário inexistente/incorreto!");
                        }
                        continue;
                    }

                    //cadastrar livro = FINALIZADO
                    case "5": {
                        System.out.println("CADASTRO DE LIVROS");
                        System.out.print("Digite o título do livro: ");
                        String tituloLivro = scanner.nextLine();

                        System.out.print("Digite o nome do Autor: ");
                        String autorLivro = scanner.nextLine();

                        Autor novoAutor = new Autor(autorLivro, new Date());
                        biblioteca.adicionarAutor(novoAutor);
                        Livro novoLivro = new Livro(tituloLivro, novoAutor);
                        biblioteca.adicionarLivro(novoLivro);
                        System.out.println("Livro cadastrado com sucesso!");

                        continue;
                    }

                    // excluir livro  - FINALIZADO
                    case "6": {

                        if (livrosDisponiveis.isEmpty()) {
                            System.out.println("Não há livros ou precisam ser devolvidos antes de serem excluidos!");
                            continue;
                        }

                        System.out.println("Livros disponíveis: ");

                        for (Livro livro : livrosDisponiveis) {
                            System.out.printf("ID: %03d | Título: %-27.27s | Autor: %-27.27s%n", livro.getId(), livro.getTitulo(), livro.getAutor().getNome());
                        }

                        System.out.print("Selecione o Id do livro para excluír: ");
                        int idLivro = scanner.nextInt();
                        scanner.nextLine();

                        Livro livroSelecionado = null;
                        for (Livro livro : listaLivros) {

                            if (livro.getId() == idLivro) {
                                livroSelecionado = livro;
                                break;
                            }
                        }
                        if (livroSelecionado == null) {
                            System.out.println("Livro não encontrado.");
                            continue;
                        }
                        if (!livroSelecionado.isDisponivel()) {
                            System.out.println("O livro precisa estar disponível para ser excluído.");
                            continue;
                        }

                        System.out.println("O livro selecionado foi: " + livroSelecionado.getTitulo());
                        System.out.println("Tem certeza que deseja excluir esse livro? (sim/nao): ");
                        String confirmacaoExclusao = scanner.nextLine();

                        if (confirmacaoExclusao.equalsIgnoreCase("sim")) {
                            biblioteca.removerLivro(idLivro);
                            System.out.println("Livro removido com sucesso!");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                        continue;


                    }

                    //cadastrar cliente - finalizado
                    case "7": {
                        System.out.println("Por favor Digite seu nome:");
                        String nome = scanner.nextLine();

                        System.out.println("Digite seu e-mail");
                        String email = scanner.nextLine();


                        Cliente novoCliente = new Cliente(nome, email);
                        biblioteca.cadastrarCliente(novoCliente);

                        System.out.println("Cliente cadastrado com sucesso!");
                    }

                    //listar clientes - finalizado
                    case "8": {
                        biblioteca.listarClientes();
                        continue;
                    }

                    //historico de emprestimos = finalizado
                    case "9": {
                        biblioteca.consultarHistoricoEmprestimos();
                        continue;
                    }

                    //finalizar programa - finalizado
                    case "0": {
                        System.out.println("Programa Encerrado!! obrigado por participar!");
                        System.exit(0);
                    }

                    default: {
                        System.out.println("Opção inválida!! Digite um número entre 0 e 9 por favor");
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("\nPor favor, digite um número válido!!");
            }

        }

    }
}