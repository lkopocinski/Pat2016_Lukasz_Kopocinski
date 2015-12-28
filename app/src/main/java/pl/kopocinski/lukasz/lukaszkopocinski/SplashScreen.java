package pl.kopocinski.lukasz.lukaszkopocinski;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Łukasz on 2015-12-28.
 */
public class SplashScreen extends Activity {
    public static final int DURATION_TIME = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(DURATION_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
