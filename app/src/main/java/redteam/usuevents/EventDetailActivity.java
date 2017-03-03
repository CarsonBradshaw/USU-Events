package redteam.usuevents;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONObject;
import org.w3c.dom.Text;

import redteam.usuevents.models.EventModel;




public class EventDetailActivity extends Activity{
    private int eventId;
    private TextView eventName;
    private TextView eventDescription;
    private EventModel event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        
        setUpUIViews();

        Intent intent=getIntent();
        event=(EventModel)intent.getSerializableExtra("EventModel");
        eventName.setText(event.getTitle());
        eventDescription.setText(event.getDescription());


    }
    private void setUpUIViews(){
        eventName=(TextView)findViewById(R.id.eventName);
        eventDescription=(TextView)findViewById(R.id.eventDescription);
    }
}