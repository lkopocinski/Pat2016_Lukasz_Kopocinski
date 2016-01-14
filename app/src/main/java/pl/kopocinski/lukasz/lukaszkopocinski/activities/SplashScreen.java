package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import pl.kopocinski.lukasz.lukaszkopocinski.R;

public class SplashScreen extends AppCompatActivity {
    private static final String CLASS_NAME = SplashScreen.class.getSimpleName();
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

                    startMainActivity();
                } catch (InterruptedException e) {
                    Log.i(CLASS_NAME, "Thread interrupted");
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

    public void startMainActivity(){
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
