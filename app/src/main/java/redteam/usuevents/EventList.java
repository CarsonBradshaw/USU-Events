package redteam.usuevents;

import android.app.Activity;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import redteam.usuevents.models.EventModel;

public class EventList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //eventListTestArray integrating database
        final String[] eventListTestArray={"Event 1","Event 2", "Event 3","Event 4","Event 5","Event 6"};


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

        new DatabaseJsonRetriever().execute("http://144.39.212.67/db_view.php");
    }

    public class DatabaseJsonRetriever extends AsyncTask<String, String, List<EventModel>>{

        @Override
        protected List<EventModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("Events");

                List<EventModel> eventModelList = new ArrayList<>();

                for(int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String category_id = finalObject.getString("category_id");
                    String event_id = finalObject.getString("event_id");
                    String title = finalObject.getString("title");
                    String description = finalObject.getString("description");
                    String date_time = finalObject.getString("date_time");
                    String address = finalObject.getString("address");
                    String lat = finalObject.getString("lat");
                    String lng = finalObject.getString("lng");
                    String voteCt = finalObject.getString("voteCt");

                    EventModel eventModel = new EventModel(category_id, event_id, title, description, date_time, address, lat, lng, voteCt);
                    eventModelList.add(eventModel);
                }

                return eventModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EventModel> result) {
            super.onPostExecute(result);
            //TODO need to set data from EventModel to the list
        }
    }
}
