package iphan.pibex.igarassu.ifpe.edu.br.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iphan.pibex.igarassu.ifpe.edu.br.constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.ui.other.MarkerOther;

public class DataBaseUtil extends ConnectionDataBaseUtil {

    protected SQLiteDatabase database;
    protected SQLiteStatement stmt;
    protected Cursor cursor;

    public DataBaseUtil(Context context) {
        super(context);
        ConnectionDataBaseUtil db = new ConnectionDataBaseUtil(context);
        database = db.getWritableDatabase();
    }

    public void dropTable() {
        database.execSQL(Constants.DROP_TABLE);
        onCreate(database);
    }

    public void insertLocation(LocationModel locationModel) {

        stmt = database.compileStatement(Constants.INSERT_ALL);
        stmt.bindString(1, locationModel.getName());
        stmt.bindDouble(2, locationModel.getLongitude());
        stmt.bindDouble(3, locationModel.getLatitude());
        stmt.bindString(4, locationModel.getAddress());
        stmt.bindString(5, locationModel.getDescription());
        stmt.executeInsert();
    }

    public void updateLocation(LocationModel locationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(Constants.UPDATE_TABLE_LOCATION);
        stmt.bindString(1, locationModel.getName());
        stmt.bindDouble(2, locationModel.getLongitude());
        stmt.bindDouble(3, locationModel.getLatitude());
        stmt.bindString(4, locationModel.getAddress());
        stmt.bindString(5, locationModel.getDescription());
        stmt.bindLong(6, locationModel.getId());
        stmt.execute();
    }

    public void deleteLocation(int locationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(Constants.DELETE_LOCATION);
        stmt.bindLong(1, locationId);
        stmt.execute();
    }

    public LocationModel searchLocation(String name) {

        cursor = database.query(Constants.TABLE, new String[]{Constants.NAME, Constants.ADDRESS, Constants.DESCRIPTION}, Constants.NAME + " = \'" + name + "\' ", null, null, null, null);
        cursor.moveToNext();

        LocationModel locationModel = new LocationModel();
        locationModel.setName(cursor.getString(0));
        locationModel.setAddress(cursor.getString(1));
        locationModel.setDescription(cursor.getString(2));

        return locationModel;
    }

    public void getLinksToSearchResults(String name) {

        cursor = database.rawQuery(Constants.SELECT_FROM_NAME, new String[]{name + "%"});
        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/

        while (cursor.moveToNext()) {

//          int id = cursor.getColumnIndex(Constants.ID);
            int name1 = cursor.getColumnIndex(Constants.NAME);
            int longitude = cursor.getColumnIndex(Constants.LONGITUDE);
            int latitude = cursor.getColumnIndex(Constants.LATITUDE);

            Log.d("name"," "+cursor.getString(name1));
            Log.d("longitude"," "+cursor.getDouble(longitude));
            Log.d("latitude"," "+cursor.getDouble(latitude));

            MarkerOther.marker(cursor.getString(name1), cursor.getDouble(latitude), cursor.getDouble(longitude)); //add marker

        }

    }


    public void addMarkerSqlite() {
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(Constants.SELECT_ALL, null);

        while (cursor.moveToNext()) {

//          int id = cursor.getColumnIndex(Constants.ID);
            int name = cursor.getColumnIndex(Constants.NAME);
            int longitude = cursor.getColumnIndex(Constants.LONGITUDE);
            int latitude = cursor.getColumnIndex(Constants.LATITUDE);

            MarkerOther.marker(cursor.getString(name), cursor.getDouble(latitude), cursor.getDouble(longitude)); //add marker

        }
    }

}
