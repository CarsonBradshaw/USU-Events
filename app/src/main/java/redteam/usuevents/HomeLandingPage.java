package redteam.usuevents;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
import java.util.Set;

import redteam.usuevents.models.EventModel;

import static redteam.usuevents.R.drawable.gradient_color;

public class HomeLandingPage extends AppCompatActivity {
    private ListView subFeed;
    public List<EventModel> eventModelList = new ArrayList<>();

    //View Flipper
    ViewFlipper viewFlipper;
    Animation fadeIn, fadeOut;
    //View Flipper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_landing_page);
        subFeed =(ListView)findViewById(R.id.subFeed);


        //View Flipper
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewFlipper.setAutoStart(true);
        viewFlipper.setAnimation(fadeIn);
        viewFlipper.setAnimation(fadeOut);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
        //View Flipper


        final Button viewEventsButton = (Button) findViewById(R.id.viewEventHome);
        viewEventsButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Intent intent=new Intent(HomeLandingPage.this,EventListActivity.class);
                startActivity(intent);
            }
        });

        final Button settingsButton = (Button) findViewById(R.id.settingsButtonHome);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Intent intent=new Intent(HomeLandingPage.this,FirstTimeSubscriptionsActivity.class);
                startActivity(intent);
            }
        });

        final Button submitButton = (Button) findViewById(R.id.submitEventHome);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Intent intent=new Intent(HomeLandingPage.this,EventCreateActivity.class);
                startActivity(intent);
            }
        });

        getFirebaseData();

    }


    public void createListView(){
        EventAdapter adapter=new EventAdapter(getApplicationContext(),R.layout.event_layout,eventModelList);
        subFeed.setAdapter(adapter);
        subFeed.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                EventModel eventModel=eventModelList.get(position);
                Intent intent=new Intent(HomeLandingPage.this,EventDetailActivity.class);
                intent.putExtra("EventModel",eventModel);
                startActivity(intent);
            }
        });
    }

    public void getFirebaseData(){

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFileName),MODE_PRIVATE);
        final Set<String> result = sharedPreferences.getStringSet("notificationSubscriptions", null);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Child info: ", "parent" + dataSnapshot.toString());
                int i=0;
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    EventModel model = child.getValue(EventModel.class);
                    Log.d("EventListSize3: ", model.getDescription());
                    Log.d("EventListSize3: ", Integer.toString(eventModelList.size()));
                    if(result.contains(model.getTopic())){
                        eventModelList.add(model);
                    }
                    if(i == dataSnapshot.getChildrenCount()-1){
                        createListView();
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public class EventAdapter extends ArrayAdapter<EventModel> {

        private List<EventModel> eventModelList;
        private int resource;
        private LayoutInflater inflater;

        public EventAdapter(Context context, int resource, List<EventModel> objects) {
            super(context, resource, objects);
            eventModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.event_layout, null);
            }
            TextView eventNameRow;
            TextView eventDetailRow;

            eventNameRow = (TextView) convertView.findViewById(R.id.eventNameRow);
            eventDetailRow = (TextView) convertView.findViewById(R.id.eventDetailRow);
            eventNameRow.setTextColor(Color.parseColor("#FFFFFF"));
            eventDetailRow.setTextColor(Color.parseColor("#FFFFFF"));
            subFeed.setDivider(new ColorDrawable(Color.parseColor("#FFFFFF")));
            subFeed.setDividerHeight(1);
            eventNameRow.setText(eventModelList.get(position).getTitle());
            eventDetailRow.setText(eventModelList.get(position).getDescription());



            return convertView;
        }
    }
}