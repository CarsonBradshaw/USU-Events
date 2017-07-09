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
import redteam.usuevents.adapter.EventsAdapter;
import redteam.usuevents.adapter.HomeEventsAdapter;
import redteam.usuevents.model.Event;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainSubscriptionsFragment extends Fragment {

    private static View sView;

    private RecyclerView mRecyclerView;


    public static MainSubscriptionsFragment getInstance() {
        return new MainSubscriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_subscriptions, container, false);
        }

        bindViews();

        //RecyclerView test code, remove once finalized
        StaggeredGridLayoutManager manager;
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }else{
            manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(manager);
        List<Event> eventList = new ArrayList<Event>();
        for(int i = 10; i<25; i++){
            Event event = new Event();
            event.setBeginDateTime("Friday, Aug "+i+" 6:00-7:30 PM");
            event.setTitle("Aggie Football: USU vs New Mexico");
            event.setLocation("Maverik Stadium, Logan, UT");
            event.setImageUri("http://global.web.usu.edu/images/uploads/Carlos/Utah%20State%20University-%20early%20summer.jpg");
            event.setNumberInterested(i);
            eventList.add(event);
        }
        HomeEventsAdapter eventAdapter = new HomeEventsAdapter(eventList);
        mRecyclerView.setAdapter(eventAdapter);
        //Remove all above between comments when done figuring out configuration

        return sView;
    }

    private void bindViews(){
        mRecyclerView = (RecyclerView)sView.findViewById(R.id.fragment_main_subscriptions_recycler_view);
    }

}
