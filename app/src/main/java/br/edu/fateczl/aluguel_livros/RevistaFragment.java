package br.edu.fateczl.aluguel_livros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.aluguel_livros.Model.Revista;
import br.edu.fateczl.aluguel_livros.Persistence.RevistaDAO;
import br.edu.fateczl.aluguel_livros.controller.RevistaController;


public class RevistaFragment extends Fragment {
    private View view;
    private EditText etIdRevista;
    private EditText etNomeRevista;
    private EditText etPaginasRevista;
    private EditText etISSNRevista;
    private TextView tvSaidaRevista;
    private RevistaController revistaController;

    public RevistaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_revista, container, false);

        etIdRevista = view.findViewById(R.id.etIdRevista);
        etNomeRevista = view.findViewById(R.id.etNomeRevista);
        etPaginasRevista = view.findViewById(R.id.etPaginasRevista);
        etISSNRevista = view.findViewById(R.id.etISSNRevista);
        tvSaidaRevista = view.findViewById(R.id.tvSaidaRevista);
        tvSaidaRevista.setMovementMethod(new ScrollingMovementMethod());

        revistaController = new RevistaController(new RevistaDAO(this.getContext()));

        view.findViewById(R.id.btnBuscarRevista).setOnClickListener(op -> buscar());
        view.findViewById(R.id.btnInserirRevista).setOnClickListener(op -> inserir());
        view.findViewById(R.id.btnAlterarRevista).setOnClickListener(op -> alterar());
        view.findViewById(R.id.btnDeletarRevista).setOnClickListener(op -> deletar());
        view.findViewById(R.id.btnListarRevista).setOnClickListener(op -> listar());

        return view;
    }

    private void buscar() {
        Revista revista;

        try {
            revista = revistaController.buscar(new Revista(
                    Integer.parseInt(etIdRevista.getText().toString()),
                    null,
                    0,
                    null)
            );

            if (revista.getExemplarNome() != null) {
                lerRevista(revista);

            } else {
                Toast.makeText(
                        view.getContext(), "Revista não encontrada!", Toast.LENGTH_LONG).show();

                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void inserir() {
        try {
            revistaController.inserir(escreverRevista());

            Toast.makeText(
                    view.getContext(), "Revista cadastrada com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void alterar() {
        try {
            revistaController.alterar(escreverRevista());

            Toast.makeText(
                    view.getContext(), "Revista atualizada com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void deletar() {
        try {
            revistaController.deletar(
                    new Revista(
                            Integer.parseInt(etIdRevista.getText().toString()), null, 0, null));

            Toast.makeText(
                    view.getContext(), "Revista deletada com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void listar() {
        try {
            List<Revista> revistas = revistaController.listar();

            StringBuilder stringBuffer = new StringBuilder();

            for (Revista revista : revistas) {
                stringBuffer.append(revista.toString()).append("\n");
            }

            tvSaidaRevista.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Revista escreverRevista() throws SQLException {
        if (!validacao()) {
            throw new SQLException("Dados inválidos");
        }

        return new Revista(
                Integer.parseInt(etIdRevista.getText().toString()),
                etNomeRevista.getText().toString(),
                Integer.parseInt(etPaginasRevista.getText().toString()),
                etISSNRevista.getText().toString()
        );
    }

    private boolean validacao() {
        if (etIdRevista.length() == 0) {
            return false;
        }

        if (etNomeRevista.length() == 0) {
            return false;
        }

        if (etPaginasRevista.length() == 0) {
            return false;
        }

        if (etISSNRevista.length() == 0) {
            return false;
        }

        return true;
    }

    private void lerRevista(Revista revista) {
        etIdRevista.setText(String.valueOf(revista.getExemplarId()));
        etNomeRevista.setText(revista.getExemplarNome());
        etPaginasRevista.setText(String.valueOf(revista.getExemplarPaginas()));
        etISSNRevista.setText(revista.getRevistaISSN());
    }

    private void limpaCampos() {
        etIdRevista.setText("");
        etNomeRevista.setText("");
        etPaginasRevista.setText("");
        etISSNRevista.setText("");
    }

}


