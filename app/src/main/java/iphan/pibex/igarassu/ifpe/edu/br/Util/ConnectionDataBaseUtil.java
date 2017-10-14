package iphan.pibex.igarassu.ifpe.edu.br.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;

public class ConnectionDataBaseUtil extends SQLiteOpenHelper {

    public ConnectionDataBaseUtil(Context context) {
        super(context, Constants.BD_NOME, null, Constants.VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DROP_TABLE);
        onCreate(db);
    }

}
