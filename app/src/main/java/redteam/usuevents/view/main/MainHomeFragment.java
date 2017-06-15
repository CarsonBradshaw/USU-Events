package redteam.usuevents.view.main;

import android.support.v4.app.Fragment;

/**
 * Created by Admin on 6/12/2017.
 */

public class MainHomeFragment extends Fragment {

    private static final MainHomeFragment sInstance = new MainHomeFragment();

    public static MainHomeFragment getInstance() {
        return sInstance;
    }

    private MainHomeFragment() {
    }

}
