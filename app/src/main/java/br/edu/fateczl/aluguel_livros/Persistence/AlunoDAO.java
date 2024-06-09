package br.edu.fateczl.aluguel_livros.Persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.aluguel_livros.Model.Aluno;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO  implements IAlunoDAO, ICRUDDAO<Aluno> {
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public AlunoDAO(Context context) {
        this.context = context;
    }

    @Override
    public AlunoDAO open() throws SQLException {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    private static ContentValues getContentValues(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", aluno.getIdAluno());
        contentValues.put("nome", aluno.getNomeAluno());
        contentValues.put("email", aluno.getEmailAluno());

        return contentValues;
    }

    @Override
    public void inserir(Aluno aluno) throws SQLException {
        db.insert("aluno", null, getContentValues(aluno));
    }

    @Override
    public void alterar(Aluno aluno) throws SQLException {
        db.update("aluno", getContentValues(aluno),
                "id = " + aluno.getIdAluno(), null);
    }

    @Override
    public void deletar(Aluno aluno) throws SQLException {
        db.delete("aluno", "id = " + aluno.getIdAluno(), null);
    }

    @SuppressLint("Range")
    @Override
    public Aluno buscar(Aluno aluno) throws SQLException {
        String querySQL = "SELECT " +
                "id, nome, email " +
                "FROM aluno " +
                "WHERE id = " + aluno.getIdAluno();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            aluno.setIdAluno(cursor.getInt(cursor.getColumnIndex("id")));
            aluno.setNomeAluno(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmailAluno(cursor.getString(cursor.getColumnIndex("email")));
        }

        cursor.close();

        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> listar() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();

        String querySQL = "SELECT " +
                "id, nome, email " +
                "FROM aluno";

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Aluno aluno = new Aluno(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("email"))
            );

            alunos.add(aluno);

            cursor.moveToNext();
        }

        cursor.close();

        return alunos;
    }
}