package redteam.usuevents;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import redteam.usuevents.models.EventModel;

/**
 * Created by DaShare on 3/2/17.
 */

public class EventCreateActivity extends Activity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText title, description, event_id, category_id, address, lat, lng, voteCt;
    Button insert, date_time;
    TextView dateView, startTime;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    RequestQueue requestQueue;
    String insertURL = "http://144.39.212.67/db_create.php";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        address = (EditText) findViewById(R.id.address);
        category_id = (EditText) findViewById(R.id.category_id);
        event_id = (EditText) findViewById(R.id.event_id);
        //date_time = (Button) findViewById(R.id.date_time);
        lat = (EditText) findViewById(R.id.lat);
        lng = (EditText) findViewById(R.id.lng);
        voteCt = (EditText) findViewById(R.id.voteCt);

        insert = (Button) findViewById(R.id.insert);
        date_time = (Button) findViewById(R.id.date_time);

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
                        parameters.put("category_id", category_id.getText().toString());
                        parameters.put("event_id", event_id.getText().toString());
                        parameters.put("title", title.getText().toString());
                        parameters.put("description", description.getText().toString());
                        parameters.put("date_time", date_time.getText().toString());
                        parameters.put("address", address.getText().toString());
                        parameters.put("lat", lat.getText().toString());
                        parameters.put("lng", lng.getText().toString());
                        parameters.put("voteCt", voteCt.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);

                Intent postCreation = new Intent(EventCreateActivity.this, HomeLandingPage.class);
                startActivity(postCreation);
            }
        });


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

}
