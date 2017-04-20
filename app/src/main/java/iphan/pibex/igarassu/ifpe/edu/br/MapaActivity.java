package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private View markerView;
    private CustomApplication application;
    private FloatingActionButton aboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        application = (CustomApplication) getApplication();
        application.populateLocations();

        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

        onClick();

    }

    private void onClick() {
        aboutMe = (FloatingActionButton) findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sobre = new Intent(MapaActivity.this, Sobre.class);
                startActivity(sobre);
            }
        });
    }

    private static final LatLng CENTER_LOCATION = new LatLng(-7.834195, -34.906142);

    @Override
    public void onMapReady(GoogleMap googleMap) {


        this.mMap = googleMap;

        populateMap();

        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER_LOCATION, 16));
        this.mMap.setOnMarkerClickListener(this);

        this.mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                String name = marker.getTitle();

                int i;
                for (i = 0; i < application.getLocations().length; i++) {
                    if (name.equals(application.getLocations()[i].getName())) {
                        break;
                    }
                }

                Intent intent = new Intent(MapaActivity.this, VejaMais.class);
                Bundle b = new Bundle();
                b.putInt("id", i);
                intent.putExtras(b);
                startActivity(intent);

            }
        });


    }

    private void infoWindow() {

        if (mMap != null) {
            this.mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override

                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    TextView tvLocality = (TextView) markerView.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) markerView.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) markerView.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) markerView.findViewById(R.id.tv_snippet);

                    LatLng location = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + location.latitude);
                    tvLng.setText("Longitude: " + location.longitude);
                    tvSnippet.setText(marker.getTitle());

                    return markerView;
                }
            });
        }
    }

    private void populateMap() {

        for (Location l : application.getLocations()) {
            LatLng location = new LatLng(l.getLongitude(), l.getLatitude());

            MarkerOptions options = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marcador_mapa))
                    .title(l.getName())
                    .position(location);
            this.mMap.addMarker(options);

            infoWindow();

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
