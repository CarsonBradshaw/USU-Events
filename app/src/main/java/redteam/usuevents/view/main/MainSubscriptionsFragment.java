package redteam.usuevents.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redteam.usuevents.R;
import redteam.usuevents.adapter.HomeEventsAdapter;
import redteam.usuevents.adapter.ManageSubscriptionsAdapater;
import redteam.usuevents.model.Event;
import redteam.usuevents.model.Topic;
import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.profile.ProfileActivity;

/**
 * Created by Admin on 6/14/2017.
 */

public class MainSubscriptionsFragment extends Fragment implements ManageSubscriptionsCallback {

    private View mView;

    private RecyclerView mRecyclerView;
    private List<Topic> topicList;
    private Set<Topic> unsavedTopicChanges;
    private boolean hasUnsavedChanges;
    private UnsavedChangesCallback unsavedChangesCallback;


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
        if(topicList == null) {
            topicList = new ArrayList<>();
            unsavedTopicChanges = new HashSet<>();
            Topic t1 = new Topic();
            t1.setTopic("Men's Basketball");
            t1.setNumActiveEvents(12);
            t1.setNumSubscribers(2264);
            Topic t2 = new Topic();
            t2.setTopic("USUSA");
            t2.setNumActiveEvents(22);
            t2.setNumSubscribers(3578);
            Topic t3 = new Topic();
            t3.setTopic("Men's Hockey");
            t3.setNumSubscribers(240);
            t3.setNumActiveEvents(10);
            Topic t4 = new Topic();
            t4.setTopic("Men's Football");
            t4.setNumActiveEvents(1);
            t4.setNumSubscribers(5442);
            Topic t5 = new Topic();
            t5.setTopic("Hunstman School of Business");
            t5.setNumSubscribers(2310);
            t5.setNumActiveEvents(5);

            topicList.add(t1);
            topicList.add(t2);
            topicList.add(t3);
            topicList.add(t4);
            topicList.add(t5);
        }
        ManageSubscriptionsAdapater manageSubscriptionsAdapater = new ManageSubscriptionsAdapater(topicList, unsavedTopicChanges);
        manageSubscriptionsAdapater.setManageSubscriptionsCallback(this);
        mRecyclerView.setAdapter(manageSubscriptionsAdapater);
    }

    @Override
    public void saveManageState() {
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
        updateUnsavedChangeState(false);
        unsavedTopicChanges.clear();
    }

    @Override
    public void updateUnsavedChangeState(boolean hasChanged) {
        hasUnsavedChanges = hasChanged;
        unsavedChangesCallback.onUnsavedChange(hasUnsavedChanges);
    }

    public void discardChanges(){
        for(Topic topic : unsavedTopicChanges){
            topic.setSubscribed(!topic.isSubscribed());
        }
        saveManageState();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setUnsavedChangesCallbackListener(UnsavedChangesCallback unsavedChangesCallback) {
        this.unsavedChangesCallback = unsavedChangesCallback;
    }
}
