package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.UserPreferences;
import pl.kopocinski.lukasz.lukaszkopocinski.Utils;


public class MainFragment extends Fragment {

    @Bind(R.id.button_logout)
    Button buttonLogout;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();

        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_logout)
    public void onButtonLogoutClicked() {
        saveUserLoggedOut();
        loadLoginFragment();
    }

    private void saveUserLoggedOut() {
        UserPreferences.getInstance(getContext()).saveLoginStatus(UserPreferences.USER_LOGGED_OUT);
    }

    private void loadLoginFragment() {
        Utils.fragmentTransactionSetup(LoginFragment.newInstance(), getFragmentManager(), LoginFragment.class.getName());
    }
}
