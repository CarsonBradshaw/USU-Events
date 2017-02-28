package redteam.usuevents;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * Created by DGDeskAsus on 2/25/2017.
 */

public class CustomEventRow  extends ArrayAdapter<String> {

    public CustomEventRow(Context context, String[] eventsTestArray) {
        super(context, R.layout.event_layout, eventsTestArray);


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater eventInflater = LayoutInflater.from(getContext());
        View customView = eventInflater.inflate(R.layout.event_layout, parent, false);

        String singleEventItem = getItem(position);
        TextView usernameRow = (TextView) customView.findViewById(R.id.usernameRow);
        usernameRow.setText(singleEventItem);

        String singleEventDetail=getItem(position);
        TextView eventDetailRow=(TextView) customView.findViewById(R.id.eventDetailRow);
        eventDetailRow.setText(singleEventDetail);
        return customView;
    }
}