package br.edu.fateczl.aluguel_livros.Persistence;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.aluguel_livros.Model.Revista;

public class RevistaDAO implements IRevistaDAO, ICRUDDAO<Revista> {
    private final Context context;
    private GenericDAO genericDAO;
    private SQLiteDatabase db;

    public RevistaDAO(Context context) {
        this.context = context;
    }

    @Override
    public RevistaDAO open() throws SQLException {
        genericDAO = new GenericDAO(context);
        db = genericDAO.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    private static ContentValues getContentValues(Revista revista, boolean isSuper) {
        ContentValues contentValues = new ContentValues();
        if (isSuper) {
            contentValues.put("id", revista.getExemplarId());
            contentValues.put("nome", revista.getExemplarNome());
            contentValues.put("paginas", revista.getExemplarPaginas());
        } else {
            contentValues.put("issn", revista.getRevistaISSN());
            contentValues.put("exemplarId", revista.getExemplarId());
        }

        return contentValues;
    }

    @Override
    public void inserir(Revista revista) throws SQLException {
        db.insert("exemplar", null, getContentValues(revista, true));
        db.insert("revista", null, getContentValues(revista, false));
    }

    @Override
    public void alterar(Revista revista) throws SQLException {
        db.update("exemplar", getContentValues(revista, true),
                "id = " + revista.getExemplarId(), null);
        db.update("revista", getContentValues(revista, false),

                "exemplarId = " + revista.getExemplarId(), null);
    }

    @Override
    public void deletar(Revista revista) throws SQLException {
        db.delete("revista", "exemplarId = " + revista.getExemplarId(), null);
        db.delete("exemplar", "id = " + revista.getExemplarId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Revista buscar(Revista revista) throws SQLException {
        String querySQL = "SELECT " +
                "exemplar.id, exemplar.nome, exemplar.paginas, revista.issn " +
                "FROM exemplar, revista " +
                "WHERE exemplar.id = revista.exemplarId " +
                "AND exemplar.id = " + revista.getExemplarId();

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()) {
            revista.setExemplarId(cursor.getInt(cursor.getColumnIndex("id")));
            revista.setExemplarNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setExemplarPaginas(cursor.getInt(cursor.getColumnIndex("paginas")));
            revista.setRevistaISSN(cursor.getString(cursor.getColumnIndex("issn")));
        }

        cursor.close();

        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<Revista> listar() throws SQLException {
        List<Revista> revistas = new ArrayList<>();

        String querySQL = "SELECT " +
                "exemplar.id, exemplar.nome, exemplar.paginas, revista.issn " +
                "FROM exemplar, revista " +
                "WHERE exemplar.id = revista.exemplarId ";

        Cursor cursor = db.rawQuery(querySQL, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            Revista revista = new Revista(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getInt(cursor.getColumnIndex("paginas")),
                    cursor.getString(cursor.getColumnIndex("issn"))
            );

            revistas.add(revista);

            cursor.moveToNext();
        }

        cursor.close();

        return revistas;
    }
}