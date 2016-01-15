package pl.kopocinski.lukasz.lukaszkopocinski;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class Utils {

    public static void fragmentTransactionSetup(Fragment fragment,
                                                FragmentManager fragmentManager,
                                                String tag) {

        fragmentManager.executePendingTransactions();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, tag);

        //fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        return isConnected;
    }
}
