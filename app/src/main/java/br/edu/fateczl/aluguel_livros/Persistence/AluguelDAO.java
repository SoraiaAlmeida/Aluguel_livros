package br.edu.fateczl.aluguel_livros.Persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.aluguel_livros.Model.Livro;
import br.edu.fateczl.aluguel_livros.Model.Revista;
import br.edu.fateczl.aluguel_livros.Model.Aluguel;
import br. edu.fateczl.aluguel_livros.Model.Aluno;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO implements IAluguelDAO, ICRUDDAO<Aluguel> {
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public AluguelDAO(Context context) {
        this.context = context;
    }

    @Override
    public AluguelDAO open() throws SQLException {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(true);

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    private static ContentValues getContentValues(Aluguel aluguel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("dataRetirada", aluguel.getAluguelDataRetirada().toString());
        contentValues.put("dataRetorno", aluguel.getAluguelDataRetorno().toString());
        contentValues.put("IdAluno", aluguel.getAluguelAluno().getIdAluno());
        contentValues.put("exemplarId", aluguel.getAluguelExemplar().getExemplarId());

        return contentValues;
    }

    @Override
    public void inserir (Aluguel aluguel) throws SQLException {
        db.insert("aluguel", null, getContentValues(aluguel));
    }

    @Override
    public void alterar (Aluguel aluguel) throws SQLException {
        String whereQuery = "dataRetirada = ? AND IdAluno = ? AND exemplarID = ?";
        String[] whereArgs = new String[]{
                aluguel.getAluguelDataRetirada().toString(),
                String.valueOf(aluguel.getAluguelAluno().getIdAluno()),
                String.valueOf(aluguel.getAluguelExemplar().getExemplarId())};

        db.update("aluguel", getContentValues(aluguel), whereQuery, whereArgs);
    }

    @Override
    public void deletar(Aluguel aluguel) throws SQLException {
        String whereQuery = "dataRetirada = ? AND IdAluno = ? AND exemplarID = ?";
        String[] whereArgs = new String[]{
                aluguel.getAluguelDataRetirada().toString(),
                String.valueOf(aluguel.getAluguelAluno().getIdAluno()),
                String.valueOf(aluguel.getAluguelExemplar().getExemplarId())};

        db.delete("aluguel", whereQuery, whereArgs);
    }

    @SuppressLint("Range")
    @Override
    public Aluguel buscar(Aluguel aluguel) throws SQLException {
        String querySQL = "SELECT " +
                "aluguel.dataRetirada AS aDataRetirada, aluguel.dataretorno AS aDataRetorno, " +
                "aluno.id AS sId, aluno.nome AS aNome, aluno.email AS aEmail, " +
                "exemplar.id AS eId, exemplar.nome AS eNome, exemplar.paginas AS ePaginas, " +
                "livro.isbn AS lIsbn, livro.edicao AS lEdicao, " +
                "revista.issn AS rIssn " +
                "FROM aluguel " +
                "INNER JOIN aluno ON aluguel.IdAluno = aluno.id " +
                "INNER JOIN exemplar ON aluguel.exemplarId = exemplar.id " +
                "LEFT JOIN revista ON exemplar.id = revista.exemplarId " +
                "LEFT JOIN livro ON exemplar.id = livro.exemplarId " +
                "WHERE aluguel.DataRetirada = " + aluguel.getAluguelDataRetirada().toString() + " " +
                "AND aluguel.IdAluno = " + aluguel.getAluguelAluno().getIdAluno() + " " +
                "AND aluguel.exemplarId = " + aluguel.getAluguelExemplar().getExemplarId();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            Aluno aluno = new Aluno(
                    cursor.getInt(cursor.getColumnIndex("aId")),
                    cursor.getString(cursor.getColumnIndex("aNome")),
                    cursor.getString(cursor.getColumnIndex("aEmail"))
            );
            aluguel.setAluguelAluno(aluno);

            if (!cursor.isNull(cursor.getColumnIndex("lIsbn"))) {
                Livro livro = new Livro(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePaginas")),
                        cursor.getString(cursor.getColumnIndex("lIsbn")),
                        cursor.getInt(cursor.getColumnIndex("lEdition"))
                );
                aluguel.setAluguelExemplar(livro);

            } else {
                Revista revista = new Revista(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePages")),
                        cursor.getString(cursor.getColumnIndex("rIssn"))
                );
                aluguel.setAluguelExemplar(revista);

            }

            aluguel.setAluguelDataRetirada(LocalDate.parse(
                    cursor.getString(cursor.getColumnIndex("alDataRetirada"))));
            aluguel.setAluguelDataRetorno(LocalDate.parse(
                    cursor.getString(cursor.getColumnIndex("alDataRetorno"))));

        }

        cursor.close();

        return aluguel;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluguel> listar() throws SQLException {
        List<Aluguel> alugueis = new ArrayList<>();

        String querySQL = "SELECT " +
                "aluguel.dataRetirada AS alDataRetirada, aluguel.dataretorno AS alDataRetorno, " +
                "aluno.id AS aId, aluno.nome AS aNome, aluno.email AS aEmail, " +
                "exemplar.id AS eId, exemplar.nome AS eNome, exemplar.paginas AS ePaginas, " +
                "livro.isbn AS lIsbn, livro.edicao AS lEdition, " +
                "revista.issn AS rIssn " +
                "FROM aluguel " +
                "INNER JOIN aluno ON aluguel.IdAluno = aluno.id " +
                "INNER JOIN exemplar ON aluguel.exemplarId = exemplar.id " +
                "LEFT JOIN revista ON exemplar.id = revista.exemplarId " +
                "LEFT JOIN livro ON exemplar.id = livro.exemplarId ";

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Aluguel aluguel = new Aluguel();

            Aluno aluno = new Aluno(
                    cursor.getInt(cursor.getColumnIndex("aId")),
                    cursor.getString(cursor.getColumnIndex("aNome")),
                    cursor.getString(cursor.getColumnIndex("aEmail"))
            );
            aluguel.setAluguelAluno(aluno);

            if (!cursor.isNull(cursor.getColumnIndex("lIsbn"))) {
                Livro livro = new Livro(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePaginas")),
                        cursor.getString(cursor.getColumnIndex("lIsbn")),
                        cursor.getInt(cursor.getColumnIndex("lEdicao"))
                );
                aluguel.setAluguelExemplar(livro);

            } else {
                Revista revista = new Revista(
                        cursor.getInt(cursor.getColumnIndex("eId")),
                        cursor.getString(cursor.getColumnIndex("eNome")),
                        cursor.getInt(cursor.getColumnIndex("ePaginas")),
                        cursor.getString(cursor.getColumnIndex("rIssn"))
                );
                aluguel.setAluguelExemplar(revista);

            }

            aluguel.setAluguelDataRetirada(LocalDate.parse(
                    cursor.getString(cursor.getColumnIndex("alDataRetirada"))));
            aluguel.setAluguelDataRetirada(LocalDate.parse(
                    cursor.getString(cursor.getColumnIndex("alDataRetorno"))));

            alugueis.add(aluguel);

            cursor.moveToNext();
        }

        cursor.close();

        return alugueis;
    }
}