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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import iphan.pibex.igarassu.ifpe.edu.br.DataBase.DataBase;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private View markerView;
    private CustomApplication application;
    private FloatingActionButton aboutMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

        onClick();

    }

    private void onClick() {
        aboutMe = (FloatingActionButton) findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sobre = new Intent(MapActivity.this, About.class);
                startActivity(sobre);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        application = (CustomApplication) getApplication();
        application.onAddMarker();

        this.application.setMap(googleMap);

        this.application.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16));
        this.application.getMap().setOnMarkerClickListener(this);

        infoWindow();

        this.application.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBase dataBase = new DataBase(getApplicationContext());

                String name = marker.getTitle();
                Location location = dataBase.buscarLocation(name);

                if (name.equals(location.getName())) {
                    Intent intent = new Intent(MapActivity.this, SeeMore.class);
                    Bundle b = new Bundle();
                    b.putString("name", location.getName());
                    b.putString("address", location.getEndereco());
                    intent.putExtras(b);
                    startActivity(intent);

                }

            }


        });


    }

    private void infoWindow() {

        if (this.application.getMap() != null) {
            this.application.getMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
