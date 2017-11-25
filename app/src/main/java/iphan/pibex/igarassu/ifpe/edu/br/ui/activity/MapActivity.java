package iphan.pibex.igarassu.ifpe.edu.br.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import iphan.pibex.igarassu.ifpe.edu.br.service.NavigationModeService;
import iphan.pibex.igarassu.ifpe.edu.br.ui.adapter.GoogleInfoWindowAdapter;
import iphan.pibex.igarassu.ifpe.edu.br.ui.fragments.DialogTypeMapsFragment;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.ui.other.InvokeAddMarkerMapOther;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.model.GoogleMapsModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.GeolocationUtil;
import iphan.pibex.igarassu.ifpe.edu.br.util.SharedPreferencesUtil;

import static iphan.pibex.igarassu.ifpe.edu.br.R.id.map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener {

    private InvokeAddMarkerMapOther invokeAddMarkerMapOther;
    private View markerView;
    private final Context context;
    private SearchView searchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private LocationModel locationModel;
    private android.os.CountDownTimer gpsProviderListenerTimer;

    public MapActivity() {
        this.context = this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Cabeçalho do menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Inflate para o pop-up dos markers(Janela em cima do marker)
        this.markerView = getLayoutInflater().inflate(R.layout.marker_view, null);

        invokeAddMarkerMapOther = new InvokeAddMarkerMapOther(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        GoogleMapsModel.setMap(googleMap);

        //Verificação de tipos de mapa
        if (Constants.MAP_TYPE_HYBRID == SharedPreferencesUtil.getTypeMaps(this)) {
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_HYBRID);
        } else {
            GoogleMapsModel.getMap().setMapType(Constants.MAP_TYPE_NORMAL);
        }

        if (SharedPreferencesUtil.isNewPoints(this)) {
            invokeAddMarkerMapOther.onAddMarkerFirebase();
            SharedPreferencesUtil.isNewPoints(this, false);
        } else {
            invokeAddMarkerMapOther.onAddMarkerSqlite(); //chamada do metodo onAddMarkerSqlite, isso fará que adicione os pontos
        }

        GoogleMapsModel.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16)); /*Centro do mapa*/
        GoogleMapsModel.getMap().setOnMarkerClickListener(this); //Listener

        //Botões de Zoom
        GoogleMapsModel.getMap().getUiSettings().setZoomControlsEnabled(true);

        infoWindow(); //chamada do método infoWindow

        //Listener de cada marker
        GoogleMapsModel.getMap().setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                DataBaseUtil dataBaseUtil = new DataBaseUtil(context); //Instância da base de dados local

                String name = marker.getTitle(); //pegando nome do marker
                locationModel = dataBaseUtil.getLocation(name); //query no sqlite passando nome

                if (name.equals(locationModel.getName())) { //verificação se o nome do marcador é igual algum nome está no banco sqlite
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

    //Método infoWindow, colocar pop-up para todos os marker
    private void infoWindow() {

        if (GoogleMapsModel.getMap() != null) {
            GoogleMapsModel.getMap().setInfoWindowAdapter(new GoogleInfoWindowAdapter(markerView));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //Menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) { //Ação para ir a tela de sobre
            Intent intent = new Intent(MapActivity.this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_type_maps) { // Alert Dialog para escolher o tipo do mapa
            DialogTypeMapsFragment.alertDialog(this);

        } else if (id == R.id.nav_check_new_points) {
            invokeAddMarkerMapOther.onAddMarkerFirebase(); // Adicionando MarkerOther no mapa
            SharedPreferencesUtil.isNewPoints(this, false); // false, há novos pontos
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() { //Método de back do botão do celular

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        this.mSearchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        this.mSearchAutoComplete.setDropDownBackgroundResource(R.color.white); //cor da lista auto complete
        this.mSearchAutoComplete.setDropDownAnchor(R.id.search);
        this.mSearchAutoComplete.setThreshold(0);
        this.searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) { //método será chamado quando for clicado na seta de voltar na barra de busca ou back do botão do celular

                GoogleMapsModel.getMap().clear(); //limpar o mapa
                GoogleMapsModel.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(Constants.CENTER_LOCATION, 16)); //centraliza o mapa com animação
                InvokeAddMarkerMapOther invokeAddMarkerMapOther = new InvokeAddMarkerMapOther(context); //instância de InvokeAddMarkerMapOther
                invokeAddMarkerMapOther.onAddMarkerSqlite(); //chamada do metodo onAddMarkerSqlite, isso fará que adicione os pontos

                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            } //método será chamando quando o menu for expandido

        });

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Log.d("Escrito", " " + s);
                final DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, dataBaseUtil.searchLocation(s));
                mSearchAutoComplete.setAdapter(adapter); //colocando titulos do marcadores na listView
                dataBaseUtil.searchLocation(s); //pesquisando no banco sqlite pelo nome digitado

                mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String valueItemList = (String) adapterView.getItemAtPosition(i); //Pega o valor do item do listView
                        Log.d("mSearchAutoCompleteItem", " " + valueItemList); //Log

                        dataBaseUtil.searchLocation(valueItemList); //query no banco sqlite passando o nome clicado da lista
                        locationModel = dataBaseUtil.getLocation(valueItemList); //query no banco sqlite passando o nome clicado da lista, para pegar longitude e latitude do item clicado

                        if (valueItemList.equals(locationModel.getName())) { //comparando se o valor achado é igual encontrado no banco ao que está lista
                            GoogleMapsModel.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(locationModel.getLatitude(), locationModel.getLongitude()), 16)); //centralizando mapa para o ponto clicado da lista
                            searchView.clearFocus(); //escondendo teclado
                        }

                    }
                });

                return false;
            }

        });

        return true;

    }

    //verificar se a thread(serviço do modo navegação) está em funcionamento
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupGpsProviderListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseGpsProviderListener();
    }

    /**
     * Configura o listener para o provedor de GPS.
     */
    public void setupGpsProviderListener() {
        // checa a cada 1 segundo se o gps esta ligado
        gpsProviderListenerTimer = new android.os.CountDownTimer(Constants.TIME_UPDATE_GPS_PROVIDER_LISTENER,
                Constants.TIME_UPDATE_GPS_PROVIDER_LISTENER) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (GeolocationUtil.isGPSEnabled(context)) {
                    if (!isMyServiceRunning(NavigationModeService.class)) {
                        Toast.makeText(getApplicationContext(), "Modo Navegação Iniciado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MapActivity.this, NavigationModeService.class);
                        startService(intent);
                    }
                } else {
                    if (isMyServiceRunning(NavigationModeService.class)) {
                        Intent intent = new Intent(MapActivity.this, NavigationModeService.class);
                        stopService(intent);
                    }
                }
            }

            @Override
            public void onFinish() {
                this.start();
            }
        }.start();
    }

    /**
     * Método para liberar os recursos do listener
     */
    public void releaseGpsProviderListener() {
        gpsProviderListenerTimer.cancel();
        gpsProviderListenerTimer = null;
    }

}
