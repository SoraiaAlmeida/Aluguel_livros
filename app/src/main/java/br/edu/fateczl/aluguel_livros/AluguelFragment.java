package br.edu.fateczl.aluguel_livros;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fateczl.aluguel_livros.Model.Exemplar;
import br.edu.fateczl.aluguel_livros.controller.AlunoController;
import br.edu.fateczl.aluguel_livros.controller.AluguelController;
import br.edu.fateczl.aluguel_livros.controller.LivroController;
import br.edu.fateczl.aluguel_livros.controller.RevistaController;
import br.edu.fateczl.aluguel_livros.Model.Aluno;
import br.edu.fateczl.aluguel_livros.Model.Aluguel;
import br.edu.fateczl.aluguel_livros.Model.Livro;
import br.edu.fateczl.aluguel_livros.Model.Revista;
import br.edu.fateczl.aluguel_livros.Persistence.AlunoDAO;
import br.edu.fateczl.aluguel_livros.Persistence.AluguelDAO;
import br.edu.fateczl.aluguel_livros.Persistence.LivroDAO;
import br.edu.fateczl.aluguel_livros.Persistence.RevistaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class AluguelFragment extends Fragment {

    private View view;
    private Spinner spinnerAluno;
    private Spinner spinnerExemplar;
    private EditText etDataAluguelRetirada;
    private EditText etDataAluguelRetorno;
    private TextView tvSaídaAluguel;
    private AluguelController aluguelController;
    private AlunoController alunoController;
    private LivroController livroController;
    private RevistaController revistaController;
    private List<Aluno> alunos;
    private List<Exemplar> exemplares;

    public AluguelFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aluguel, container, false);

        spinnerAluno = view.findViewById(R.id.spinnerAluno);
        spinnerExemplar = view.findViewById(R.id.spinnerExemplar);
        etDataAluguelRetirada = view.findViewById(R.id.etDataAluguelRetirada);

        etDataAluguelRetorno = view.findViewById(R.id.etDataAluguelRetirada);
        tvSaídaAluguel = view.findViewById(R.id.tvSaídaAluguel);
        tvSaídaAluguel.setMovementMethod(new ScrollingMovementMethod());

        aluguelController = new AluguelController(new AluguelDAO(this.getContext()));
        alunoController = new AlunoController(new AlunoDAO(this.getContext()));
        livroController = new LivroController(new LivroDAO(this.getContext()));
        revistaController = new RevistaController(new RevistaDAO(this.getContext()));

        carregarSpinnerAluno();
        carregarSpinnerExemplar();

        view.findViewById(R.id.btnBuscarAluguel).setOnClickListener(op -> buscar());
        view.findViewById(R.id.btnInserirAluguel).setOnClickListener(op -> inserir());
        view.findViewById(R.id.btnAlterarAluguel).setOnClickListener(op -> alterar());
        view.findViewById(R.id.btnDeletarAluguel).setOnClickListener(op -> deletar());
        view.findViewById(R.id.btnListarAluguel).setOnClickListener(op -> listar());

        return view;
    }

    private void carregarSpinnerAluno() {
        Aluno aluno0 = new Aluno(0, "Selecione um Aluno", null);
        List<Aluno> alunosSelector = new ArrayList<>();

        try {
            alunos = alunoController.listar();

            alunosSelector.add(0, aluno0);
            alunosSelector.addAll(alunos);

            ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<>(
                    view.getContext(), android.R.layout.simple_spinner_item, alunosSelector);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAluno.setAdapter(arrayAdapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void carregarSpinnerExemplar() {
        Revista revista0 = new Revista(0, "Selecione um Exemplar", 0, null);
        List<Exemplar> exemplaresSelector = new ArrayList<>();

        try {
            exemplares = new ArrayList<>();
            exemplares.addAll(livroController.listar());
            exemplares.addAll(revistaController.listar());

            exemplaresSelector.add(0, revista0);
            exemplaresSelector.addAll(exemplares);

            ArrayAdapter<Exemplar> arrayAdapter = new ArrayAdapter<>(
                    view.getContext(), android.R.layout.simple_spinner_item, exemplaresSelector);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerExemplar.setAdapter(arrayAdapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscar() {
        Aluguel aluguel;

        try {
            aluguel = aluguelController.buscar(new Aluguel(
                    (Aluno) spinnerAluno.getSelectedItem(),
                    getExemplar(),
                    LocalDate.parse(etDataAluguelRetirada.getText().toString())));

            if (aluguel.getAluguelAluno().getNomeAluno() != null) {
                lerAluguel(aluguel);

            } else {
                Toast.makeText(
                        view.getContext(), "Aluguel não encontrado!", Toast.LENGTH_LONG).show();

                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void inserir() {
        try {
            aluguelController.inserir(escreverAluguel());

            Toast.makeText(
                    view.getContext(), "Aluguel inserido com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void alterar() {
        try {
            aluguelController.alterar(escreverAluguel());

            Toast.makeText(
                    view.getContext(), "Aluguel atualizado com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void deletar() {
        try {
            aluguelController.deletar(new Aluguel(
                    (Aluno) spinnerAluno.getSelectedItem(),
                    getExemplar(),
                    LocalDate.parse(etDataAluguelRetirada.getText().toString()))
            );

            Toast.makeText(
                    view.getContext(), "Aluguel removido com sucesso!", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        limpaCampos();
    }

    private void listar() {
        try {
            List<Aluguel> rentals = aluguelController.listar();

            StringBuilder stringBuffer = new StringBuilder();

            for (Aluguel rental : rentals) {
                stringBuffer.append(rental.toString()).append("\n");
            }

            tvSaídaAluguel.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Aluguel escreverAluguel() throws SQLException {
        if (!validacao()) {
            throw new SQLException("Invalid input");
        }

        return new Aluguel(
                (Aluno) spinnerAluno.getSelectedItem(),
                getExemplar(),
                LocalDate.parse(etDataAluguelRetirada.getText().toString()),
                LocalDate.parse(etDataAluguelRetorno.getText().toString())
        );
    }

    private boolean validacao() {
        if (etDataAluguelRetirada.length() == 0) {
            return false;
        }

        if (etDataAluguelRetorno.length() == 0) {
            return false;
        }

        if (spinnerAluno.getSelectedItemPosition() == 0) {
            return false;
        }

        if (spinnerAluno.getSelectedItemPosition() == 0) {
            return false;
        }

        return true;
    }

    private Exemplar getExemplar() {
        if (spinnerExemplar.getSelectedItem() instanceof Livro) {
            return (Livro) spinnerExemplar.getSelectedItem();
        } else {
            return (Revista) spinnerExemplar.getSelectedItem();
        }
    }

    private void lerAluguel(Aluguel aluguel) {
        setStudentSpinnerId(aluguel);
        setExemplarSpinnerId(aluguel);
        etDataAluguelRetirada.setText(String.valueOf(aluguel.getAluguelDataRetirada()));
        etDataAluguelRetorno.setText(String.valueOf(aluguel.getAluguelDataRetorno()));
    }

    private void setStudentSpinnerId(Aluguel aluguel) {
        int i = 1;
        for (Aluno aluno : alunos) {
            if (aluno.getIdAluno() == aluguel.getAluguelAluno().getIdAluno()) {
                spinnerAluno.setSelection(i);
            } else {
                i++;
            }
        }
        if (i > alunos.size()) {
            spinnerAluno.setSelection(0);
        }
    }

    private void setExemplarSpinnerId(Aluguel aluguel) {
        int i = 1;
        for (Exemplar exemplar : exemplares) {
            if (exemplar.getExemplarId() == aluguel.getAluguelExemplar().getExemplarId()) {
                spinnerExemplar.setSelection(i);
            } else {
                i++;
            }
        }
        if (i > exemplares.size()) {
            spinnerExemplar.setSelection(0);
        }
    }

    private void limpaCampos() {
        spinnerAluno.setSelection(0);
        spinnerExemplar.setSelection(0);
        etDataAluguelRetirada.setText("");
        etDataAluguelRetorno.setText("");
    }
}
