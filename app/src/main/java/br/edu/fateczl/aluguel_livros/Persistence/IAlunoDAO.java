package br.edu.fateczl.aluguel_livros.Persistence;

import java.sql.SQLException;

public interface IAlunoDAO {

    public AlunoDAO open() throws SQLException;

    public void close();
}

