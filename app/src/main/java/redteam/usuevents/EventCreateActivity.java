package redteam.usuevents;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import redteam.usuevents.models.EventModel;

/**
 * Created by DaShare on 3/2/17.
 */

public class EventCreateActivity extends Activity{

    EditText title, description, event_id, category_id, address, date_time, lat, lng, voteCt;
    Button insert, listevents;
    //TextView result;

    RequestQueue requestQueue;
    String insertURL = "http://144.39.212.67/db_create.php";
   // String showURL = "http://144.39.212.67/db_view.php";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        address = (EditText) findViewById(R.id.address);
        category_id = (EditText) findViewById(R.id.category_id);
        event_id = (EditText) findViewById(R.id.event_id);
        date_time = (EditText) findViewById(R.id.date_time);
        lat = (EditText) findViewById(R.id.lat);
        lng = (EditText) findViewById(R.id.lng);
        voteCt = (EditText) findViewById(R.id.voteCt);

        insert = (Button) findViewById(R.id.insert);
       // listevents = (Button) findViewById(R.id.listevents);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

       /* listevents.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        showURL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray USU_Events = response.getJSONArray("USU_Events");
                            for(int i = 0; i < USU_Events.length(); i++){
                                JSONObject USU_Event = USU_Events.getJSONObject(i);

                                String title = USU_Event.getString("title");
                                String description = USU_Event.getString("description");
                                String category_id = USU_Event.getString("category_id");
                                String event_id = USU_Event.getString("event_id");
                                String address = USU_Event.getString("address");
                                String date_time = USU_Event.getString("date_time");
                                String lat = USU_Event.getString("lat");
                                String lng = USU_Event.getString("lng");
                                String voteCt = USU_Event.getString("voteCt");

                                result.append(title+" "+description+" "+category_id+" "+address+" "+date_time+" "+lat+" "+lng+" "+voteCt+ "\n");

                            }
                            result.append("===\n");

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });*/
        insert.setOnClickListener(new View.OnClickListener(){
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
            }
        });
    }
}
