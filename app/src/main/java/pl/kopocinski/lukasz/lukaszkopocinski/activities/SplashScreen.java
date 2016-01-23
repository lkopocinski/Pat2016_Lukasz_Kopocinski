package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.utils.UserPreferences;

public class SplashScreen extends AppCompatActivity {
    private static final String CLASS_NAME = SplashScreen.class.getSimpleName();
    private static final long SPLASH_DURATION_TIME = 5000;
    private static final long WAIT_TIME_TO_SECOND_CLICK = 2000;

    public static final boolean USER_LOGGED_IN = true;
    public static final boolean USER_LOGGED_OUT = false;

    private Handler handler;
    private Runnable runnable;
    private boolean backPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, SPLASH_DURATION_TIME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUserLoggedIn()) {
            MainActivity.start(SplashScreen.this, USER_LOGGED_IN);
            finish();
        } else {
            setContentView(R.layout.splash_screen);

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    MainActivity.start(SplashScreen.this, USER_LOGGED_OUT);
                }
            };
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
            return;
        }

        backPressedOnce = true;
        handler.removeCallbacks(runnable);
        Toast.makeText(this, R.string.click_twice_to_exit, Toast.LENGTH_SHORT).show();

        waitForSecondClick();
    }

    private void waitForSecondClick(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, WAIT_TIME_TO_SECOND_CLICK);
    }

    private boolean isUserLoggedIn() {
        return UserPreferences.getInstance(getApplicationContext()).isUserLoggedIn();
    }
}
