package redteam.usuevents.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.adapter.EventsAdapter;
import redteam.usuevents.model.Event;

/**
 * Created by Admin on 6/12/2017.
 */

public class MainHomeFragment extends Fragment {

    private static View sView;

    private RecyclerView mRecyclerView;


    public static MainHomeFragment getInstance() {
        return new MainHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_home, container, false);
        }

        bindViews();

        //RecyclerView test code, remove once finalized
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Event> eventList = new ArrayList<Event>();
        for(int i = 0; i<10; i++){
            Event event = new Event();
            event.setBeginDateTime("Starts at " + i);
            event.setTitle("Event title is " + i);
            event.setLocation("Event Is Happening @ " + i);
            event.setNumberInterested(i);
            eventList.add(event);
        }
        EventsAdapter eventAdapter = new EventsAdapter(eventList);
        mRecyclerView.setAdapter(eventAdapter);
        //Remove all above between comments when done figuring out configuration


        return sView;
    }

    private void bindViews(){
        mRecyclerView = (RecyclerView)sView.findViewById(R.id.fragment_main_home_recycler_view);
    }

}
