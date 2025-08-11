package programa;

import sistema.Sistema;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----- Sistema de Avaliações de Filmes (e futuramente séries...) -----");

        int option = 0;

        Sistema sistema = new Sistema();

        do {
            try {
                System.out.println("Menu: ");
                System.out.print("1 - Cadastrar Usuário\n2 - Logar\n3 - Fechar\nEscolha: ");
                option = sc.nextInt();
            } catch (InputMismatchException e) {
                e.printStackTrace();
            } finally {
                sc.nextLine();
            }

            switch (option) {
                case 1:
                    sistema.addUsuario();
                    break;
                case 2:
                    if (sistema.logarUsuario()) {
                        int option2 = 0;

                        if (sistema.getUsuarioLogado().isAdmin() == true) {
                            do {
                                try {
                                    System.out.println("Menu de Administração: ");
                                    System.out.print("1 - Listar Usuários\n2 - Excluir Usuário\n3 - Adicionar Conteúdo\n4 - Remover Conteúdo\n5 - Sair\nEscolha: ");
                                    option2 = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    e.printStackTrace();
                                } finally {
                                    sc.nextLine();
                                }

                                switch (option2) {
                                    case 1:
                                        sistema.listarUsuarios();
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        sistema.addConteudo();
                                        break;
                                    case 4:
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Opção inválida!");
                                        break;
                                }

                            } while (option2 != 5);
                        }
                    }

                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (option != 3);
    }
}
