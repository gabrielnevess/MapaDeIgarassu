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
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import iphan.pibex.igarassu.ifpe.edu.br.model.NavigationModeModel;
import iphan.pibex.igarassu.ifpe.edu.br.util.DataBaseUtil;




public class NavigationModeService extends Service {

    private Context context;
    public List<Task> tasks = new ArrayList<Task>();
    public NavigationModeService(Context context) { this.context = context; }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() { super.onCreate(); }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Task task = new Task(this, startId);
        task.execute();
        Toast.makeText(getApplicationContext(), "Iniciando Modo Navegação", Toast.LENGTH_LONG).show();
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


    private class Task extends AsyncTask<Void, Void, String> {

        private boolean active = true;
        private int startId;
        private Context context;
        private double longitude;
        private double latitude;
        private LocationManager locationManager;
        private LocationListener locationListener;

        public Task(Context context, int startId) {
            this.startId = startId;
            this.context = context;
        }

        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }

        private double radian(double rad) {
            return rad * Math.PI / 180;
        }

        private double MetersBetweenTwoPoints(double lat1, double lng1, double lat2, double lng2) {
            double r = 6378137;
            double dLat = radian(lat2 - lat1);
            double dLong = radian(lng2 - lng1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat) + Math.cos(radian(lat1)) * Math.cos(radian(lat2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double d = r * c;
            return d;
        }


        @SuppressLint("MissingPermission")
        private double checkingMetersBetweenTwoPoints() {

            List<NavigationModeModel> list;
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            DataBaseUtil dataBaseUtil = new DataBaseUtil(context);
            list = dataBaseUtil.getLocationNavigationMode();

            locationListener = new LocationGPS(this);
            locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);


            for (int i = 0; i < list.size(); i++) {

                if (MetersBetweenTwoPoints(getLatitude(), getLongitude(), list.get(i).getLatitude(), list.get(i).getLongitude()) == 0) {

                }
            }


            return 0;
        }


        @Override
        protected String doInBackground(Void... params) {

            while (active) {

                try {

                    TimeUnit.MINUTES.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String params) {
            super.onPostExecute(params);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0, tam = tasks.size(); i < tam; i++) {
            tasks.get(i).active = false;
        }
        Toast.makeText(getApplicationContext(), "Encerrando Modo Navegação", Toast.LENGTH_LONG).show();
    }

}
