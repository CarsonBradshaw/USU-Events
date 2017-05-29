package redteam.usuevents.view.launchscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.main.MainActivity;

public class LaunchScreenActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
