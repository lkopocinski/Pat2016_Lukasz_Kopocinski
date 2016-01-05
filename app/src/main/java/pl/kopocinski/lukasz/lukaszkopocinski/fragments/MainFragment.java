package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.kopocinski.lukasz.lukaszkopocinski.JsonServerList;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.gson.JsonSerializer;
import pl.kopocinski.lukasz.lukaszkopocinski.http.UrlDownloader;
import pl.kopocinski.lukasz.lukaszkopocinski.http.onHttpResponse;
import pl.kopocinski.lukasz.lukaszkopocinski.recycler.ListAdapter;

public class MainFragment extends Fragment implements onHttpResponse {
    final static String BASE_SERVER_URL = "http://192.168.1.137:8080";
    
    // Recycler View
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();

        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        setUpRecyclerView(view);
        initRecyclerViewData();
    }

    private void setUpRecyclerView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_fragment_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initRecyclerViewData() {
        downloadData();
    }

    private void downloadData(){
        UrlDownloader urlDownloader = new UrlDownloader();
        urlDownloader.download(BASE_SERVER_URL + "/page_0.json",this);
    }

    @Override
    public void onHttpResponseSuccess(String response) {
        JsonSerializer jsonSerializer  = new JsonSerializer();
        JsonServerList jsonServerList = jsonSerializer.deserialize(response);

        mAdapter = new ListAdapter(jsonServerList.getArray(), getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onHttpResponseError(String errorMessage) {
        Toast.makeText(getContext(),errorMessage,Toast.LENGTH_LONG);
    }

    /*
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
    */
}
