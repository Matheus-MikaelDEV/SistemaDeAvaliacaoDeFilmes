package model.usuarios;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private boolean admin = false;


    public Usuario(String nome, String email, String senha, boolean admin) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public abstract void getInfo();
}
