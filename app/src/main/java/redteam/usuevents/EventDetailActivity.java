package redteam.usuevents;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import redteam.usuevents.models.EventModel;

import static redteam.usuevents.R.id.imageView;


public class EventDetailActivity extends Activity{
    private int eventId;
    private TextView eventName;
    private TextView eventDescription;
    private TextView eventAddress;
    private EventModel event;
    private ImageView eventMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        
        setUpUIViews();

        Intent intent=getIntent();
        event=(EventModel)intent.getSerializableExtra("EventModel");
        eventName.setText(event.getTitle());
        eventDescription.setText(event.getDescription());

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(event.getLat()),Double.parseDouble(event.getLng()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();

        eventAddress.setText(address + ", " + city + ", " + state + " " + postalCode);

        String lat = event.getLat();
        String lng = event.getLng();

        Picasso.with(this).load("http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=17&size=2000x2000&sensor=false&markers=color:blue%7C"+lat+","+lng).into(eventMap);

        final Button interestButton = (Button) findViewById(R.id.interestButton_eventDetail);
        interestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){

            }
        });
        final Button subscribeButton = (Button) findViewById(R.id.subscribeButton_eventDetail);
        interestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){

            }
        });
        final Button reportButton = (Button) findViewById(R.id.reportButton_eventDetail);
        interestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){

            }
        });

    }
    private void setUpUIViews(){
        eventName=(TextView)findViewById(R.id.eventName);
        eventDescription=(TextView)findViewById(R.id.eventDescription);
        eventDescription.setMovementMethod(new ScrollingMovementMethod());
        eventAddress=(TextView)findViewById(R.id.addressField_eventDetail);
        eventMap = (ImageView)findViewById(imageView);
    }
}
