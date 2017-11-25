package iphan.pibex.igarassu.ifpe.edu.br.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import iphan.pibex.igarassu.ifpe.edu.br.constants.Constants;
import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.util.GeolocationUtil;


public class NavigationModeService extends Service {

    private Context context;
    public List<Task> tasks = new ArrayList<Task>();
    public static final String WAKELOCK_NAME = "wakeLockName";
    private PowerManager.WakeLock wakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;

        // configuracao para que o device nao pare de rodar os processos de CPU ao ficar em modo sleep
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKELOCK_NAME);
        wakeLock.acquire();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Task task = new Task(context, startId);
        tasks.add(task);
        task.start();
        return START_REDELIVER_INTENT;
    }

    private class Task extends Thread implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        private GoogleApiClient mGoogleApiClient;
        private boolean active = true;
        private int startId;
        private Context context;
        private LocationModel locationModel;
        private Location location;

        public Task(Context context, int startId) {
            this.startId = startId;
            this.context = context;
        }

        private synchronized void callConnection() {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addOnConnectionFailedListener(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }

        @Override
        public void onConnectionSuspended(int i) { }
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

        @SuppressLint("MissingPermission") //ignorando permissão em tempo de execução para o gps
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                locationModel.setLatitude(location.getLatitude());
                locationModel.setLongitude(location.getLongitude());
            }
        }

        @SuppressLint("StaticFieldLeak")
        public void run() {

            while (active) {

                try {

                    new AsyncTask<Void, Void, String>() {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            callConnection();
                        }

                        @Override
                        protected String doInBackground(Void... params) {

                            List<LocationModel> list;
                            DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
                            list = dataBaseUtil.getLocationNavigationMode();

                            String name = " ";
                            for (int i = 0; i < list.size(); i++) {

                                if (GeolocationUtil.getDistance(locationModel.getLatitude(), locationModel.getLongitude(),
                                        list.get(i).getLatitude(), list.get(i).getLongitude()) <= Constants.MINIMUM_DISTANCE) {

                                    name = list.get(i).getName();

                                }

                            }
                            return name;
                        }

                        @Override
                        protected void onPostExecute(String params) {
                            super.onPostExecute(params);
                            Toast.makeText(getApplicationContext(), "ESTOU PERTO DE: " + params, Toast.LENGTH_LONG).show();
                        }

                    }.execute();

                    TimeUnit.SECONDS.sleep(Constants.UPDATE_TIME);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stopSelf(startId);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();
        for (int i = 0, tam = tasks.size(); i < tam; i++) {
            tasks.get(i).active = false;
        }
    }

}
