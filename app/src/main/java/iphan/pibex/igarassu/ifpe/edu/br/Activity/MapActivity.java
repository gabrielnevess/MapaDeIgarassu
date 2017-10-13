package iphan.pibex.igarassu.ifpe.edu.br.Activity;

import android.content.Context;
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
    private final Context context;

    public MapActivity(){
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /*SupportMapFragment ==> Mapa*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Cabeçalho do menu*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*Menu*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Inflate para o pop-up dos markers(Janela em cima do marker)*/
        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMapsModel.setMap(googleMap);

        /*Verificação de tipos de mapa*/
        if(Constants.MAP_TYPE_HYBRID == SharedPrefUtil.getTypeMaps(this)){
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_HYBRID);
        }else{
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_NORMAL);
        }

        /*Adicionando pontos realizando download diretamente do firebase*/
        addMarkerMapFirebase = new AddMarkerMapFirebase(this);
        addMarkerMapFirebase.onAddMarker(); /*Adicionando Marker no mapa*/

        GoogleMapsModel.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16)); /*Centro do mapa*/
        GoogleMapsModel.getMap().setOnMarkerClickListener(this); /*Listener*/

        /*Botões de Zoom*/
        GoogleMapsModel.getMap().getUiSettings().setZoomControlsEnabled(true);

        infoWindow();

        /*Listener de cada marker*/
        GoogleMapsModel.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBaseUtil dataBaseUtil = new DataBaseUtil(context); /*Instância da base de dados local*/

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

    /*Método infoWindow, colocar pop-up para todos os marker*/
    private void infoWindow() {

        if (GoogleMapsModel.getMap() != null) {
            GoogleMapsModel.getMap().setInfoWindowAdapter(new GoogleInfoWindowAdapter(markerView));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /*Menu*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) { /*Ação para ir a tela de sobre*/
            Intent intent = new Intent(MapActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_type_maps) { /*Alert Dialog para escolher o tipo do mapa*/
            DialogTypeMapsFragment.alertDialog(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*Método de back do botão do celular*/
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
