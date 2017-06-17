package redteam.usuevents.view.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import redteam.usuevents.R;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainTrendingFragment extends Fragment {

    private static View sView;

    public static MainTrendingFragment getInstance() {
        return new MainTrendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_trending, container, false);
        }
        return sView;
    }

}
