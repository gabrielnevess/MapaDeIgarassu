package iphan.pibex.igarassu.ifpe.edu.br.Adapter;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import iphan.pibex.igarassu.ifpe.edu.br.R;

public class GoogleInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View markerView;

    public GoogleInfoWindowAdapter(View markerView) {
        this.markerView = markerView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView tvLocality = (TextView) this.markerView.findViewById(R.id.tv_locality);
        TextView tvLat = (TextView) this.markerView.findViewById(R.id.tv_lat);
        TextView tvLng = (TextView) this.markerView.findViewById(R.id.tv_lng);
        TextView tvSnippet = (TextView) this.markerView.findViewById(R.id.tv_snippet);

        LatLng location = marker.getPosition();
        tvLocality.setText(marker.getTitle());
        tvLat.setText("Latitude: " + location.latitude);
        tvLng.setText("Longitude: " + location.longitude);
        tvSnippet.setText(marker.getTitle());

        return this.markerView;
    }
}
