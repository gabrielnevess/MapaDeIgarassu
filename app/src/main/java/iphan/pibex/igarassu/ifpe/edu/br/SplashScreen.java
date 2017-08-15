package iphan.pibex.igarassu.ifpe.edu.br;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class SplashScreen extends Activity implements Runnable {

    private ProgressBar mProgress;
    private ImageView splashScreen;

    protected void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashScreen = (ImageView) findViewById(R.id.splash_screen);
        splashScreen.setImageResource(R.mipmap.splash_screen);

        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FF4C35"), PorterDuff.Mode.MULTIPLY);
        mProgress.postDelayed(this, 3000);

    }

    @Override
    public void run() {
        Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }
}

