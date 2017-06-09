package iphan.pibex.igarassu.ifpe.edu.br.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iphan.pibex.igarassu.ifpe.edu.br.Constants;

/**
 * Created by gabri on 09/06/2017.
 */

public class DbConnection extends SQLiteOpenHelper{

    public DbConnection(Context context) {
        super(context, Constants.BD_NOME, null, Constants.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location( " +
                "_id integer not null autoincrement, " +
                "name text not null, " +
                "longitude double not null, " +
                "latitude double not null, " +
                "endereco text not null)" +
                "constraint _id_pk primary key (_id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table location;");
        onCreate(db);
    }

}
