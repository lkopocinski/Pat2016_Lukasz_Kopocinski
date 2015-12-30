package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.kopocinski.lukasz.lukaszkopocinski.R;

/**
 * Created by Łukasz on 2015-12-28.
 */
public class SplashScreen extends AppCompatActivity {
    public static final int SPLASH_DURATION_TIME = 5000;

    private Thread timerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        timerThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_DURATION_TIME);

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        timerThread.start();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(!timerThread.isInterrupted()){
            timerThread.interrupt();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
