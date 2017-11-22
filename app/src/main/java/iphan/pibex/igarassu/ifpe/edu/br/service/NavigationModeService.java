package iphan.pibex.igarassu.ifpe.edu.br.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import iphan.pibex.igarassu.ifpe.edu.br.model.LocationModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;
import iphan.pibex.igarassu.ifpe.edu.br.util.GeolocationUtil;


public class NavigationModeService extends Service {

    private Context context;
    public List<Task> tasks = new ArrayList<Task>();
    public static final String WAKELOCK_NAME = "locationActivityServiceWakeLock";
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


    public class LocationGPS implements LocationListener {

        private Task task;

        public LocationGPS(Task task) {
            this.task = task;
        }

        @Override
        public void onLocationChanged(Location location) {
            this.task.setLongitude(location.getLatitude());
            this.task.setLatitude(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }


    private class Task extends Thread {

        private boolean active = true;
        private int startId;
        private Context context;
        private double longitude;
        private double latitude;
        private Task task;
        private LocationManager locationManager;
        private LocationListener locationListener;

        public Task(Context context, int startId) {
            this.startId = startId;
            this.context = context;
            this.task = this;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @SuppressLint("MissingPermission")
        private String checkingMetersBetweenTwoPoints() {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    locationListener = new LocationGPS(task);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            });

            List<LocationModel> list;
            DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
            list = dataBaseUtil.getLocationNavigationMode();


            String name = " ";
            for (int i = 0; i < list.size(); i++) {

                if (GeolocationUtil.getDistance(getLatitude(), getLongitude(), list.get(i).getLatitude(), list.get(i).getLongitude(), 'K') <= 5000) {
                    name = list.get(i).getName();
                }
            }


            return name;
        }

        public void run() {

            while (active) {

                try {

//                    Toast.makeText(getApplicationContext(), "Eu Estou perto de "+checkingMetersBetweenTwoPoints(), Toast.LENGTH_LONG).show();
                    Log.d("ESTOU PERTO DE", " "+checkingMetersBetweenTwoPoints());
                    TimeUnit.MINUTES.sleep(1);
                    locationManager.removeUpdates(locationListener);
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
        Toast.makeText(getApplicationContext(), "Modo Navegação Encerrado", Toast.LENGTH_LONG).show();
    }


}
