package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private View markerView;
//    private HashMap<Marker, Location> locationMap;

//    private Location search(String title) {
//        return null;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
//        this.locationMap = new HashMap<>();

        CustomApplication application = (CustomApplication) getApplication();
        application.populateLocations();

//        populateMap();

        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

//        Button button_vejamais = (Button) markerView.findViewById(R.id.button_vejamais);
//        button_vejamais.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Button button = (Button) view;
////                ((Button) view).setText("clicked");
//                // buscar a location
//                // criar a nova activity
//                // setar a location
//                // fazer a intent ir para a nova activity
//                Log.d("DEBUG", "View Clicked");
//
//            }
//        });

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

                CustomApplication application = (CustomApplication) getApplication();

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
//                finish();

//                Log.d("DEBUG", "Info View Clicked");
            }
        });

    }

    private void infoWindow() {

//        final MapaActivity activity = this;

        if (mMap != null) {
            this.mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override

                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    //View v = getLayoutInflater().inflate(R.layout.marker_view, null);

                    TextView tvLocality = (TextView) markerView.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) markerView.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) markerView.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) markerView.findViewById(R.id.tv_snippet);

                    LatLng location = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + location.latitude);
                    tvLng.setText("Longitude: " + location.longitude);
                    tvSnippet.setText(marker.getTitle());


//                    MarkerView v = new MarkerView(activity, null);
//                    v.setLocation(locations[0]);
                    Log.d("DEBUG", "Info View");
                    return markerView;
                }
            });
        }
    }


    private void populateMap() {

        CustomApplication application = (CustomApplication) getApplication();

        for (Location l : application.getLocations()) {
            LatLng location = new LatLng(l.getLongitude(), l.getLatitude());
            //this.mMap.addMarker(new MarkerOptions().position(location).title(l.getName()));

            MarkerOptions options = new MarkerOptions()
                    //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .title(l.getName())
                    .position(location);
            this.mMap.addMarker(options);

            infoWindow();

        }
    }


//    private void populateMap() {
//        for (Location l : locations) {
//            LatLng location = new LatLng(l.getLatitude(), l.getLongitude());
//            // by adsl
//            MarkerOptions markerOptions = new MarkerOptions().position(location).title(l.getName());
//            Marker marker = this.mMap.addMarker(markerOptions);
//            this.locationMap.put(marker, l);
//        }
//    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("DEBUG", "Clicked");

        return false;
    }
}
