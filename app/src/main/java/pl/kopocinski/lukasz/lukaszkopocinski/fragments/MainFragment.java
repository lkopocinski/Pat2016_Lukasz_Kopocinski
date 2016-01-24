package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.HttpURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.models.JsonServerArray;
import pl.kopocinski.lukasz.lukaszkopocinski.recycler.Adapter;
import pl.kopocinski.lukasz.lukaszkopocinski.recycler.EndlessRecyclerScrollListener;
import pl.kopocinski.lukasz.lukaszkopocinski.retrofit.ServerCall;
import pl.kopocinski.lukasz.lukaszkopocinski.utils.UserPreferences;
import pl.kopocinski.lukasz.lukaszkopocinski.utils.Utils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainFragment extends Fragment {
    private static final String CLASS_NAME = MainFragment.class.getSimpleName();

    final static String BASE_SERVER_URL = "http://192.168.1.137:8080";
    //final static String BASE_SERVER_URL = "http://10.0.2.2:8080";

    private final static int FIRST_PAGE = 0;
    private static boolean lastPage = false;

    /* Recycler View */
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    //private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mLayoutManager;

    /* Retrofit interface */
    private ServerCall serverCall;

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
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        setUpRecyclerView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!Utils.isNetworkAvailable(getContext())) {
            showNoInternetToast();
            return;
        }

        lastPage = false;
        initRetrofit();
        downloadData();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_SERVER_URL)
                .build();

        serverCall = retrofit.create(ServerCall.class);
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_fragment_recycler_view);

        gridView();
        //listView();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(mLayoutManager) {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                downloadNextData(page);
            }
        });
    }

    private void gridView() {
        if (isTablet()) {
            int COLUMN_QUANTITY_TABLET = 4;
            setUpGridLayout(COLUMN_QUANTITY_TABLET);
        } else {
            int COLUMN_QUANTITY_PHONE = 2;
            setUpGridLayout(COLUMN_QUANTITY_PHONE);
        }
    }

    private void listView() {
        //mLayoutManager = new LinearLayoutManager(getActivity());
    }

    private void setUpGridLayout(final int columnQuantity) {
        final int ONE_PLACE = 1;
        mLayoutManager = new GridLayoutManager(getActivity(), columnQuantity);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position >= mAdapter.getItemCount() - 1) ? columnQuantity : ONE_PLACE;
            }
        });
    }

    private void downloadData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<JsonServerArray> getServerArray = serverCall.getServerArray(FIRST_PAGE);
        getServerArray.enqueue(firstPageCall);
    }

    private void downloadNextData(int pageNumber) {
        if (!lastPage) {
            Call<JsonServerArray> getServerArray = serverCall.getServerArray(pageNumber);
            getServerArray.enqueue(nextPageCall);
        }
    }

    private Callback<JsonServerArray> firstPageCall = new Callback<JsonServerArray>() {
        @Override
        public void onResponse(Response<JsonServerArray> response, Retrofit retrofit) {
            mAdapter = new Adapter(response.body().getArray(), getContext());
            mRecyclerView.setAdapter(mAdapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(CLASS_NAME, t.getMessage());
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), R.string.json_parse_error, Toast.LENGTH_LONG).show();
        }
    };

    private Callback<JsonServerArray> nextPageCall = new Callback<JsonServerArray>() {
        @Override
        public void onResponse(Response<JsonServerArray> response, Retrofit retrofit) {
            final int currentSize = mAdapter.getItemCount();

            if (response.isSuccess()) {
                final JsonServerArray responseBody = response.body();
                mAdapter.addAll(responseBody);

                /* PostDelayed only for mock-up */
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyItemRangeInserted(currentSize, responseBody.getArray().size() - 1);
                    }
                }, 1000);
            }

            if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                lastPage = true;
                mAdapter.setNoMoreData(true);
                mAdapter.notifyItemRangeInserted(currentSize, currentSize);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(CLASS_NAME, t.getMessage());
        }
    };

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

    public void showNoInternetToast() {
        Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
