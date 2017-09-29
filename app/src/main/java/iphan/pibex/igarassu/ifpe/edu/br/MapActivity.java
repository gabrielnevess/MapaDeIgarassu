package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iphan.pibex.igarassu.ifpe.edu.br.DataBase.DataBase;
import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private View markerView;
    private CustomApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        application = (CustomApplication) getApplication();
        application.onAddMarker();

        this.application.setMap(googleMap);

        this.application.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16));
        this.application.getMap().setOnMarkerClickListener(this);

        /**
         * Botões de Zoom
         */
        this.application.getMap().getUiSettings().setZoomControlsEnabled(true);

        infoWindow();

        this.application.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBase dataBase = new DataBase(getApplicationContext());

                String name = marker.getTitle();
                Location location = dataBase.searchLocation(name);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Intent intent = new Intent(MapActivity.this, About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

}
