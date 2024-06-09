package br.edu.fateczl.aluguel_livros.Persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.fateczl.aluguel_livros.Model.Livro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LivroDAO implements ILivroDAO, ICRUDDAO<Livro> {
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public LivroDAO(Context context) {
        this.context = context;
    }

    @Override
    public LivroDAO open() throws SQLException {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    private static ContentValues getContentValues(Livro livro, boolean isSuper) {
        ContentValues contentValues = new ContentValues();
        if (isSuper) {
            contentValues.put("id", livro.getExemplarId());
            contentValues.put("nome", livro.getExemplarNome());
            contentValues.put("paginas", livro.getExemplarPaginas());
        } else {
            contentValues.put("isbn", livro.getLivroISBN());
            contentValues.put("edicao", livro.getEdicaoLivro());
            contentValues.put("exemplarId", livro.getExemplarId());
        }

        return contentValues;
    }

    @Override
    public void inserir(Livro livro) throws SQLException {
        db.insert("exemplar", null, getContentValues(livro, true));
        db.insert("livro", null, getContentValues(livro, false));
    }

    @Override
    public void alterar(Livro livro) throws SQLException {
        db.update("exemplar", getContentValues(livro, true),
                "id = " + livro.getExemplarId(), null);
        db.update("livro", getContentValues(livro, false),
                "exemplarId = " + livro.getExemplarId(), null);
    }

    @Override
    public void deletar(Livro livro) throws SQLException {
        db.delete("livro", "exemplarId = " + livro.getExemplarId(), null);
        db.delete("exemplar", "id = " + livro.getExemplarId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Livro buscar(Livro livro) throws SQLException {
        String querySQL = "SELECT " +
                "exemplar.id , exemplar.nome, exemplar.paginas, livro.isbn, livro.edicao " +
                "FROM exemplar, book " +
                "WHERE exemplar.id = book.exemplarId " +
                "AND exemplar.id = " + livro.getExemplarId();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            livro.setExemplarId(cursor.getInt(cursor.getColumnIndex("id")));
            livro.setExemplarNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setExemplarPaginas(cursor.getInt(cursor.getColumnIndex("paginas")));
            livro.setLivroISBN(cursor.getString(cursor.getColumnIndex("isbn")));
            livro.setEdicaoLivro(cursor.getInt(cursor.getColumnIndex("edicao")));
        }

        cursor.close();

        return livro;
    }

    @SuppressLint("Range")
    @Override
    public List<Livro> listar() throws SQLException {
        List<Livro> livros = new ArrayList<>();

        String querySQL = "SELECT " +
                "exemplar.id , exemplar.nome, exemplar.paginas, livro.isbn, livro.edicao " +
                "FROM exemplar, livro " +
                "WHERE exemplar.id = livro.exemplarId ";

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Livro livro = new Livro(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getInt(cursor.getColumnIndex("paginas")),
                    cursor.getString(cursor.getColumnIndex("isbn")),
                    cursor.getInt(cursor.getColumnIndex("edicao"))
            );

            livros.add(livro);

            cursor.moveToNext();
        }

        cursor.close();

        return livros;
    }
}
