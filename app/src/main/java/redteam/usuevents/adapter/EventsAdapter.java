package redteam.usuevents.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.model.Event;

/**
 * Created by Admin on 6/29/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder> {

    private List<Event> mEventList;
    private boolean mTrending = false;

    public EventsAdapter(){
        this.mEventList = Collections.emptyList();
    }

    public EventsAdapter(List<Event> eventList){
        this.mEventList = eventList;
    }

    public void setEventList(List<Event> eventList){
        this.mEventList = eventList;
    }

    public void setTrending(boolean bool){
        this.mTrending = bool;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item_event;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new EventHolder(layoutInflater, parent, viewType, mTrending);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Event event = mEventList.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Event mEvent;
        private boolean mTrending;

        private TextView mTitle;
        private TextView mLocation;
        private TextView mTime;
        private ImageView mImage;
        private CardView mCardView;

        public void bind(Event event) {
            mEvent = event;
            if(mTrending) setTrendingElements();

            mTitle.setText(mEvent.getTitle());
            mLocation.setText(mEvent.getLocation());
            mTime.setText(mEvent.getBeginDateTime());
            //Glide has issues with loading on rotation from an abstracted adapter. Need to either migrate back into fragment or disallow rotation.
//            Glide.with(itemView).load(mEvent.getImageUri()).apply(RequestOptions.fitCenterTransform()).into(mImage);
        }

        public void setTrendingElements(){
            mCardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
            mTitle.setTextColor(Color.WHITE);
            mLocation.setTextColor(Color.WHITE);
            mTime.setTextColor(Color.WHITE);
        }

        public EventHolder(LayoutInflater inflater, ViewGroup parent, int layoutResourceId, boolean isTrending) {
            super(inflater.inflate(layoutResourceId, parent, false));
            mTrending = isTrending;

            mTitle = (TextView) itemView.findViewById(R.id.list_item_event_title);
            mLocation = (TextView) itemView.findViewById(R.id.list_item_event_location);
            mTime = (TextView) itemView.findViewById(R.id.list_item_event_time);
            mImage = (ImageView) itemView.findViewById(R.id.list_item_event_image);
            if(mTrending) mCardView = (CardView) itemView.findViewById(R.id.list_item_event_cardview);
        }

        @Override
        public void onClick(View v) {

        }
    }
}