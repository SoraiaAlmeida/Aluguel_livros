package br.edu.fateczl.aluguel_livros.controller;

import br.edu.fateczl.aluguel_livros.Model.Revista;
import br.edu.fateczl.aluguel_livros.Persistence.RevistaDAO;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.aluguel_livros.Persistence.RevistaDAO;

public class RevistaController implements IController<Revista> {
    private final RevistaDAO revistaDAO;

    public RevistaController(RevistaDAO revistaDAO) {
        this.revistaDAO = revistaDAO;
    }

    @Override
    public void inserir(Revista revista) throws SQLException {
        if (revistaDAO.open() == null) {
            revistaDAO.open();
        }

        revistaDAO.inserir(revista);

        revistaDAO.close();
    }

    @Override
    public void alterar(Revista revista) throws SQLException {
        if (revistaDAO.open() == null) {
            revistaDAO.open();
        }

        revistaDAO.alterar(revista);

        revistaDAO.close();
    }

    @Override
    public void deletar(Revista revista) throws SQLException {
        if (revistaDAO.open() == null) {
            revistaDAO.open();
        }

        revistaDAO.deletar(revista);

        revistaDAO.close();
    }

    @Override
    public Revista buscar(Revista revista) throws SQLException {
        if (revistaDAO.open() == null) {
            revistaDAO.open();
        }

        return revistaDAO.buscar(revista);

    }

    @Override
    public List<Revista> listar() throws SQLException {
        if (revistaDAO.open() == null) {
            revistaDAO.open();
        }

        return revistaDAO.listar();
    }
}
