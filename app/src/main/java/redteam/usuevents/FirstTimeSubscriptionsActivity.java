package redteam.usuevents;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class FirstTimeSubscriptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_subscriptions);
    }

    protected void xmlButtonOnClickCall(View v){
        createSharedPreferences(createCheckBoxCsvString());
    }

    protected String createCheckBoxCsvString(){

        ArrayList<CheckBox> checkBoxArrayList = new ArrayList<CheckBox>();

        CheckBox box1 = (CheckBox)findViewById(R.id.checkBox);
        CheckBox box2 = (CheckBox)findViewById(R.id.checkBox2);
        CheckBox box3 = (CheckBox)findViewById(R.id.checkBox3);
        CheckBox box4 = (CheckBox)findViewById(R.id.checkBox4);
        CheckBox box5 = (CheckBox)findViewById(R.id.checkBox5);
        CheckBox box6 = (CheckBox)findViewById(R.id.checkBox6);
        CheckBox box7 = (CheckBox)findViewById(R.id.checkBox7);

        checkBoxArrayList.add(box1);
        checkBoxArrayList.add(box2);
        checkBoxArrayList.add(box3);
        checkBoxArrayList.add(box4);
        checkBoxArrayList.add(box5);
        checkBoxArrayList.add(box6);
        checkBoxArrayList.add(box7);

        int numCheckedRemaining = 0;

        for(int i=0; i<7; i++){
            if(checkBoxArrayList.get(i).isChecked()){
                numCheckedRemaining++;
            }
        }

        String result = "";

        for(int i=0; i<7; i++){
            if(numCheckedRemaining == 1 && checkBoxArrayList.get(i).isChecked()){
                result += i+1;
                --numCheckedRemaining;
            }else if(checkBoxArrayList.get(i).isChecked()){
                result += i+1 + ",";
                --numCheckedRemaining;
            }
        }
        //Log.v(result,result);   //see createSharedPreferences for outputting the stored sharedPreferences string
        return result;
    }

    protected void createSharedPreferences(String subscriptionsCSV){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notificationSubscriptions", subscriptionsCSV);
        editor.apply();

        String result = sharedPreferences.getString("notificationSubscriptions", "");
        //Log.v(result, result);
    }


}
