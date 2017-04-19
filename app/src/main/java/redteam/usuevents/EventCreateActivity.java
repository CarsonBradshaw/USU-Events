package redteam.usuevents;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import redteam.usuevents.models.EventModel;

/**
 * Created by DaShare on 3/2/17.
 */

public class EventCreateActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback{

    private EventModel event;
   // private String lat;
   // private String lng;

    EditText title, description, lat, lng;
    Button insert, date_time, topic;
    TextView dateView, startTime, topicSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    RequestQueue requestQueue;
    String insertURL = "http://144.39.212.67/db_create.php";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Intent intent=getIntent();
        event=(EventModel)intent.getSerializableExtra("EventModel");

        //lat = event.getLat();
       // lng = event.getLng();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);

        topic = (Button) findViewById(R.id.topic);
        topicSelected = (TextView) findViewById(R.id.topicSelected);
        listItems = getResources().getStringArray(R.array.category_item);
        checkedItems = new boolean[listItems.length];

        date_time = (Button) findViewById(R.id.date_time);
        insert = (Button) findViewById(R.id.insert);

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


        requestQueue = Volley.newRequestQueue(getApplicationContext());


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
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
                requestQueue.add(request);

                Intent postCreation = new Intent(EventCreateActivity.this, HomeLandingPage.class);
                startActivity(postCreation);
            }
        });


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEventDetail);
        mapFragment.getMapAsync(this);
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
        date_time.setText(yearFinal + ":" + monthFinal + ":" + dayFinal
                + " " + hourFinal + ":" + minuteFinal + ":00");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  LatLng event = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
      //  googleMap.addMarker(new MarkerOptions().position(event)).showInfoWindow();
      //  googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(event,13));
    }

}
