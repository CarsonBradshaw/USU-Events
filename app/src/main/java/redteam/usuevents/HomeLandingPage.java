package redteam.usuevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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

public class HomeLandingPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_landing_page);


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

    }





}