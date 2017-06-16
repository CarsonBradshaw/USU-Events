package redteam.usuevents.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import redteam.usuevents.R;

/**
 * Created by Admin on 6/12/2017.
 */

public class MainHomeFragment extends Fragment {

    private static final MainHomeFragment sInstance = new MainHomeFragment();

    public static MainHomeFragment getInstance() {
        return sInstance;
    }

    public MainHomeFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_home, container, false);
    }
}
