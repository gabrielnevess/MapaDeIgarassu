package iphan.pibex.igarassu.ifpe.edu.br.Activity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import iphan.pibex.igarassu.ifpe.edu.br.Adapter.GoogleInfoWindowAdapter;
import iphan.pibex.igarassu.ifpe.edu.br.Fragments.DialogTypeMapsFragment;
import iphan.pibex.igarassu.ifpe.edu.br.Model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.Other.AddMarkerMapFirebase;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.Constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.Model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.Util.SharedPrefUtil;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private AddMarkerMapFirebase addMarkerMapFirebase;
    private View markerView;

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

        GoogleMapsModel.setMap(googleMap);

        if(SharedPrefUtil.getTypeMaps(this).equals(Constants.SATELLITE)){
            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else if(SharedPrefUtil.getTypeMaps(this).equals(Constants.TERRAIN)){
            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }else{
            GoogleMapsModel.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }


        addMarkerMapFirebase = new AddMarkerMapFirebase(this);
        addMarkerMapFirebase.onAddMarker();

        GoogleMapsModel.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16));
        GoogleMapsModel.getMap().setOnMarkerClickListener(this);

        /**
         * Botões de Zoom
         */
        GoogleMapsModel.getMap().getUiSettings().setZoomControlsEnabled(true);

        infoWindow();

        GoogleMapsModel.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBaseUtil dataBaseUtil = new DataBaseUtil(getApplicationContext());

                String name = marker.getTitle();
                LocationModel locationModel = dataBaseUtil.searchLocation(name);

                if (name.equals(locationModel.getName())) {
                    Intent intent = new Intent(MapActivity.this, SeeMoreActivity.class);
                    Bundle b = new Bundle();
                    b.putString("name", locationModel.getName());
                    b.putString("address", locationModel.getAddress());
                    b.putString("description", locationModel.getDescription());
                    intent.putExtras(b);
                    startActivity(intent);

                }
            }


        });

    }

    private void infoWindow() {

        if (GoogleMapsModel.getMap() != null) {
            GoogleMapsModel.getMap().setInfoWindowAdapter(new GoogleInfoWindowAdapter(markerView));
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
            Intent intent = new Intent(MapActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_type_maps) {
            DialogTypeMapsFragment.alertDialog(this);
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
