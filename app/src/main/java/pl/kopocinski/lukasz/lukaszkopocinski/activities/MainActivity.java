package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.fragments.LoginFragment;
import pl.kopocinski.lukasz.lukaszkopocinski.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    private static final String IS_USER_LOGGED_IN_KEY = "isUserLogged";
    public static final boolean USER_LOGGED_OUT = false;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            if (isUserLoggedIn()) {
                loadMainFragment();
            } else {
                loadLoginFragment();
            }
        }
    }

    public boolean isUserLoggedIn() {
        return getIntent().getBooleanExtra(IS_USER_LOGGED_IN_KEY, USER_LOGGED_OUT);
    }

    public void loadMainFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                MainFragment.newInstance()).commit();
    }

    public void loadLoginFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                LoginFragment.newInstance()).commit();
    }

}
