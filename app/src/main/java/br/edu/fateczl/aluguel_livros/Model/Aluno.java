package br.edu.fateczl.aluguel_livros.Model;

import androidx.annotation.NonNull;
public class Aluno {

    private int IdAluno;
    private String nomeAluno;
    private String emailAluno;

    public Aluno(int IdAluno, String nomeAluno, String emailAluno) {
        this.IdAluno = IdAluno;
        this.nomeAluno = nomeAluno;
        this.emailAluno = emailAluno;
    }


    public int getIdAluno() {
        return IdAluno;
    }

    public void setIdAluno(int idAluno) {
        IdAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    @NonNull
    @Override
    public String toString() {
        return IdAluno + " - " + nomeAluno;
    }
}

