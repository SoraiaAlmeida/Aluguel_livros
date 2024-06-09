package br.edu.fateczl.aluguel_livros.Persistence;

import java.sql.SQLException;
public interface ILivroDAO {

    public LivroDAO open() throws SQLException;

    public void close();
}
