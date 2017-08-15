package iphan.pibex.igarassu.ifpe.edu.br;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import iphan.pibex.igarassu.ifpe.edu.br.ConnectionDataBase;
import iphan.pibex.igarassu.ifpe.edu.br.Location;

public class DataBase {

    private SQLiteDatabase database;

    public DataBase(Context context) {
        ConnectionDataBase db = new ConnectionDataBase(context);
        database = db.getWritableDatabase();
    }

    public void insertLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put("name", location.getName());
        values.put("longitude", location.getLongitude());
        values.put("latitude", location.getLatitude());
        values.put("address", location.getEndereco());

        Log.e("", "" + values.get("name"));
        database.insert("location", null, values);
    }

    public Location searchLocation(String name) {

        @SuppressLint("Recycle")
        Cursor cursor = database.query("location", new String[]{"name", "address"}, "name = \'" + name + "\' ", null, null, null, null);
        cursor.moveToNext();

        Location location = new Location();
        location.setName(cursor.getString(0));
        location.setEndereco(cursor.getString(1));

        return location;

    }

}
