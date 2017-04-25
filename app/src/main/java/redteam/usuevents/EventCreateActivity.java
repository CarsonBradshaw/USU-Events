package redteam.usuevents;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Context;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import redteam.usuevents.models.EventModel;

/**
 * Created by DaShare on 3/2/17.
 */

public class EventCreateActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private EventModel event;

    EditText eventTitle, eventDescription;
    Button insert, date_time, topic, getPlaceButton;
    TextView topicSelected;
    TextView placeNameText;
    TextView dateTimeText;
    String[] listItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    boolean[] checkedItems;
    private static final LatLngBounds BOUNDS_UTAH_STATE = new LatLngBounds(
            new LatLng(41.7438120, -111.8110915), new LatLng(41.7465100, -111.8083935));
    int PLACE_PICKER_REQUEST = 1;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    boolean notified;
    String voteCt, lat, lng;

    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;

    //RequestQueue requestQueue;
   // String insertURL = "http://144.39.212.67/db_create.php";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Intent intent=getIntent();
        event=(EventModel)intent.getSerializableExtra("EventModel");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        eventTitle = (EditText) findViewById(R.id.title);
        eventDescription = (EditText) findViewById(R.id.description);
        notified = false;
        voteCt = "1";

        topic = (Button) findViewById(R.id.topic);
        topicSelected = (TextView) findViewById(R.id.topicSelected);
        listItems = getResources().getStringArray(R.array.category_item);
        checkedItems = new boolean[listItems.length];

        placeNameText = (TextView) findViewById(R.id.tvPlaceName);
        getPlaceButton = (Button) findViewById(R.id.btGetPalce);

        date_time = (Button) findViewById(R.id.date_time);
        dateTimeText = (TextView) findViewById(R.id.dateTimeText);
        insert = (Button) findViewById(R.id.insert);

        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");

        mAuth = FirebaseAuth.getInstance();

        //dateView = (TextView) findViewById(R.id.dateView);

        date_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventCreateActivity.this, EventCreateActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

       topic.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(EventCreateActivity.this);
               mBuilder.setTitle("Must select at least one topic");
               mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                            else{
                                mUserItems.remove(position);
                            }
                        }
                   }
               });
               mBuilder.setCancelable(false);
               mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String item = "";
                       for(int i = 0; i < mUserItems.size(); i++){
                           item = item + listItems[mUserItems.get(i)];
                           if(i != mUserItems.size() - 1){
                               item = item + ", ";
                           }
                       }
                       topicSelected.setText(item);
                   }
               });
               mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                   }
               });
               mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       for(int i = 0; i < checkedItems.length; i++){
                           checkedItems[i] = false;
                           mUserItems.clear();
                           topicSelected.setText("");
                       }
                   }
               });
               AlertDialog mDialog = mBuilder.create();
               mDialog.show();
           }
       });

        getPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                builder.setLatLngBounds(BOUNDS_UTAH_STATE);
                Intent intent = new Intent(EventCreateActivity.this, PlacePicker.class);
                try {
                    startActivityForResult(builder.build(EventCreateActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


       //requestQueue = Volley.newRequestQueue(getApplicationContext());

       insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();

             /*   StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("topic", topic.getText().toString());
                        parameters.put("title", title.getText().toString());
                        parameters.put("description", description.getText().toString());
                        parameters.put("date_time", date_time.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);*/


            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("%s", place.getAddress());
                LatLng latLng = place.getLatLng();
                lat = String.valueOf(latLng.latitude);
                lng = String.valueOf(latLng.longitude);
                //Allows the lat/lng string to become public by submitting it to placeNameText, that way it can be pushed to the DB
                placeNameText.setText(lat);
                placeNameText.setText(lng);
                placeNameText.setText(address);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(EventCreateActivity.this, EventCreateActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;

        //Needed to set the formating to match that of the database
        dateTimeText.setText(yearFinal + "-" + monthFinal + "-" + dayFinal
                + " " + hourFinal + ":" + minuteFinal + ":00");

    }


    private void addEvent(){
        mProgress.setMessage("Submitting Event...");

        final String theTitle = eventTitle.getText().toString().trim();
        final String theDescription = eventDescription.getText().toString().trim();
        final String theTopic = "userSubmitted";
        final String theLat = lat;
        final String theLng = lng;
        final String theStartDateTime = date_time.getText().toString().trim();
        final Boolean theNotified = notified;
        final String theVoteCt = voteCt;

        if(!TextUtils.isEmpty(theTitle) && !TextUtils.isEmpty(theDescription) && !TextUtils.isEmpty(theTopic) && !TextUtils.isEmpty(theLat) && !TextUtils.isEmpty(theLng) && !TextUtils.isEmpty(theStartDateTime)){
            mProgress.show();

            DatabaseReference submitEvent = mDatabase.push();

            submitEvent.child("description").setValue(theDescription);
            submitEvent.child("lat").setValue(theLat);
            submitEvent.child("lng").setValue(theLng);
            submitEvent.child("notified").setValue(theNotified);
            submitEvent.child("startDateTime").setValue(theStartDateTime);
            submitEvent.child("topic").setValue(theTopic);
            submitEvent.child("title").setValue(theTitle);
            submitEvent.child("voteCt").setValue(theVoteCt);

            mProgress.dismiss();

            //Made a toast because event would be posted too quick to display a progress dialog
            Toast.makeText(this,"Event was submited!", Toast.LENGTH_LONG).show();

            Intent postCreation = new Intent(EventCreateActivity.this, HomeLandingPage.class);
            startActivity(postCreation);

        }
        else{
            Toast.makeText(this, "Please fill out every field", Toast.LENGTH_LONG).show();
        }
    }

}
