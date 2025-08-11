package model.conteudo;

import model.avaliacao.Avaliacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Conteudo {
    private int id;
    private String tipoConteudo;
    private String titulo;
    private LocalDate anoLancamento;
    private String genero;
    private String sinopse;

    List<Avaliacao> avaliacoes = new ArrayList<>();

    public Conteudo(int id, String tipoConteudo, String titulo, LocalDate anoLancamento, String genero, String sinopse) {
        this.id = id;
        this.tipoConteudo = tipoConteudo;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public int getId() {
        return id;
    }

    public abstract void exibirDetalhes();

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
}
