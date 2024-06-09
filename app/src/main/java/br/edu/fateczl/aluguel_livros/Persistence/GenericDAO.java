package br.edu.fateczl.aluguel_livros.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class GenericDAO extends SQLiteOpenHelper {
    private static final String DATABASE = "ALUGUELLIVROS.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_ALUNO =
            "CREATE TABLE aluno( " +
                    "id INTEGER(10) NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(50) NOT NULL);";
    private static final String CREATE_TABLE_EXEMPLAR =
            "CREATE TABLE exemplar( " +
                    "id INTEGER(10) NOT NULL PRIMARY KEY, " +
                    "nome VARCHAR(50) NOT NULL, " +
                    "paginas INTEGER(10) NOT NULL);";
    private static final String CREATE_TABLE_LIVRO =
            "CREATE TABLE livro( " +
                    "isbn CHAR(13) NOT NULL, " +
                    "edicao INTEGER(10) NOT NULL, " +
                    "exemplarId INTEGER(10), " +
                    "FOREIGN KEY (exemplarId) REFERENCES exemplar (id) ON DELETE CASCADE);";
    private static final String CREATE_TABLE_REVISTA =
            "CREATE TABLE revista( " +
                    "issn CHAR(8) NOT NULL, " +
                    "exemplarId INTEGER(10), " +
                    "FOREIGN KEY (exemplarId) REFERENCES exemplar (id) ON DELETE CASCADE);";
    private static final String CREATE_TABLE_ALUGUEL =
            "CREATE TABLE aluguel( " +
                    "dataRetirada VARCHAR(10) NOT NULL, " +
                    "dataRetorno VARCHAR(10) NOT NULL, " +
                    "IdAluno INTEGER(10),  " +
                    "exemplarId INTEGER(10), " +
                    "FOREIGN KEY (IdAluno) REFERENCES aluno (id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (exemplarId) REFERENCES exemplar (id) ON DELETE CASCADE, " +
                    "PRIMARY KEY (dataRetirada, IdAluno, exemplarId));";

    public GenericDAO(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUNO);
        db.execSQL(CREATE_TABLE_EXEMPLAR);
        db.execSQL(CREATE_TABLE_LIVRO);
        db.execSQL(CREATE_TABLE_REVISTA);
        db.execSQL(CREATE_TABLE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int antigaVersao, int novaVersao) {
        if (novaVersao > antigaVersao) {
            db.execSQL("DROP TABLE IF EXISTS aluguel");
            db.execSQL("DROP TABLE IF EXISTS revista");
            db.execSQL("DROP TABLE IF EXISTS livro");
            db.execSQL("DROP TABLE IF EXISTS exemplar");
            db.execSQL("DROP TABLE IF EXISTS aluno");

            onCreate(db);
        }
    }
}