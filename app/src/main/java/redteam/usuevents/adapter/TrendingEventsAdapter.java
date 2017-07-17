package redteam.usuevents.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.model.Event;
import redteam.usuevents.view.event.EventActivity;

/**
 * Created by Admin on 6/29/2017.
 */

public class TrendingEventsAdapter extends RecyclerView.Adapter<TrendingEventsAdapter.EventHolder> {

    private List<Event> mEventList;
    private boolean mTrending = false;

    public TrendingEventsAdapter(){
        this.mEventList = Collections.emptyList();
    }

    public TrendingEventsAdapter(List<Event> eventList){
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
        private TextView mTitleUnderline;
        private TextView mNumberInterested;
        private CardView mCardView;

        public void bind(Event event) {
            mEvent = event;

            mTitle.setText(mEvent.getTitle());
            mLocation.setText(mEvent.getLocation());
            mTime.setText(mEvent.getBeginDateTime());
            if(mTrending){
                mNumberInterested.setText(mEvent.getNumberInterested() + " Interested");
            }
            //Glide has issues with loading on rotation from an abstracted adapter. Need to either migrate back into fragment or disallow rotation.
//            Glide.with(itemView).load(mEvent.getImageUri()).apply(RequestOptions.fitCenterTransform()).into(mImage);
        }

        public void applyTrendingStyles(){
            mCardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
            mTitle.setTextColor(Color.WHITE);
            mLocation.setTextColor(Color.WHITE);
            mTime.setTextColor(Color.WHITE);
            mTitleUnderline.setVisibility(View.VISIBLE);
            mNumberInterested.setVisibility(View.VISIBLE);
        }

        public EventHolder(LayoutInflater inflater, ViewGroup parent, int layoutResourceId, boolean isTrending) {
            super(inflater.inflate(layoutResourceId, parent, false));
            mTrending = isTrending;

            mTitle = (TextView) itemView.findViewById(R.id.list_item_event_title);
            mLocation = (TextView) itemView.findViewById(R.id.list_item_event_location);
            mTime = (TextView) itemView.findViewById(R.id.list_item_event_time);
            mImage = (ImageView) itemView.findViewById(R.id.list_item_event_image);
            if(mTrending) {
                mCardView = (CardView) itemView.findViewById(R.id.list_item_event_cardview);
                mTitleUnderline = (TextView) itemView.findViewById(R.id.list_item_event_trending_underline);
                mNumberInterested = (TextView) itemView.findViewById(R.id.list_item_event_trending_number_interested);
                applyTrendingStyles();
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), EventActivity.class);
            itemView.getContext().startActivity(intent);
        }
    }
}