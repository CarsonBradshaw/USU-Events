package redteam.usuevents.view.launchscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.main.MainActivity;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //only used without firebase model
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startActivity(MainActivity.newIntent(LaunchScreenActivity.this));
            finish();
        } else {
            // not signed in
            startActivity(LoginActivity.newIntent(LaunchScreenActivity.this));
            finish();
        }
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LaunchScreenActivity.class);
        return intent;
    }

    //TODO - delete with viewModel changes
    public void finishActivity(){
        finish();
    }
}
