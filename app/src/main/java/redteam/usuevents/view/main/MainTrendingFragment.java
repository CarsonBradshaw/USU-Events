package redteam.usuevents.view.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.adapter.TrendingEventsAdapter;
import redteam.usuevents.model.Event;

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

        bindViews();

        //RecyclerView test code, remove once finalized
        StaggeredGridLayoutManager manager;
        if((getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && getActivity().getResources().getConfiguration().screenWidthDp >= 600)
                || getActivity().getResources().getConfiguration().smallestScreenWidthDp >= 600) {
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }else{
            manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(manager);
        List<Event> eventList = new ArrayList<Event>();
        for(int i = 0; i<25; i++){
            Event event = new Event();
            event.setBeginDateTime("Friday, Aug "+i+" 6:00-7:30 PM");
            event.setTitle("Aggie Football: USU vs New Mexico");
            event.setLocation("Maverik Stadium, Logan, UT");
            event.setNumberInterested(i * 10);
            eventList.add(event);
        }
        TrendingEventsAdapter eventAdapter = new TrendingEventsAdapter(eventList);
        eventAdapter.setTrending(true);
        mRecyclerView.setAdapter(eventAdapter);
        //Remove all above between comments when done figuring out configuration

        return sView;
    }

    private void bindViews(){
        mRecyclerView = (RecyclerView)sView.findViewById(R.id.fragment_main_trending_recycler_view);
    }

}
