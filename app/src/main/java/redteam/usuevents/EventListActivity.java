package redteam.usuevents;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import redteam.usuevents.models.EventModel;

public class EventListActivity extends AppCompatActivity {

    private ListView eventListView;
    //Toolbar code
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //Toolbar code end

    //Swipe (pull down) to Refresh feature
    SwipeRefreshLayout mSwipeRefreshLayout;

    //Coordinator Create Event Fab Button
    FloatingActionButton fab;

    public List<EventModel> eventModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //Toolbar Code
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_event_list);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //Toolbar Code End

        eventListView=(ListView)findViewById(R.id.eventListView);

        //Swipe action will cause function to make call to database and reload
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                getFirebaseData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //Once Fab Coordinator Button is clicked navigates user to Create Event Activity
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(EventListActivity.this,EventCreateActivity.class));
            }
        });

        getFirebaseData();

    }

    public void createListView(){
        EventAdapter adapter=new EventAdapter(getApplicationContext(),R.layout.event_layout,eventModelList);
        eventListView.setAdapter(adapter);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                EventModel eventModel=eventModelList.get(position);
                Intent intent=new Intent(EventListActivity.this,EventDetailActivity.class);
                intent.putExtra("EventModel",eventModel);
                startActivity(intent);
            }
        });
    }

    public void getFirebaseData(){
        eventModelList.clear();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Child info: ", "parent" + dataSnapshot.toString());

                for(DataSnapshot child: dataSnapshot.getChildren()){
                    EventModel model = child.getValue(EventModel.class);
                    Log.d("EventListSize: ", model.getDescription());
                    eventModelList.add(model);
                    Log.d("EventListSize: ", Integer.toString(eventModelList.size()));
                    if(eventModelList.size() == dataSnapshot.getChildrenCount()){
                        createListView();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Toolbar Code
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }




    public class EventAdapter extends ArrayAdapter<EventModel> {

        private List<EventModel> eventModelList;
        private int resource;
        private LayoutInflater inflater;
        public EventAdapter(Context context, int resource, List<EventModel> objects) {
            super(context, resource, objects);
            eventModelList=objects;
            this.resource=resource;
            inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent){
           if(convertView==null){
               convertView=inflater.inflate(R.layout.event_layout,null);
           }
            TextView eventNameRow;
            TextView eventDetailRow;

            eventNameRow=(TextView)convertView.findViewById(R.id.eventNameRow);
            eventDetailRow=(TextView)convertView.findViewById(R.id.eventDetailRow);

            eventNameRow.setText(eventModelList.get(position).getTitle());
            eventDetailRow.setText(eventModelList.get(position).getDescription());

            return convertView;
        }

    }


}
