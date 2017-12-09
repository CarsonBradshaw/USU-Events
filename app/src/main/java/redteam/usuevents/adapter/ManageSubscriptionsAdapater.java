package redteam.usuevents.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int mNotificationSettingString;
    private String mPrevText;

    private List<Topic> mTopicList;
    private Set<Topic> unsavedTopicChanges;

    public ManageSubscriptionsAdapater(){
        this.mTopicList = Collections.emptyList();
    }

    public void setManageSubscriptionsCallback(ManageSubscriptionsCallback callBack){
        this.manageSubscriptionsCallback = callBack;
    }

    public ManageSubscriptionsAdapater(List<Topic>topicList, Set<Topic> unsavedTopicChanges){
        this.mTopicList = topicList;
        this.unsavedTopicChanges = unsavedTopicChanges;
    }

    public void setTopicList(List<Topic> topicList){
        this.mTopicList = topicList;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return R.layout.subscriptions_settings_manage_header;
        }
        return R.layout.list_item_topic_manage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if(viewType == R.layout.subscriptions_settings_manage_header){
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            final TextView manageText = (TextView)view.findViewById(R.id.subscriptions_header_manage_clickable_area);
            manageText.setText("SAVE ALL");
            manageText.setOnClickListener(this);
            final TextView notificationSettingsText = (TextView)view.findViewById(R.id.notification_settings_time_text_view);
            notificationSettingsText.setText(mPrevText);
            ConstraintLayout constraintLayout = (ConstraintLayout)view.findViewById(R.id.subscriptions_header_manage_timer_clickable_area);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parent.getContext(), R.style.MainDialogTheme);
                    alertDialogBuilder.setView(R.layout.dialog_choose_notification_delay);
                    final AlertDialog alertDialog = alertDialogBuilder.show();
                    final GridLayout gridLayout = (GridLayout) alertDialog.findViewById(R.id.grid_time_chooser);
                    gridLayout.findViewById(mNotificationSettingString).setBackgroundResource(R.drawable.circle_selection_background);
                    for(int i = 0; i<gridLayout.getChildCount(); i++){
                        gridLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                        if(view instanceof TextView){
                                            gridLayout.findViewById(mNotificationSettingString).setBackgroundResource(0);
                                            mNotificationSettingString = view.getId();
                                            manageSubscriptionsCallback.updateNotificationPeriod(mNotificationSettingString, ((TextView) view).getText().toString());
                                            notificationSettingsText.setText(((TextView) view).getText());
                                            ((TextView) view).setBackgroundResource(R.drawable.circle_selection_background);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    alertDialog.dismiss();
                                                }
                                            }, 100);
                                            //alertDialog.dismiss();
                                        }
                                    }
                            });
                    }
                }
            });
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
        manageSubscriptionsCallback.updateUnsavedChangeState(false);
        manageSubscriptionsCallback.saveManageState();
    }

    public void setNotificationSettingString(int notificationSettingString) {
        mNotificationSettingString = notificationSettingString;
    }

    public void setPrevText(String newText){
        mPrevText = newText;
    }


    public class TopicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            if(unsavedTopicChanges.contains(mTopic)){
                unsavedTopicChanges.remove(mTopic);
            }else{
                unsavedTopicChanges.add(mTopic);
            }
            manageSubscriptionsCallback.updateUnsavedChangeState(unsavedTopicChanges.size() > 0);
            overlay.setVisibility( overlay.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mTopic.setSubscribed(!mTopic.isSubscribed());
        }
    }



}
