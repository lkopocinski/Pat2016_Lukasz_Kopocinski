package pl.kopocinski.lukasz.lukaszkopocinski.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // switch

      //  if (id == R.id.action_logout) {
       //     return true;
       // }

        return super.onOptionsItemSelected(item);
    }
}
