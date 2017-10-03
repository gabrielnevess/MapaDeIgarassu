package iphan.pibex.igarassu.ifpe.edu.br.DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import iphan.pibex.igarassu.ifpe.edu.br.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Location;

public class DataBase {

    private SQLiteDatabase database;

    public DataBase(Context context) {
        ConnectionDataBase db = new ConnectionDataBase(context);
        database = db.getWritableDatabase();
    }

    public void insertLocation(Location location) {

        ContentValues values = new ContentValues();
        values.put(Constants.NAME, location.getName());
        values.put(Constants.LONGITUDE, location.getLongitude());
        values.put(Constants.LATITUDE, location.getLatitude());
        values.put(Constants.ADDRESS, location.getAddress());
        values.put(Constants.DESCRIPTION, location.getDescription());

        Log.e("", "" + values.get(Constants.NAME));
        database.insert(Constants.TABLE, null, values);
    }

    public Location searchLocation(String name) {

        @SuppressLint("Recycle")
        Cursor cursor = database.query(Constants.TABLE, new String[]{Constants.NAME, Constants.ADDRESS, Constants.DESCRIPTION}, Constants.NAME + " = \'" + name + "\' ", null, null, null, null);
        cursor.moveToNext();

        Location location = new Location();
        location.setName(cursor.getString(0));
        location.setAddress(cursor.getString(1));
        location.setDescription(cursor.getString(2));

        return location;

    }

}
