package redteam.usuevents;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import redteam.usuevents.models.EventModel;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    private int eventId;
    private TextView eventName;
    private TextView eventDescription;
    private TextView eventDate;
    private EventModel event;
    private String lat;
    private String lng;
    private String title;
    private String voteCtS;
    private int voteCtI;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        setUpUIViews();

        Intent intent=getIntent();
        event=(EventModel)intent.getSerializableExtra("EventModel");
        eventName.setText(event.getTitle());
        eventDescription.setText(event.getDescription());


        eventDate.setText("Event Starts "+event.getStartDateMonthDayYearFormat()+" at "+event.getStartTime12Hr());

        lat = event.getLat();
        lng = event.getLng();
        title = event.getTitle();
        voteCtS=event.getVoteCt();
        voteCtI=Integer.parseInt(voteCtS);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference();




        final Button interestButton = (Button) findViewById(R.id.interestButton_eventDetail);
        interestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                Toast.makeText(getApplicationContext(),
                        event.getEvent_id(), Toast.LENGTH_LONG).show();
                /*
                   Currently only sets the event voteCt of event 8 to a value of 1. Event Id isn't
                   being set in the event model.
 */
                try {

                    myRef.child("events").child(event.getEvent_id()).child("voteCt").setValue(Integer.toString(voteCtI+1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final Button subscribeButton = (Button) findViewById(R.id.subscribeButton_eventDetail);
        subscribeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){

            }
        });
        final Button reportButton = (Button) findViewById(R.id.reportButton_eventDetail);
        reportButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){

            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEventDetail);
        mapFragment.getMapAsync(this);

    }
    private void setUpUIViews(){
        eventName=(TextView)findViewById(R.id.eventName);
        eventDescription=(TextView)findViewById(R.id.eventDescription);
        eventDate=(TextView)findViewById(R.id.eventDate);
        eventDescription.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng event = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        googleMap.addMarker(new MarkerOptions().position(event)
                .title(title)).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(event,13));
    }

    @Override
    public void onBackPressed()
    {
        Bundle extras = getIntent().getExtras();

        boolean launchedFromNotif = false;

        if (extras.containsKey("EXTRA_LAUNCHED_BY_NOTIFICATION"))
        {
            launchedFromNotif = extras.getBoolean("EXTRA_LAUNCHED_BY_NOTIFICATION");
        }

        if (launchedFromNotif)
        {
            // Launched from notification, handle as special case
            Intent intent = new Intent(this, HomeLandingPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
