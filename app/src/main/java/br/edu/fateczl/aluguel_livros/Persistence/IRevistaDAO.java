package br.edu.fateczl.aluguel_livros.Persistence;

import java.sql.SQLException;

public interface IRevistaDAO {
    public RevistaDAO  open() throws SQLException;

    public void close();
}

