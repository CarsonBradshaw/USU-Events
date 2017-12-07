package redteam.usuevents.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import redteam.usuevents.model.Topic;
import redteam.usuevents.view.event.EventActivity;
import redteam.usuevents.view.main.MainActivity;
import redteam.usuevents.view.main.ManageSubscriptionsCallback;

/**
 * Created by Admin on 12/6/2017.
 */

public class ManageSubscriptionsAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private ManageSubscriptionsCallback manageSubscriptionsCallback;

    private List<Topic> mTopicList;

    public ManageSubscriptionsAdapater(){
        this.mTopicList = Collections.emptyList();
    }

    public void setManageSubscriptionsCallback(ManageSubscriptionsCallback callBack){
        this.manageSubscriptionsCallback = callBack;
    }

    public ManageSubscriptionsAdapater(List<Topic>topicList){
        this.mTopicList = topicList;
    }

    public void setTopicList(List<Topic> topicList){
        this.mTopicList = topicList;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return R.layout.subscriptions_settings_header;
        }
        return R.layout.list_item_topic_manage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == R.layout.subscriptions_settings_header){
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            TextView manageText = (TextView)view.findViewById(R.id.subscriptions_header_manage_clickable_area);
            manageText.setText("SAVE ALL");
            manageText.setOnClickListener(this);
            return new RecyclerView.ViewHolder(view) {

            };
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TopicHolder(layoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0)
            return;
        Topic topic = mTopicList.get(position - 1);
        ((TopicHolder)holder).bind(topic);
    }

    @Override
    public int getItemCount() {
        return mTopicList.size() + 1;
    }

    @Override
    public void onClick(View view) {
        manageSubscriptionsCallback.saveManageState();
    }


    public static class TopicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Topic mTopic;

        private View overlay;

        private TextView mTitle;
        private TextView mLocation;
        private TextView mTime;
        private ImageView mImage;
        private TextView mTitleUnderline;
        private TextView mNumberInterested;
        private CardView mCardView;

        public void bind(Topic topic) {
            mTopic = topic;

            mTitle.setText(mTopic.getTopic());
            mLocation.setText(mTopic.getNumActiveEvents()+" Upcoming Events");
            mTime.setText(mTopic.getNumSubscribers()+" Active Subscribers");
            overlay.setVisibility(topic.isSubscribed() ? View.GONE : View.VISIBLE);
            //Glide has issues with loading on rotation from an abstracted adapter. Need to either migrate back into fragment or disallow rotation.
//            Glide.with(itemView).load(mEvent.getImageUri()).apply(RequestOptions.fitCenterTransform()).into(mImage);
        }


        public TopicHolder(LayoutInflater inflater, ViewGroup parent, int layoutResourceId) {
            super(inflater.inflate(layoutResourceId, parent, false));

            mTitle = (TextView) itemView.findViewById(R.id.list_item_event_title);
            mLocation = (TextView) itemView.findViewById(R.id.list_item_event_location);
            mTime = (TextView) itemView.findViewById(R.id.list_item_event_time);
            overlay = (View) itemView.findViewById(R.id.topic_overlay);
//            mImage = (ImageView) itemView.findViewById(R.id.list_item_event_image);
//            mCardView = (CardView) itemView.findViewById(R.id.list_item_event_cardview);
//            mTitleUnderline = (TextView) itemView.findViewById(R.id.list_item_event_trending_underline);
//            mNumberInterested = (TextView) itemView.findViewById(R.id.list_item_event_trending_number_interested);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(itemView.getContext(), EventActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(MainActivity.EVENT_KEY, mTopic);
//            intent.putExtras(bundle);
//            itemView.getContext().startActivity(intent);
            overlay.setVisibility( overlay.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mTopic.setSubscribed(!mTopic.isSubscribed());
        }
    }



}
