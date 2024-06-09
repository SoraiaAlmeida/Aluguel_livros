package br.edu.fateczl.aluguel_livros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fateczl.aluguel_livros.Model.Aluno;
import br.edu.fateczl.aluguel_livros.Persistence.AlunoDAO;
import br.edu.fateczl.aluguel_livros.controller.AlunoController;

import java.sql.SQLException;
import java.util.List;

public class AlunoFragment extends Fragment {

    private View view;
    private EditText edIdAluno;
    private EditText edNomeAluno;
    private EditText edEmailAluno;
    private TextView tvResultadoAluno;
    private AlunoController alunoController;

    public AlunoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluno, container, false);

        edIdAluno = view.findViewById(R.id.edIdAluno);
        edNomeAluno = view.findViewById(R.id.edNomeAluno);
        edEmailAluno = view.findViewById(R.id.edEmailAluno);
        tvResultadoAluno = view.findViewById(R.id.tvResultadoAluno);
        tvResultadoAluno.setMovementMethod(new ScrollingMovementMethod());

            alunoController = new AlunoController(new AlunoDAO(view.getContext()));

        view.findViewById(R.id.btnBuscarAluno).setOnClickListener(op -> buscar());
        view.findViewById(R.id.btnInserirAluno).setOnClickListener(op -> inserir());
        view.findViewById(R.id.btnAlterarAluno).setOnClickListener(op -> alterar());
        view.findViewById(R.id.btnDeletarAluno).setOnClickListener(op -> deletar());
        view.findViewById(R.id.btnListarAluno).setOnClickListener(op -> listar());

        return view;
    }

    private void buscar() {
        Aluno aluno;

        try {

            aluno = alunoController.buscar(new Aluno(
                    Integer.parseInt(edIdAluno.getText().toString()),
                    null,
                    null)
            );

            if (aluno.getNomeAluno() != null) {
                lerAluno(aluno);

            } else {
                Toast.makeText(
                        view.getContext(), "Aluno não encontrado!", Toast.LENGTH_LONG).show();

                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void inserir() {
        try {
            alunoController.inserir(escreverAluno());

            Toast.makeText(
                    view.getContext(), "Aluno cadastrado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void alterar() {
        try {
            alunoController.alterar(escreverAluno());

            Toast.makeText(
                    view.getContext(), "Aluno atualizado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void deletar() {
        try {

            alunoController.deletar(new Aluno(
                    Integer.parseInt(edIdAluno.getText().toString()), null, null));

            Toast.makeText(
                    view.getContext(), "Aluno excluido com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void listar() {
        try {
            List<Aluno> alunos = alunoController.listar();

            StringBuilder stringBuffer = new StringBuilder();

            for (Aluno aluno : alunos) {
                stringBuffer.append(aluno.toString()).append("\n");
            }

            tvResultadoAluno.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Aluno escreverAluno() throws SQLException {
        if (!validacao()) {
            throw new SQLException("Dados inválidos");
        }

        return new Aluno(
                Integer.parseInt(edIdAluno.getText().toString()),
                edNomeAluno.getText().toString(),
                edEmailAluno.getText().toString()
        );
    }

    private boolean validacao() {
        if (edIdAluno.length() == 0) {
            return false;
        }

        if (edNomeAluno.length() == 0) {
            return false;
        }

        if (edEmailAluno.length() == 0) {
            return false;
        }

        return true;
    }

    private void lerAluno(Aluno aluno) {
        edIdAluno.setText(String.valueOf(aluno.getIdAluno()));
        edNomeAluno.setText(aluno.getNomeAluno());
        edEmailAluno.setText(aluno.getEmailAluno());
    }

    private void limpaCampos() {
        edIdAluno.setText("");
        edNomeAluno.setText("");
        edEmailAluno.setText("");
    }
}

