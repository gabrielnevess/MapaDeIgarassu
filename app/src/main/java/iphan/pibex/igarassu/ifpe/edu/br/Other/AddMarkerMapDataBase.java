package iphan.pibex.igarassu.ifpe.edu.br.Other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.DataBaseUtil;

public class AddMarkerMapDataBase extends DataBaseUtil implements OnMapReadyCallback {

    public AddMarkerMapDataBase(Context context) {
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

            Marker.marker(cursor.getString(name), cursor.getDouble(latitude), cursor.getDouble(longitude)); //add marker

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }
}
