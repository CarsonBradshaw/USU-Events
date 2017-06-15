package redteam.usuevents.view.main;

import android.support.v4.app.Fragment;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainTrendingFragment extends Fragment {

    private static final MainTrendingFragment sInstance = new MainTrendingFragment();

    public static MainTrendingFragment getInstance() {
        return sInstance;
    }

    private MainTrendingFragment() {
    }
}
