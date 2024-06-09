package br.edu.fateczl.aluguel_livros.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.aluguel_livros.Persistence.AluguelDAO;
import br.edu.fateczl.aluguel_livros.Model.Aluguel;

public class AluguelController implements IController<Aluguel> {
    private final AluguelDAO aluguelDAO;

    public AluguelController(AluguelDAO aluguelDAO) {
        this.aluguelDAO = aluguelDAO;
    }

    @Override
    public void inserir(Aluguel aluguel) throws SQLException {
        if (aluguelDAO.open() == null) {
            aluguelDAO.open();
        }

        aluguelDAO.inserir(aluguel);

        aluguelDAO.close();
    }

    @Override
    public void alterar(Aluguel aluguel) throws SQLException {
        if (aluguelDAO.open() == null) {
            aluguelDAO.open();
        }

        aluguelDAO.alterar(aluguel);

        aluguelDAO.close();
    }

    @Override
    public void deletar(Aluguel aluguel) throws SQLException {
        if (aluguelDAO.open() == null) {
            aluguelDAO.open();
        }

        aluguelDAO.deletar(aluguel);

        aluguelDAO.close();
    }

    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        if (aluguelDAO.open() == null) {
            aluguelDAO.open();
        }

        return aluguelDAO.buscar(aluguel);

    }

    @Override
    public List<Aluguel> listar() throws SQLException {
        if (aluguelDAO.open() == null) {
            aluguelDAO.open();
        }

        return aluguelDAO.listar();
    }
}