package redteam.usuevents;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

public class EventList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //eventListTestArray integrating database
        final String[] eventListTestArray={"Event 1","Event 5", "Event 4","Event 3","Event 4","Event 5"};


        ListAdapter eventAdapter = new CustomEventRow(this,eventListTestArray);
        ListView eventListView=(ListView) findViewById(R.id.eventListView);
        eventListView.setAdapter(eventAdapter);

        eventListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent,View view, int position, long id){
                        String eventList= String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(EventList.this, eventList, Toast.LENGTH_LONG).show();
                    }
                }
        );



    }
}
