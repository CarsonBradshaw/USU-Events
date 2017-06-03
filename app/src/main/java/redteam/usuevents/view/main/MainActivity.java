package redteam.usuevents.view.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import redteam.usuevents.R;
import redteam.usuevents.view.launchscreen.LaunchScreenActivity;
import redteam.usuevents.view.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            // not signed in
            startActivity(LoginActivity.newIntent(MainActivity.this));
            finish();
        }

        CircleImageView profileImage = (CircleImageView) findViewById(R.id.profile_image_button);
        Picasso.with(this).load(auth.getCurrentUser().getPhotoUrl()).into(profileImage);


    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
