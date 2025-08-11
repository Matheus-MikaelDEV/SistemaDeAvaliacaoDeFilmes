package model.conteudo;

import model.avaliacao.Avaliacao;

import java.time.LocalDate;
import java.util.List;

public class Filme extends Conteudo{
    private int duracaoEmMinutos;

    public Filme(int id, String tipoConteudo, String titulo, LocalDate anoLancamento, String genero, String sinopse, int duracaoEmMinutos) {
        super(id, tipoConteudo, titulo, anoLancamento, genero, sinopse);
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Filme: " + getTitulo());
        System.out.println("Ano de Lançamento: " + getAnoLancamento());
        System.out.println("Gênero: " + getGenero());
        System.out.println("Sinopse: " + getSinopse());
        System.out.println("Duração: " + duracaoEmMinutos + " minutos");
    }

}
