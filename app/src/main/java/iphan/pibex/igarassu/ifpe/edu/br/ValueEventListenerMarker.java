package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import iphan.pibex.igarassu.ifpe.edu.br.DataBase.DataBase;

public class ValueEventListenerMarker implements ValueEventListener {

    private Context context;

    public ValueEventListenerMarker(Context context) {
        this.context = context;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
        GoogleMaps.getMap().clear();

        DataBase dataBase = new DataBase(this.context);

        for (DataSnapshot dataSnapshot1 : dataSnapshots) {

            final Location local = dataSnapshot1.getValue(Location.class);
            GoogleMaps.getMap().addMarker(new MarkerOptions()
                    .position(new LatLng(local.getLatitude(), local.getLongitude()))
                    .title(local.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_map)));
            dataBase.insertLocation(local);

        }

    }

    @Override
    public void onCancelled(DatabaseError error) {
    }

}
