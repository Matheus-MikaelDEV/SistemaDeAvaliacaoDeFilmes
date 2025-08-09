package sistema;

import model.conteudo.Conteudo;
import model.conteudo.Filme;
import model.usuarios.Admin;
import model.usuarios.Comum;
import model.usuarios.Usuario;

import java.io.*;
import java.time.LocalDate;
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
        try(BufferedReader br = new BufferedReader(new FileReader("conteudos.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] vetor = linha.split(",");

                LocalDate data = LocalDate.parse(vetor[2]);
                int duracao = Integer.parseInt(vetor[5]);

                if (vetor[0].equals("Filme")) {
                    Conteudo filme = new Filme(vetor[0], vetor[1], data, vetor[3], vetor[4],  duracao);
                    conteudos.add(filme);
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhum conteúdo cadastrado...");
        }

        boolean tokenCadastrado = false;

        try(BufferedReader br = new BufferedReader(new FileReader("token.txt"))) {
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

        try(BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
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
            if (tipo.equals("adm")) {
                bw.write("Admin," + nome + "," + email + "," + senha);
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

    }
}
