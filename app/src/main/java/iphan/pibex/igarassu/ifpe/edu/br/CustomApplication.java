package iphan.pibex.igarassu.ifpe.edu.br;

import android.app.Application;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import iphan.pibex.igarassu.ifpe.edu.br.DataBase.DataBase;
import iphan.pibex.igarassu.ifpe.edu.br.Firebase.ConnectionFireBase;

public class CustomApplication extends Application implements OnMapReadyCallback {

    private GoogleMap map;

    public GoogleMap getMap() {
        return this.map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void loadMarker() {

        ConnectionFireBase.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(

                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                                getMap().clear();

                                DataBase dataBase = new DataBase(getApplicationContext());

                                for (DataSnapshot dataSnapshot1 : dataSnapshots) {

                                    final Location local = dataSnapshot1.getValue(Location.class);
                                    getMap().addMarker(new MarkerOptions()
                                            .position(new LatLng(local.getLatitude(), local.getLongitude()))
                                            .title(local.getName())
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_map)));

                                    dataBase.inserirLocation(local);

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }

                        });

    }

    public void onAddMarker() {
        loadMarker();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }

}

