package model.usuarios;

public class Admin extends Usuario{
    public Admin(String nome, String email, String senha, boolean admin) {
        super(nome, email, senha, admin);
    }

    @Override
    public void getInfo() {
        System.out.println("Usuário administrador:");
        System.out.println("Nome: " + getNome() + " Email: " + getEmail() + " Senha: " + getSenha());
    }
}
