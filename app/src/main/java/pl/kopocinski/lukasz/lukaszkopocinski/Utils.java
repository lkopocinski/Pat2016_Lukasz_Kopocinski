package pl.kopocinski.lukasz.lukaszkopocinski;

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
}
