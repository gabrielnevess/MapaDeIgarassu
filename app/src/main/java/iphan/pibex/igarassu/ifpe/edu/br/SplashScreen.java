package iphan.pibex.igarassu.ifpe.edu.br;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class SplashScreen extends Activity {

    private ProgressBar mProgress;
    private ImageView splashScreen;

    protected void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(iphan.pibex.igarassu.ifpe.edu.br.SplashScreen.this, IntroActivity.class);
                    startActivity(intent);

                }
            }

        };
        timer.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


