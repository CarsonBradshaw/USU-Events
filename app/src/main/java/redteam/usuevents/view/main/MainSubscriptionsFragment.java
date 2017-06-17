package redteam.usuevents.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import redteam.usuevents.R;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainSubscriptionsFragment extends Fragment {

    private static View sView;

    public static MainSubscriptionsFragment getInstance() {
        return new MainSubscriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_subscriptions, container, false);
        }
        return sView;
    }

}
