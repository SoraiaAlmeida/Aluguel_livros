package br.edu.fateczl.aluguel_livros.Persistence;

import java.sql.SQLException;
public interface IAluguelDAO {
    public AluguelDAO open() throws SQLException;

    public void close();
}

