package sistema;

import model.avaliacao.Avaliacao;
import model.conteudo.Conteudo;
import model.conteudo.Filme;
import model.usuarios.Admin;
import model.usuarios.Comum;
import model.usuarios.Usuario;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    List<Conteudo> conteudos = new ArrayList<>();
    List<Usuario> usuarios = new ArrayList<>();
    Usuario usuarioLogado;

    Scanner sc = new Scanner(System.in);

    int token;

    // O sistema de início verifica-ra se há algo escrito no arquivo, caso conter irá registrar tudo no sistema pra haver uma persistencia de dados sólida.
    public Sistema() {
        try (BufferedReader br = new BufferedReader(new FileReader("conteudos.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] vetor = linha.split("//");

                if (vetor[0].equals("Filme")) {
                    Filme filme = new Filme(Integer.parseInt(vetor[1]), vetor[0], vetor[2], LocalDate.parse(vetor[3]), vetor[4], vetor[5], Integer.parseInt(vetor[6]));
                    conteudos.add(filme);

                } else if (vetor[0].equals("Serie")) {

                } else {
                    Avaliacao avaliacao = new Avaliacao(vetor[1], Integer.parseInt(vetor[2]), vetor[3], vetor[4]);

                    for (Conteudo conteudo : conteudos) {
                        if (conteudo.getTitulo().equals(vetor[0])) {
                            conteudo.adicionarAvaliacao(avaliacao);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhum conteúdo cadastrado...");
        }

        boolean tokenCadastrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader("token.txt"))) {
            String linha = br.readLine();
            if (linha != null) {
                token = Integer.parseInt(linha);
                tokenCadastrado = true;
            }
        } catch (IOException e) {
            System.out.println("Nenhum token cadastrado...");
        }

        if (!tokenCadastrado) {
            try {
                System.out.println("Digite o token para cadastrar admin: ");
                token = sc.nextInt();

                tokenCadastrado = true;

                try (BufferedWriter bw = new BufferedWriter(new FileWriter("token.txt"))) {
                    bw.write(String.valueOf(token));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (InputMismatchException e) {
                System.out.println("Token inválido! Deve ser apenas números...");
            } finally {
                sc.nextLine();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] vetor = linha.split(",");

                if (vetor[0].equals("Admin")) {
                    Usuario admin = new Admin(vetor[1], vetor[2], vetor[3], true);
                    usuarios.add(admin);
                } else {
                    Usuario usuario = new Comum(vetor[1], vetor[2], vetor[3], false);
                    usuarios.add(usuario);
                }
            }
        } catch (Exception e) {
            System.out.println("Nenhum usuário cadastrado...");
        }
    }

    public void addUsuario() {
        System.out.print("Digite o nome: ");
        String nome = sc.nextLine();
        System.out.print("Digite o email: ");
        String email = sc.nextLine();
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                System.out.println("Email já cadastrado!");
                return;
            }
        }

        System.out.print("Admin ou Comum(adm/com): ");
        String tipo = sc.nextLine();

        if (tipo.equals("adm")) {
            System.out.print("Token para cadastro de admin: ");
            int token = sc.nextInt();

            if (token != this.token) {
                System.out.println("Token inválido!");
                return;
            }

            Usuario admin = new Admin(nome, email, senha, true);
            usuarios.add(admin);
        } else if (tipo.equals("com")) {
            Usuario usuario = new Comum(nome, email, senha, false);
            usuarios.add(usuario);
        } else {
            System.out.println("Tipo de usuário inválido!");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            if (tipo.equals("adm")) {bw.write("Admin," + nome + "," + email + "," + senha);
                bw.newLine();
            } else if (tipo.equals("com")) {
                bw.write("Comum," + nome + "," + email + "," + senha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean logarUsuario() {
        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários cadastrados!");
            return false;
        } else {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            for (Usuario usuario : usuarios) {
                if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                    System.out.println("Logado com sucesso!");
                    if (usuario.isAdmin() == true) {
                        usuarioLogado = usuario;
                    } else {
                        usuarioLogado = usuario;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários cadastrados!");
        } else {
            for (Usuario usuario : usuarios) {
                usuario.getInfo();
            }
        }
    }

    public void addConteudo() {
        try {
            System.out.print("Filme ou sério(f/s)? ");
            String tipo = sc.nextLine();

            if (tipo.equals("f")) {
                System.out.print("Título: ");
                String titulo = sc.nextLine();
                System.out.print("Ano de lançamento(dd/MM/yyyy): ");
                LocalDate anoLancamento = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.print("Gênero: ");
                String genero = sc.nextLine();
                System.out.print("Sinopse: ");
                String sinopse = sc.nextLine();
                System.out.print("Duração: ");
                int duracao = sc.nextInt();
                sc.nextLine();

                Filme filme = new Filme(conteudos.size() + 1, tipo, titulo, anoLancamento, genero, sinopse, duracao);
                conteudos.add(filme);
            } else if (tipo.equals("s")) {

            } else {
                System.out.println("Tipo de conteúdo inválido!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Algum valor inserido é inválido!");
        }

        salvarConteudos();
    }

    public void addAvaliacao() {
        boolean jaFeita = false;

        if (conteudos.isEmpty()) {
            System.out.println("Não há conteúdos cadastrados!");
        } else {
            System.out.print("Título do conteúdo: ");
            String titulo = sc.nextLine();

            for (Conteudo conteudo : conteudos) {
                for (Avaliacao avaliacao : conteudo.getAvaliacoes()) {
                    if (avaliacao.getEmailDoAutor().equalsIgnoreCase(usuarioLogado.getEmail())) {
                        jaFeita = true;
                        break;
                    }
                }
            }

            if (jaFeita) {
                System.out.println("Você já avaliou esse conteúdo!");
            } else {
                for (Conteudo conteudo : conteudos) {
                    if (conteudo.getTitulo().equalsIgnoreCase(titulo)) {
                        try {
                            System.out.print("Qual a nota(1 a 10)? ");
                            int nota = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Comentário: ");
                            String comentario = sc.nextLine();

                            Avaliacao avaliacao = new Avaliacao(usuarioLogado.getNome(), nota, comentario, usuarioLogado.getEmail());
                            conteudo.adicionarAvaliacao(avaliacao);
                            System.out.println("Avaliação adicionada!");

                            salvarConteudos();
                        } catch (InputMismatchException e) {
                            System.out.println("Nota inválida!");
                            sc.nextLine();
                        }
                    }
                }
            }
        }
    }

    public void listarTudo() {
        if (conteudos.isEmpty()) {
            System.out.println("Não há conteúdos cadastrados!");
        } else {
            for (Conteudo conteudo : conteudos) {
                System.out.println("--------------------------");
                conteudo.exibirDetalhes();
                for (Avaliacao avaliacao : conteudo.getAvaliacoes()) {
                    avaliacao.getInfo();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listarAvaliacoesComEmailDoUsuarioLogado() {
        if (conteudos.isEmpty()) {
            System.out.println("Não há conteúdos cadastrados!");
        } else {
            for (Conteudo conteudo : conteudos) {
                for (Avaliacao avaliacao : conteudo.getAvaliacoes()) {
                    if (avaliacao.getEmailDoAutor().equals(usuarioLogado.getEmail())) {
                        avaliacao.getInfo();
                    }
                }
            }
        }
    }

    public void removerConteudoPorNome() {
        if (conteudos.isEmpty()) {
            System.out.println("Não há conteúdos cadastrados!");
        } else {
            System.out.print("Título do conteúdo: ");
            String titulo = sc.nextLine();

            boolean removido = conteudos.removeIf(c -> c.getTitulo().equalsIgnoreCase(titulo));

            if (removido) {
                System.out.println("Conteúdo removido!");
                salvarConteudos();
            } else {
                System.out.println("Não há conteúdo com esse nome!");
            }
        }
    }

    public void removerUsuarioPorNome() {
        if (usuarios.isEmpty()) {
            System.out.println("Não há usuários cadastrados!");
        } else {
            System.out.print("Nome do usuário: ");
            String nome = sc.nextLine();

            boolean removido = usuarios.removeIf(usuario -> usuario.getNome().equalsIgnoreCase(nome));

            if (removido) {
                System.out.println("Usuário removido!");
                salvarUsuarios();
            } else {
                System.out.println("Usuário não encontrado!");
            }
        }
    }

    public void salvarConteudos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("conteudos.txt"))) {
            for (Conteudo conteudo : conteudos) {
                if (conteudo instanceof Filme) {
                    Filme filme = (Filme) conteudo;
                    bw.write("Filme//" + filme.getId() + "//" + filme.getTitulo() + "//" + filme.getAnoLancamento() + "//" + filme.getGenero() + "//" + filme.getSinopse() + "//" + filme.getDuracaoEmMinutos());
                    bw.newLine();
                }

                // Salvando as avaliações
                for (Avaliacao avaliacao2 : conteudo.getAvaliacoes()) {
                    bw.write(conteudo.getTitulo() + "//" + avaliacao2.getNomeDoAutor() + "//" + avaliacao2.getNota() + "//" + avaliacao2.getComentario() + "//" + avaliacao2.getEmailDoAutor());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void salvarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                if (usuario instanceof Admin) {
                    Admin admin = (Admin) usuario;
                    bw.write("Admin," + admin.getNome() + "," + admin.getEmail() + "," + admin.getSenha());
                    bw.newLine();
                } else if (usuario instanceof Comum) {
                    Comum comum = (Comum) usuario;
                    bw.write("Comum," + comum.getNome() + "," + comum.getEmail() + "," + comum.getSenha());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}

