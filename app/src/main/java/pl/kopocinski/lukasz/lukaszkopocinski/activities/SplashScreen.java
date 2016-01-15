package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.UserPreferences;


public class SplashScreen extends AppCompatActivity {
    public static final int SPLASH_DURATION_TIME = 5000;

    private static final String IS_USER_LOGGED_IN_KEY = "isUserLogged";
    public static final boolean USER_LOGGED_IN = true;
    public static final boolean USER_LOGGED_OUT = false;

    private Thread timerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUserLoggedIn()) {
            startMainActivity(USER_LOGGED_IN);
        } else {
            setContentView(R.layout.splash_screen);

            timerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(SPLASH_DURATION_TIME);

                        startMainActivity(USER_LOGGED_OUT);
                    } catch (InterruptedException e) {
                        //return;
                    }
                }
            };
            timerThread.start();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (!timerThread.isInterrupted()) {
            timerThread.interrupt();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private boolean isUserLoggedIn() {
        return UserPreferences.getInstance(getApplicationContext()).isUserLoggedIn();
    }

    private void startMainActivity(boolean isLoggedIn) {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        intent.putExtra(IS_USER_LOGGED_IN_KEY, isLoggedIn);
        startActivity(intent);
    }
}
