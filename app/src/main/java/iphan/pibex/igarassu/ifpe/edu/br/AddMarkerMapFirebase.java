package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import iphan.pibex.igarassu.ifpe.edu.br.Firebase.ConnectionFireBase;

public class AddMarkerMapFirebase implements OnMapReadyCallback {

    private Context context;

    public AddMarkerMapFirebase(Context context){
        this.context = context;
    }

    public void loadMarker() {

        ConnectionFireBase.getReferenceFirebase()
                .child("locations")
                .addValueEventListener(new ValueEventListenerMarker(this.context));
    }

    public void onAddMarker() {
        loadMarker();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        onAddMarker();
    }

}

