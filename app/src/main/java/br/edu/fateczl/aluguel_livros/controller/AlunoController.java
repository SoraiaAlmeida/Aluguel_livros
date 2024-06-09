package br.edu.fateczl.aluguel_livros.controller;

import br.edu.fateczl.aluguel_livros.Model.Aluno;
import br.edu.fateczl.aluguel_livros.Persistence.AlunoDAO;

import java.sql.SQLException;
import java.util.List;
public class AlunoController implements IController<Aluno> {
    private final AlunoDAO alunoDAO;

    public AlunoController(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }

        alunoDAO.inserir(aluno);

        alunoDAO.close();
    }

    @Override
    public void alterar(Aluno aluno) throws SQLException {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }

        alunoDAO.alterar(aluno);

        alunoDAO.close();
    }

    @Override
    public void deletar(Aluno aluno) throws SQLException {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }

        alunoDAO.deletar(aluno);

        alunoDAO.close();
    }

    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }

        return alunoDAO.buscar(aluno);

    }

    @Override
    public List<Aluno> listar() throws SQLException {
        if (alunoDAO.open() == null) {
            alunoDAO.open();
        }

        return alunoDAO.listar();
    }
}

