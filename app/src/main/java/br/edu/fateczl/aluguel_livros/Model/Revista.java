package br.edu.fateczl.aluguel_livros.Model;

import androidx.annotation.NonNull;
public class Revista extends Exemplar {

    private String revistaISSN;

    public Revista(int exemplarId, String exemplarNome, int exemplarPaginas, String revistaISSN) {
        super(exemplarId, exemplarNome, exemplarPaginas);
        this.revistaISSN = revistaISSN;
    }

    public String getRevistaISSN() {
        return revistaISSN;
    }

    public void setRevistaISSN(String revistaISSN) {
        this.revistaISSN = revistaISSN;
    }

    @NonNull
    @Override
    public String toString() {
        return getExemplarId() + " - " + getExemplarNome();
    }
}


