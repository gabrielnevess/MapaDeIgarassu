package iphan.pibex.igarassu.ifpe.edu.br.Other;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.R;

public class MarkerOther {
    public static void marker(String title, Double latitude, Double longitude){
        GoogleMapsModel.getMap().addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_map)));
    }
}
