package redteam.usuevents.view.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.adapter.HomeEventsAdapter;
import redteam.usuevents.adapter.ManageSubscriptionsAdapater;
import redteam.usuevents.model.Event;
import redteam.usuevents.model.Topic;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainSubscriptionsFragment extends Fragment implements ManageSubscriptionsCallback {

    private View mView;

    private RecyclerView mRecyclerView;


    public static MainSubscriptionsFragment getInstance() {
        return new MainSubscriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView==null){
            mView = inflater.inflate(R.layout.fragment_main_subscriptions, container, false);
        }

        if(mRecyclerView!=null)
            return mView;

        bindViews();

        //RecyclerView test code, remove once finalized
        GridLayoutManager manager;
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            manager = new GridLayoutManager(getContext(), 3);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position > 0)
                        return 1;
                    return 3;
                }
            });
        }else{
            manager = new GridLayoutManager(getContext(), 2);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position > 0)
                        return 1;
                    return 2;
                }
            });
        }
        mRecyclerView.setLayoutManager(manager);
        List<Event> eventList = new ArrayList<Event>();
        for(int i = 10; i<25; i++){
            Event event = new Event();
            event.setBeginDateTime("2017-12-"+i+"T18:00:00-07:00");
            event.setCategory("Aggie Football");
            event.setDescription("Come watch your Aggies crush New Mexico.");
            event.setEndDateTime("2017-12-"+i+"T21:00:00-07:00");
            event.setEventId(i + "");
            event.setImageUri("http://global.web.usu.edu/images/uploads/Carlos/Utah%20State%20University-%20early%20summer.jpg");
            event.setLatitude(41.750996996);
            event.setLocation("Maverik Stadium, Logan, UT");
            event.setLongitude(-111.806996772);
            event.setNumberInterested(i);
            event.setTopic("mFootball");
            event.setTitle("USU vs New Mexico");
            eventList.add(event);
        }
        HomeEventsAdapter eventAdapter = new HomeEventsAdapter(eventList);
        eventAdapter.setSubscriptions(true);
        eventAdapter.setManageSubscriptionsCallback(this);
        mRecyclerView.setAdapter(eventAdapter);
        //Remove all above between comments when done figuring out configuration

        return mView;
    }

    private void bindViews(){
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.fragment_main_subscriptions_recycler_view);
    }

    @Override
    public void updateManageViews() {
        List<Topic> topicList = new ArrayList<>();
        ManageSubscriptionsAdapater manageSubscriptionsAdapater = new ManageSubscriptionsAdapater(topicList);
        manageSubscriptionsAdapater.setManageSubscriptionsCallback(this);
        mRecyclerView.setAdapter(manageSubscriptionsAdapater);
        //mRecyclerView.notifyAll();
    }

    @Override
    public void saveManageState() {

    }
}
