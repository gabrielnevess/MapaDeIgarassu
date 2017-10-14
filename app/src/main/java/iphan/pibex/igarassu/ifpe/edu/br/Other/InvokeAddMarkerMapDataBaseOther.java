package iphan.pibex.igarassu.ifpe.edu.br.Other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.Util.DataBaseUtil;

public class InvokeAddMarkerMapDataBaseOther extends DataBaseUtil implements OnMapReadyCallback {

    public InvokeAddMarkerMapDataBaseOther(Context context) {
        super(context);
    }

    public void onAddMarker() {

        GoogleMapsModel.getMap().clear(); /*Limpando o mapa*/
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery(Constants.SELECT_ALL, null);

        while (cursor.moveToNext()) {

//            int id = cursor.getColumnIndex(Constants.ID);
            int name = cursor.getColumnIndex(Constants.NAME);
            int longitude = cursor.getColumnIndex(Constants.LONGITUDE);
            int latitude = cursor.getColumnIndex(Constants.LATITUDE);

            MarkerOther.marker(cursor.getString(name), cursor.getDouble(latitude), cursor.getDouble(longitude)); //add marker

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }
}
