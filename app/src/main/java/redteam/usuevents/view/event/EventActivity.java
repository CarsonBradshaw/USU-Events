package redteam.usuevents.view.event;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.speech.RecognitionListener;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import redteam.usuevents.R;
import redteam.usuevents.model.Event;
import redteam.usuevents.view.main.MainActivity;

/**
 * Created by Admin on 6/26/2017.
 */

public class EventActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RelativeLayout calendarClickableArea;
    private RelativeLayout locationClickableArea;

    private TextView title;
    private TextView category;
    private TextView date;
    private TextView time;
    private TextView location;
    private TextView address;
    private TextView numInterested;

    private Event event;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event = (Event) getIntent().getExtras().getSerializable(MainActivity.EVENT_KEY);

        bindViews();
        fillWithEventInfo();
        setClickListeners();
    }

    private void bindViews(){
        toolbar = (Toolbar) findViewById(R.id.activity_event_toolbar);
        calendarClickableArea = (RelativeLayout) findViewById(R.id.event_calendar_area);
        locationClickableArea = (RelativeLayout) findViewById(R.id.event_location_area);
        title = (TextView) findViewById(R.id.event_title);
        category = (TextView) findViewById(R.id.event_category);
        date = (TextView) findViewById(R.id.event_date);
        time = (TextView) findViewById(R.id.event_time);
        location = (TextView) findViewById(R.id.event_location);
        address = (TextView) findViewById(R.id.event_address);
        numInterested = (TextView) findViewById(R.id.event_num_interested);
    }

    private void fillWithEventInfo() {
        title.setText(event.getTitle());
        category.setText(event.getCategory());
//        date.setText();
//        time.setText();
//        location.setText();
//        address.setText();
        numInterested.setText(event.getNumberInterested() + " INTERESTED");
    }

    private void setClickListeners(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calendarClickableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssX");
                Date date = new Date();
                try {
                    date = sdf.parse(event.getBeginDateTime());
                    Log.d("here", date.getTime() + "");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, event.getTitle())
                        .putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription())
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation())
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime())
                        .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

                startActivity(calendarIntent);
            }
        });

        locationClickableArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
