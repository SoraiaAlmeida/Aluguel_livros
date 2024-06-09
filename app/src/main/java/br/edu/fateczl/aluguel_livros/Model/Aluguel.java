package br.edu.fateczl.aluguel_livros.Model;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Aluguel {

    private Aluno aluguelAluno;
    private Exemplar aluguelExemplar;
    private LocalDate aluguelDataRetirada;
    private LocalDate aluguelDataRetorno;

    public Aluguel() {
        super();
    }

    public Aluguel(Aluno aluguelAluno, Exemplar aluguelExemplar, LocalDate aluguelDataRetirada) {
        this.aluguelAluno = aluguelAluno;
        this.aluguelExemplar = aluguelExemplar;
        this.aluguelDataRetirada = aluguelDataRetirada;
    }

    public Aluguel(Aluno aluguelAluno, Exemplar aluguelExemplar, LocalDate aluguelDataRetirada, LocalDate aluguelDataRetorno) {
        this.aluguelAluno = aluguelAluno;
        this.aluguelExemplar = aluguelExemplar;
        this.aluguelDataRetirada = aluguelDataRetirada;
        this.aluguelDataRetorno = aluguelDataRetorno;
    }

    public Aluno getAluguelAluno() {
        return aluguelAluno;
    }

    public void setAluguelAluno(Aluno aluguelAluno) {
        this.aluguelAluno = aluguelAluno;
    }

    public Exemplar getAluguelExemplar() {
        return aluguelExemplar;
    }

    public void setAluguelExemplar(Exemplar aluguelExemplar) {
        this.aluguelExemplar = aluguelExemplar;
    }

    public LocalDate getAluguelDataRetirada() {
        return aluguelDataRetirada;
    }

    public void setAluguelDataRetirada(LocalDate aluguelDataRetirada) {
        this.aluguelDataRetirada = aluguelDataRetirada;
    }

    public LocalDate getAluguelDataRetorno() {
        return aluguelDataRetorno;
    }

    public void setAluguelDataRetorno(LocalDate aluguelDataRetorno) {
        this.aluguelDataRetorno = aluguelDataRetorno;
    }


    @NonNull
    @Override
    public String toString() {
        return "Aluguel{" +
                "Aluno='" + aluguelAluno.getNomeAluno() + "', " +
                "Exemplar='" + aluguelExemplar.getExemplarNome() + "', " +
                "Data='" + aluguelDataRetirada + "'}";
    }
}

