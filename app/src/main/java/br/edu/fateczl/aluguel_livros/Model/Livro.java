package br.edu.fateczl.aluguel_livros.Model;

import androidx.annotation.NonNull;
public class Livro extends Exemplar {
    private String livroISBN;
    private int edicaoLivro;

    public Livro(int exemplarId, String exemplarNome, int exemplarPaginas, String livroISBN, int edicaoLivro) {
        super(exemplarId, exemplarNome, exemplarPaginas);
        this.livroISBN = livroISBN;
        this.edicaoLivro = edicaoLivro;

    }
    public String getLivroISBN() {
        return livroISBN;
    }

    public void setLivroISBN(String livroISBN) {
        this.livroISBN = livroISBN;
    }

    public int getEdicaoLivro() {
        return edicaoLivro;
    }

    public void setEdicaoLivro(int edicaoLivro) {
        this.edicaoLivro = edicaoLivro;
    }

    @NonNull
    @Override
    public String toString() {
        return getExemplarId() + " - " + getExemplarNome();
    }
}