package iphan.pibex.igarassu.ifpe.edu.br;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class SplashActivity extends FragmentActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(this, 1500);

    }

    public void run(){
        Intent intent = new Intent(SplashActivity.this, MapaActivity.class);
        startActivity(intent);
        finish();
    }

}