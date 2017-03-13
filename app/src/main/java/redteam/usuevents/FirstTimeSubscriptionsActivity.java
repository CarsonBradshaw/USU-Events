package redteam.usuevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FirstTimeSubscriptionsActivity extends AppCompatActivity {


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String,List<String>> expandableListDetail;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_subscriptions);

        submitButton = (Button)findViewById(R.id.notificationManagmentSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FirstTimeSubscriptionsActivity.this, EventListActivity.class);
                startActivity(myIntent);
            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);


        expandableListDetail = this.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new FirstLevelAdapter(this, expandableListTitle, expandableListDetail, expandableListView);
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

//                if (expandableListAdapter.getChildrenCount(groupPosition) == 0 || expandableListAdapter.getChildrenCount(groupPosition) == 1) {
//                    return true;
//                } else {
//                    return false;
//                }
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//
//            }
//        });
//
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                return false;
//            }
//        });

    }

    public static LinkedHashMap<String, List<String>> getData() {

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
        womensSportsList.add("Volleyball");
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


//
//    protected void xmlButtonOnClickCall(View v){
//        createSharedPreferences(createCheckBoxCsvString());
//        Intent myIntent = new Intent(this, EventListActivity.class);
//        startActivity(myIntent);
//    }
//
//
//    protected String createCheckBoxCsvString(){
//
//        ArrayList<CheckBox> checkBoxArrayList = new ArrayList<CheckBox>();
//
//        CheckBox box1 = (CheckBox)findViewById(R.id.checkBox);
//        CheckBox box2 = (CheckBox)findViewById(R.id.checkBox2);
//        CheckBox box3 = (CheckBox)findViewById(R.id.checkBox3);
//        CheckBox box4 = (CheckBox)findViewById(R.id.checkBox4);
//        CheckBox box5 = (CheckBox)findViewById(R.id.checkBox5);
//        CheckBox box6 = (CheckBox)findViewById(R.id.checkBox6);
//        CheckBox box7 = (CheckBox)findViewById(R.id.checkBox7);
//
//        checkBoxArrayList.add(box1);
//        checkBoxArrayList.add(box2);
//        checkBoxArrayList.add(box3);
//        checkBoxArrayList.add(box4);
//        checkBoxArrayList.add(box5);
//        checkBoxArrayList.add(box6);
//        checkBoxArrayList.add(box7);
//
//        int numCheckedRemaining = 0;
//
//        for(int i=0; i<7; i++){
//            if(checkBoxArrayList.get(i).isChecked()){
//                numCheckedRemaining++;
//            }
//        }
//
//        String result = "";
//
//        for(int i=0; i<7; i++){
//            if(numCheckedRemaining == 1 && checkBoxArrayList.get(i).isChecked()){
//                result += i+1;
//                --numCheckedRemaining;
//            }else if(checkBoxArrayList.get(i).isChecked()){
//                result += i+1 + ",";
//                --numCheckedRemaining;
//            }
//        }
//        //Log.v(result,result);   //see createSharedPreferences for outputting the stored sharedPreferences string
//        return result;
//    }
//
//    protected void createSharedPreferences(String subscriptionsCSV){
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("notificationSubscriptions", subscriptionsCSV);
//        editor.apply();
//
//        String result = sharedPreferences.getString("notificationSubscriptions", "");
//        //Log.v(result, result);
//    }

    public class FirstLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> expandableListTitle;
        private LinkedHashMap<String, List<String>> expandableListDetail;
        private ExpandableListView expandableListView;
        private String groupText;
        private String childText;

        // Hashmap for keeping track of our checkbox check states
        private HashMap<Integer, boolean[]> mChildCheckStates;

        private ChildViewHolder childViewHolder;

        public final class ChildViewHolder {
            TextView mChildText;
            CheckBox mCheckBox;
        }


        public FirstLevelAdapter(Context context, List<String> expandableListTitle, LinkedHashMap<String, List<String>> expandableListDetail, ExpandableListView expandableListView) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
            this.expandableListView = expandableListView;
            mChildCheckStates = new HashMap<Integer, boolean[]>();
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
//            if (convertView == null) {
//                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.expandable_list_child, null);
//            }
//            final String expandedListText = (String) getChild(groupPosition, childPosition);
//            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.childTextView);
//            expandedListTextView.setText(expandedListText);
//            return convertView;

            final int mGroupPosition = groupPosition;
            final int mChildPosition = childPosition;

            //  I passed a text string into an activity holding a getter/setter
            //  which I passed in through "ExpListChildItems".
            //  Here is where I call the getter to get that text
            childText = getChild(mGroupPosition, mChildPosition).toString();

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {

                        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                        getChecked[mChildPosition] = isChecked;
                        mChildCheckStates.put(mGroupPosition, getChecked);

                    } else {

                        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                        getChecked[mChildPosition] = isChecked;
                        mChildCheckStates.put(mGroupPosition, getChecked);
                    }
                }
            });

            return convertView;


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
        public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String listTitle = (String) getGroup(listPosition);


            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_list_parent, null);
            }

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
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return false;
        }
    }
}
