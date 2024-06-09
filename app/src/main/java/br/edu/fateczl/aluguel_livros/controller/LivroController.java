package br.edu.fateczl.aluguel_livros.controller;


import br.edu.fateczl.aluguel_livros.Model.Livro;
import br.edu.fateczl.aluguel_livros.Persistence.LivroDAO;

import java.sql.SQLException;
import java.util.List;

public class LivroController  implements IController<Livro> {
        private final LivroDAO livroDAO;

        public LivroController(LivroDAO livroDAO) {
            this.livroDAO = livroDAO;
        }

        @Override
        public void inserir(Livro livro) throws SQLException {
            if (livroDAO.open() == null) {
                livroDAO.open();
            }

            livroDAO.inserir(livro);

            livroDAO.close();
        }

        @Override
        public void alterar(Livro livro) throws SQLException {
            if (livroDAO.open() == null) {
                livroDAO.open();
            }

            livroDAO.alterar(livro);

            livroDAO.close();
        }

        @Override
        public void deletar(Livro livro) throws SQLException {
            if (livroDAO.open() == null) {
                livroDAO.open();
            }

            livroDAO.deletar(livro);

            livroDAO.close();
        }

        @Override
        public Livro buscar (Livro livro) throws SQLException {
            if (livroDAO.open() == null) {
                livroDAO.open();
            }

            return livroDAO.buscar(livro);

        }

        @Override
        public List<Livro> listar() throws SQLException {
            if (livroDAO.open() == null) {
                livroDAO.open();
            }

            return livroDAO.listar();
        }
    }