package redteam.usuevents.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import redteam.usuevents.R;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainTrendingFragment extends Fragment {

    private static View sView;

    private RecyclerView mRecyclerView;


    public static MainTrendingFragment getInstance() {
        return new MainTrendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_trending, container, false);
        }

        bindViews(container);
        
        return sView;
    }

    private void bindViews(ViewGroup container){
        mRecyclerView = (RecyclerView)container.findViewById(R.id.fragment_main_home_recycler_view);
    }

}
