package model.usuarios;

public class Comum extends Usuario{
    public Comum(String nome, String email, String senha, boolean admin) {
        super(nome, email, senha, admin);
    }

    @Override
    public void getInfo() {
        System.out.println("Usuário comum:");
        System.out.println("Nome: " + getNome() + " Email: " + getEmail() + " Senha: " + getSenha());
    }
}
