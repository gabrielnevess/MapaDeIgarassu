package iphan.pibex.igarassu.ifpe.edu.br.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import iphan.pibex.igarassu.ifpe.edu.br.Location;

public class DataBase {

    public SQLiteDatabase database;

    public DataBase(Context context) {
        ConnectionDataBase db = new ConnectionDataBase(context);
        database = db.getWritableDatabase();
    }

    public void inserirLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put("name", location.getName());
        values.put("longitude", location.getLongitude());
        values.put("latitude", location.getLatitude());
        values.put("address", location.getEndereco());

        Log.e("", "" + values.get("name"));
        database.insert("location", null, values);
    }

    public Location buscarLocation(String name) {

        Cursor cursor = database.query("location", new String[]{"name", "address"}, "name = \'" + name + "\' ", null, null, null, null);
        cursor.moveToNext();

        Location location = new Location();
        location.setName(cursor.getString(0));
        location.setEndereco(cursor.getString(1));

        return location;

    }

}
