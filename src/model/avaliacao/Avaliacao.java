package model.avaliacao;

public class Avaliacao {
    private String nomeDoAutor;
    private int nota;
    private String comentario;
    private String emailDoAutor;

    public Avaliacao(String nomeDoAutor, int nota, String comentario, String emailDoAutor) {
        this.nomeDoAutor = nomeDoAutor;
        this.nota = nota;
        this.comentario = comentario;
        this.emailDoAutor = emailDoAutor;
    }

    public void getInfo() {
        System.out.println("Nome: " + nomeDoAutor + " - Nota: " + nota + " ★\nComentário: " + comentario);
    }

    public String getNomeDoAutor() {
        return nomeDoAutor;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public String getEmailDoAutor() {
        return emailDoAutor;
    }
}