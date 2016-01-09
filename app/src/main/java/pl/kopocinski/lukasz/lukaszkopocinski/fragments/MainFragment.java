package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.UserPreferences;
import pl.kopocinski.lukasz.lukaszkopocinski.Utils;
import pl.kopocinski.lukasz.lukaszkopocinski.http.HttpAsync;
import pl.kopocinski.lukasz.lukaszkopocinski.http.onHttpResponse;
import pl.kopocinski.lukasz.lukaszkopocinski.json.JsonSerializer;
import pl.kopocinski.lukasz.lukaszkopocinski.json.models.JsonServerArray;
import pl.kopocinski.lukasz.lukaszkopocinski.recycler.ListAdapter;

public class MainFragment extends Fragment implements onHttpResponse {
    final static String BASE_SERVER_URL = "http://192.168.1.137:8080";
    final static String JSON_PAGE_0 = "/page_0.json";

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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        ButterKnife.bind(this, view);

        if (!Utils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            return view;
        }

        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        setUpRecyclerView(view);
        initRecyclerViewData();
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_fragment_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initRecyclerViewData() {
        downloadData();
    }

    private void downloadData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        HttpAsync httpAsync = new HttpAsync();
        httpAsync.download(BASE_SERVER_URL + JSON_PAGE_0, this);
    }

    @Override
    public void onHttpResponseSuccess(String response) {
        JsonSerializer jsonSerializer = new JsonSerializer();
        JsonServerArray jsonServerArray = jsonSerializer.deserialize(response);

        if (jsonServerArray == null) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            Toast.makeText(getActivity(), R.string.json_parse_error, Toast.LENGTH_LONG).show();
            return;
        }

        mAdapter = new ListAdapter(jsonServerArray.getArray(), getContext());
        mRecyclerView.setAdapter(mAdapter);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void onHttpResponseError(String errorMessage) {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_logout) {
            saveUserLoggedOut();
            loadLoginFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveUserLoggedOut() {
        UserPreferences.getInstance(getContext()).saveLoginStatus(UserPreferences.USER_LOGGED_OUT);
    }

    private void loadLoginFragment() {
        Utils.fragmentTransactionSetup(LoginFragment.newInstance(), getFragmentManager(), LoginFragment.class.getName());
    }
}
