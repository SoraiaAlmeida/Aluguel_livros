package br.edu.fateczl.aluguel_livros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fateczl.aluguel_livros.Model.Livro;
import br.edu.fateczl.aluguel_livros.Persistence.LivroDAO;
import br.edu.fateczl.aluguel_livros.controller.LivroController;

import java.sql.SQLException;
import java.util.List;


public class LivroFragment extends Fragment {
    private View view;
    private EditText etIdLivro;
    private EditText etNomeLivro;
    private EditText etPaginasLivro;
    private EditText etISBNLivro;
    private EditText etEdicaoLivro;
    private TextView tvSaidaLivro;
    private LivroController livroController;

    public LivroFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_livro, container, false);

        etIdLivro = view.findViewById(R.id.etIdLivro);
        etNomeLivro = view.findViewById(R.id.etNomeLivro);
        etPaginasLivro = view.findViewById(R.id.etPaginasLivro);
        etISBNLivro = view.findViewById(R.id.etISBNLivro);
        etEdicaoLivro = view.findViewById(R.id.etEdicaoLivro);
        tvSaidaLivro = view.findViewById(R.id.tvSaidaLivro);
        tvSaidaLivro.setMovementMethod(new ScrollingMovementMethod());

        livroController = new LivroController(new LivroDAO(this.getContext()));

        view.findViewById(R.id.btnBuscarLivro).setOnClickListener(op -> buscar());
        view.findViewById(R.id.btnInserirLivro).setOnClickListener(op -> inserir());
        view.findViewById(R.id.btnAlterarLivro).setOnClickListener(op -> alterar());
        view.findViewById(R.id.btnDeletarLivro).setOnClickListener(op -> deletar());
        view.findViewById(R.id.btnListarLivro).setOnClickListener(op -> listar());

        return view;
    }

    private void buscar() {
        Livro livro;

        try {
            livro = livroController.buscar(new Livro(
                    Integer.parseInt(etIdLivro.getText().toString()),
                    null,
                    0,
                    null,
                    0)
            );

            if (livro.getExemplarNome() != null) {
                lerlivro(livro);

            } else {
                Toast.makeText(
                        view.getContext(), "Livro Não Encontrado!", Toast.LENGTH_LONG).show();

                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void inserir() {
        try {
            livroController.inserir(escreverLivro());

            Toast.makeText(
                    view.getContext(), "Livro cadastrado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void alterar() {
        try {
            livroController.alterar(escreverLivro());

            Toast.makeText(
                    view.getContext(), "Livro atualizado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void deletar() {
        try {
            livroController.deletar(
                    new Livro(Integer.parseInt(etIdLivro.getText().toString()), null, 0, null, 0));

            Toast.makeText(
                    view.getContext(), "Livro excluido com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void listar() {
        try {
            List<Livro> livros = livroController.listar();

            StringBuilder stringBuffer = new StringBuilder();

            for (Livro livro : livros) {
                stringBuffer.append(livro.toString()).append("\n");
            }

            tvSaidaLivro.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Livro escreverLivro() throws SQLException {
        if (!validacao()) {
            throw new SQLException("Entrada inválida!");
        }

        return new Livro(
                Integer.parseInt(etIdLivro.getText().toString()),
                etNomeLivro.getText().toString(),
                Integer.parseInt(etPaginasLivro.getText().toString()),
                etISBNLivro.getText().toString(),
                Integer.parseInt(etEdicaoLivro.getText().toString())
        );
    }

    private boolean validacao() {
        if (etIdLivro.length() == 0) {
            return false;
        }

        if (etNomeLivro.length() == 0) {
            return false;
        }

        if (etPaginasLivro.length() == 0) {
            return false;
        }

        if (etISBNLivro.length() == 0) {
            return false;
        }

        if (etEdicaoLivro.length() == 0) {
            return false;
        }

        return true;
    }

    private void lerlivro(Livro livro) {
        etIdLivro.setText(String.valueOf(livro.getExemplarId()));
        etNomeLivro.setText(livro.getExemplarNome());
        etPaginasLivro.setText(String.valueOf(livro.getExemplarPaginas()));
        etISBNLivro.setText(livro.getLivroISBN());
        etEdicaoLivro.setText(String.valueOf(livro.getEdicaoLivro()));
    }

    private void limpaCampos() {
        etIdLivro.setText("");
        etNomeLivro.setText("");
        etPaginasLivro.setText("");
        etISBNLivro.setText("");
        etEdicaoLivro.setText("");
    }
}