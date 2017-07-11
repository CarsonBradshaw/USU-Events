package redteam.usuevents.view.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import redteam.usuevents.R;

/**
 * Created by Admin on 6/26/2017.
 */

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }
}
