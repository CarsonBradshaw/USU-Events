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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import redteam.usuevents.models.EventModel;

public class EventListActivity extends Activity {

    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventListView=(ListView)findViewById(R.id.eventListView);





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
        protected void onPostExecute(final List<EventModel> result) {
            super.onPostExecute(result);
            if(result!=null){
                EventAdapter adapter=new EventAdapter(getApplicationContext(),R.layout.event_layout,result);
                eventListView.setAdapter(adapter);
                eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        EventModel eventModel=result.get(position);
                        Intent intent=new Intent(EventListActivity.this,EventDetailActivity.class);
                        intent.putExtra("EventModel",eventModel);
                        startActivity(intent);
                    }
                });
            }

        } //TODO need to set data from EventModel to the list
    }
    public class EventAdapter extends ArrayAdapter<EventModel> {

        private List<EventModel> eventModelList;
        private int resource;
        private LayoutInflater inflater;
        public EventAdapter(Context context, int resource, List<EventModel> objects) {
            super(context, resource, objects);
            eventModelList=objects;
            this.resource=resource;
            inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent){
           if(convertView==null){
               convertView=inflater.inflate(R.layout.event_layout,null);
           }
            TextView eventNameRow;
            TextView eventDetailRow;

            eventNameRow=(TextView)convertView.findViewById(R.id.eventNameRow);
            eventDetailRow=(TextView)convertView.findViewById(R.id.eventDetailRow);

            eventNameRow.setText(eventModelList.get(position).getTitle());
            eventDetailRow.setText(eventModelList.get(position).getDescription());

            return convertView;
        }

    }





}
