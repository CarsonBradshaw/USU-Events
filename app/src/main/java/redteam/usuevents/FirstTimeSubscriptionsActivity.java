package redteam.usuevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class FirstTimeSubscriptionsActivity extends AppCompatActivity {
    //Toolbar code
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //Toolbar code end


    public ExpandableListView expandableListView;
    public NotificationsAdapter expandableListAdapter;
    public List<String> expandableListTitle;
    public LinkedHashMap<String,List<String>> expandableListDetail;
    public ArrayList<Boolean> finalSubscriptionsBoolArray;

    public static List<String> topicList = Arrays.asList("mBasketball","mFootball","mTennis",
            "mTrack","mGolf","mCrossCountry","wBasketball","wVolleyball","wTennis","wTrack",
            "wCrossCountry","wSoccer","wSoftball","wGymnastics","parties","miscUsu","userSubmitted");

    public Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //use to test onTokenRefresh in MyFirebaseInstanceIDService

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    FirebaseInstanceId.getInstance().deleteInstanceId();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_subscriptions);

        //Toolbar Code
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.activity_first_time_subscriptions);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //Toolbar Code End

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expandableListDetail = this.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new NotificationsAdapter(this, expandableListTitle, expandableListDetail, expandableListView);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0);
        //expandableListView.expandGroup(1);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if(parent.isGroupExpanded(groupPosition)){
                    parent.collapseGroup(groupPosition);
                }else{
                    boolean animateExpansion = false;
                    parent.expandGroup(groupPosition,animateExpansion);
                }

                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });



        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        //onSubmit
        submitButton = (Button)findViewById(R.id.notificationManagmentSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalSubscriptionsBoolArray = expandableListAdapter.getFinalSubscriptions();

                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFileName),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                Set<String> set = new HashSet<String>();
                //subscribes user to topic if box is checked, unsubscribes if otherwise
                //unsubscribe to remove a potential existing subscription

                boolean firstItem = true;
                for(int i = 0; i<topicList.size(); i++){
                    if(finalSubscriptionsBoolArray.get(i)){
                        Log.d("Subscriptions", "Subscribed to " + topicList.get(i));
                        FirebaseMessaging.getInstance().subscribeToTopic(topicList.get(i));
                        set.add(topicList.get(i));
                    }else{
                        Log.d("Subscriptions", "Unsubscribed from " + topicList.get(i));
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(topicList.get(i));
                    }
                }

                editor.putStringSet("notificationSubscriptions", set);
                editor.apply();

                Set<String> result = sharedPreferences.getStringSet("notificationSubscriptions", null);
                Log.d("SharedPreferences", result.toString());


                Intent myIntent = new Intent(FirstTimeSubscriptionsActivity.this, EventListActivity.class);
                startActivity(myIntent);
            }
        });
    }

    //Toolbar Code
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //Toolbar Code End

    public LinkedHashMap<String, List<String>> getData() {

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> mensSportsList = new ArrayList<String>();

        mensSportsList.add("Basketball");
        mensSportsList.add("Football");
        mensSportsList.add("Tennis");
        mensSportsList.add("Track and Field");
        mensSportsList.add("Golf");
        mensSportsList.add("Cross Country");

        List<String> womensSportsList = new ArrayList<String>();

        womensSportsList.add("Basketball");
        womensSportsList.add("Volleyball");
        womensSportsList.add("Tennis");
        womensSportsList.add("Track and Field");
        womensSportsList.add("Cross Country");
        womensSportsList.add("Soccer");
        womensSportsList.add("Softball");
        womensSportsList.add("Gymnastics");

        List<String> miscUsuEvents = new ArrayList<String>();
        List<String> parties = new ArrayList<String>();
        List<String> userSubmittedEvents = new ArrayList<String>();

        expandableListDetail.put("Utah State - Men's Sports", mensSportsList);
        expandableListDetail.put("Utah State - Women's Sports", womensSportsList);
        expandableListDetail.put("Parties", parties);
        expandableListDetail.put("Misc USU Sponsored Events", miscUsuEvents);
        expandableListDetail.put("User Submitted Events", userSubmittedEvents);

        return expandableListDetail;
    }

    public class NotificationsAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> expandableListTitle;
        private LinkedHashMap<String, List<String>> expandableListDetail;
        private ExpandableListView expandableListView;
        private String groupText;
        private String childText;

        public HashMap<Integer, boolean[]> mChildCheckStates;
        public HashMap<Integer, Boolean> mParentCheckStates;

        private GroupViewHolder groupViewHolder;
        private ChildViewHolder childViewHolder;

        public final class ChildViewHolder {
            TextView mChildText;
            CheckBox mCheckBox;
        }

        public final class GroupViewHolder {
            TextView mGroupText;
            CheckBox mGroupCheckBox;
        }


        public NotificationsAdapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<String>> expandableListDetail, ExpandableListView expandableListView) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
            this.expandableListView = expandableListView;
            mChildCheckStates = new HashMap<Integer, boolean[]>();
            boolean[] filler = new boolean[6];
            Arrays.fill(filler, false);
            mChildCheckStates.put(0, filler);
            boolean[] filler2 = new boolean[8];
            Arrays.fill(filler, false);
            mChildCheckStates.put(1, filler2);
            mParentCheckStates = new HashMap<Integer, Boolean>();
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final int mGroupPosition = groupPosition;
            final int mChildPosition = childPosition;

            //  I passed a text string into an activity holding a getter/setter
            //  which I passed in through "ExpListChildItems".
            //  Here is where I call the getter to get that text
            childText = getChild(mGroupPosition, mChildPosition).toString();

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.expandable_list_child, null);

                childViewHolder = new ChildViewHolder();

                childViewHolder.mChildText = (TextView) convertView.findViewById(R.id.childTextView);

                childViewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.childCheckBox);

                convertView.setTag(R.layout.expandable_list_child, childViewHolder);

            } else {

                childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.expandable_list_child);
            }

            childViewHolder.mChildText.setText(childText);
                /*
             * You have to set the onCheckChangedListener to null
             * before restoring check states because each call to
             * "setChecked" is accompanied by a call to the
             * onCheckChangedListener
            */
            childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

            if (mChildCheckStates.containsKey(mGroupPosition)) {
                /*
                 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
                 * the value of the parent view (group) of this child (aka, the key),
                 * then retrive the boolean array getChecked[]
                */
                boolean getChecked[] = mChildCheckStates.get(mGroupPosition);

                // set the check state of this position's checkbox based on the
                // boolean value of getChecked[position]
                childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

            } else {

                /*
                * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
                * contain the value of the parent view (group) of this child (aka, the key),
                * (aka, the key), then initialize getChecked[] as a new boolean array
                *  and set it's size to the total number of children associated with
                *  the parent group
                */
                boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];

                // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
                mChildCheckStates.put(mGroupPosition, getChecked);

                // set the check state of this position's checkbox based on the
                // boolean value of getChecked[position]
                childViewHolder.mCheckBox.setChecked(false);
            }

            childViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    boolean oldChecked[] = mChildCheckStates.get(mGroupPosition);
                    boolean newChecked[] = oldChecked;
                    newChecked[mChildPosition] = isChecked;
                    mChildCheckStates.put(mGroupPosition, newChecked);
                    if (isChecked && isAllCheckedChild(newChecked)) {
                        mParentCheckStates.put(mGroupPosition, true);
                        notifyDataSetChanged();
                    }else if(!isChecked && mParentCheckStates.get(mGroupPosition)){
                        mParentCheckStates.put(mGroupPosition, false);
                        notifyDataSetChanged();
                    }
                }
            });


            return convertView;
        }

        public boolean isAllCheckedChild(boolean[] childCheckedStates){
            for(Boolean item: childCheckedStates){
                if(!item.booleanValue()){
                    return false;
                }
            }
            return true;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.expandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(final int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final String listTitle = (String) getGroup(listPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_list_parent, null);
                mParentCheckStates.put(listPosition, false);
            }
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.parentCheckBox);
            checkBox.setChecked(mParentCheckStates.get(listPosition));
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = !mParentCheckStates.get(listPosition);
                    mParentCheckStates.put(listPosition, isChecked);
                    if (getChildrenCount(listPosition) > 0 && isChecked) {
                        //update mchildren bool matrix with all checked and notifyDataSetChanged();
                        boolean[] trueFiller = new boolean[getChildrenCount(listPosition)];
                        Arrays.fill(trueFiller, true);
                        mChildCheckStates.put(listPosition, trueFiller);
                        notifyDataSetChanged();
                    } else if (getChildrenCount(listPosition) > 0) {
                        boolean[] falseFiller = new boolean[getChildrenCount(listPosition)];
                        Arrays.fill(falseFiller, false);
                        mChildCheckStates.put(listPosition, falseFiller);
                        notifyDataSetChanged();
                    }
                }
            });

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    mParentCheckStates.put(listPosition, isChecked);
//                    if (getChildrenCount(listPosition) > 0 && isChecked) {
//                        //update mchildren bool matrix with all checked and notifyDataSetChanged();
//                        boolean[] trueFiller = new boolean[getChildrenCount(listPosition)];
//                        Arrays.fill(trueFiller, true);
//                        mChildCheckStates.put(listPosition, trueFiller);
//                        notifyDataSetChanged();
//                    } else if (getChildrenCount(listPosition) > 0) {
//                        boolean[] falseFiller = new boolean[getChildrenCount(listPosition)];
//                        Arrays.fill(falseFiller, false);
//                        mChildCheckStates.put(listPosition, falseFiller);
//                        notifyDataSetChanged();
//                    }
//                }
//            });


            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setImageDrawable(null);

            if (isExpanded && getChildrenCount(listPosition) != 0) {
                imageView.setImageResource(R.drawable.collapse_arrow_48dp);
            } else if (getChildrenCount(listPosition) != 0) {
                imageView.setImageResource(R.drawable.expand_arrow_48dp);
            }
            TextView listTitleTextView = (TextView) convertView.findViewById(R.id.parentTextView);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }

        public boolean isParentChecked(int listPosition) {
            return mParentCheckStates.get(listPosition);
        }

        public final ArrayList<Boolean> getFinalSubscriptions() {
            ArrayList<Boolean> result = new ArrayList<Boolean>();
            //TODO fill with state of all children and groups 2-4
            boolean[] mensChecked;
            boolean[] womensChecked;
            mensChecked = mChildCheckStates.get(0);
            womensChecked = mChildCheckStates.get(1);

            for(Boolean item : mensChecked){
                result.add(item);
            }

            for(Boolean item : womensChecked){
                result.add(item);
            }

            result.add(mParentCheckStates.get(2));
            result.add(mParentCheckStates.get(3));
            result.add(mParentCheckStates.get(4));

            return result;
        }
    }
}
