package br.edu.fateczl.aluguel_livros.Model;

import androidx.annotation.NonNull;

public abstract class Exemplar {
    private int exemplarId;
    private String exemplarNome;
    private int exemplarPaginas;

    public Exemplar(int exemplarId, String exemplarNome, int exemplarPaginas) {
        this.exemplarId = exemplarId;
        this.exemplarNome = exemplarNome;
        this.exemplarPaginas = exemplarPaginas;
}
    public int getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(int exemplarId) {
        this.exemplarId = exemplarId;
    }

    public String getExemplarNome() {
        return exemplarNome;
    }

    public void setExemplarNome(String exemplarNome) {
        this.exemplarNome = exemplarNome;
    }

    public int getExemplarPaginas() {
        return exemplarPaginas;
    }

    public void setExemplarPaginas(int exemplarPaginas) {
        this.exemplarPaginas = exemplarPaginas;
    }

    @NonNull
    @Override
    public String toString() {
        return exemplarId + " - " + exemplarNome;
    }
}
