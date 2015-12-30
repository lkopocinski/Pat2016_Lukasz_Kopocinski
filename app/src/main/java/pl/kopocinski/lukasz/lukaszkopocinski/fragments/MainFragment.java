package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.kopocinski.lukasz.lukaszkopocinski.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();

        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
