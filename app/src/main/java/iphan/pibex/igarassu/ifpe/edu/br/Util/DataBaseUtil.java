package iphan.pibex.igarassu.ifpe.edu.br.Util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.LocationModel;

public class DataBaseUtil {

    private SQLiteDatabase database;

    public DataBaseUtil(Context context) {
        ConnectionDataBaseUtil db = new ConnectionDataBaseUtil(context);
        database = db.getWritableDatabase();
    }

    public void insertLocation(LocationModel locationModel) {

        ContentValues values = new ContentValues();
        values.put(Constants.NAME, locationModel.getName());
        values.put(Constants.LONGITUDE, locationModel.getLongitude());
        values.put(Constants.LATITUDE, locationModel.getLatitude());
        values.put(Constants.ADDRESS, locationModel.getAddress());
        values.put(Constants.DESCRIPTION, locationModel.getDescription());

        Log.e("", "" + values.get(Constants.NAME));
        database.insert(Constants.TABLE, null, values);
    }

    public LocationModel searchLocation(String name) {

        @SuppressLint("Recycle")
        Cursor cursor = database.query(Constants.TABLE, new String[]{Constants.NAME, Constants.ADDRESS, Constants.DESCRIPTION}, Constants.NAME + " = \'" + name + "\' ", null, null, null, null);
        cursor.moveToNext();

        LocationModel locationModel = new LocationModel();
        locationModel.setName(cursor.getString(0));
        locationModel.setAddress(cursor.getString(1));
        locationModel.setDescription(cursor.getString(2));

        return locationModel;

    }

}
