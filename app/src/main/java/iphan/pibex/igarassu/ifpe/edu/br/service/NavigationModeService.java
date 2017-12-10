package iphan.pibex.igarassu.ifpe.edu.br.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import iphan.pibex.igarassu.ifpe.edu.br.R;
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
        private double latitude;
        private double longitude;
        private Location location;

        public Task(Context context, int startId) {
            this.startId = startId;
            this.context = context;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
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
        public void onConnectionSuspended(int i) {
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                setLatitude(location.getLatitude());
                setLongitude(location.getLongitude());
            }
        }

        //metodo de notificação
        public void notificationMessage(String message){

            //nova notificação
            android.support.v7.app.NotificationCompat.Builder builder = new
                    android.support.v7.app.NotificationCompat.Builder(NavigationModeService.this);

            //versão maior igual ao lollipop foto transparente, else menor igual foto normal
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.mipmap.ic_launcher);
            } else {
                builder.setSmallIcon(R.mipmap.ic_launcher);
            }

            builder.setContentTitle("Aviso");
            builder.setContentText(message);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setTicker("Aviso");

            NotificationManager notification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notific = builder.build();
            //notificação em vibração
            notific.vibrate = new long[]{150, 300, 150, 600};

            try {

                //ringtone para notificação
                Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone toque = RingtoneManager.getRingtone(NavigationModeService.this, som);
                toque.play();

            }catch (Exception e){}

            //auto cancelar notificaçãoes
            notific.flags = Notification.FLAG_AUTO_CANCEL;
            //set notification
            notification.notify(R.mipmap.ic_launcher, notific);


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

                                if (GeolocationUtil.getDistance(getLatitude(), getLongitude(),
                                        list.get(i).getLatitude(), list.get(i).getLongitude()) <= Constants.MINIMUM_DISTANCE) {
                                        name = list.get(i).getName();
                                    //Log.e("PARAMS", "PARAMS: "+ name);
                                }
                            }

                            return name;

                        }

                        @Override
                        protected void onPostExecute(String params) {
                            super.onPostExecute(params);

                            if(!params.trim().isEmpty()) {
                                Log.e("PARAMS", "PARAMS: "+ params);
                                notificationMessage("Você está perto do ponto " + params);
                            }

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
