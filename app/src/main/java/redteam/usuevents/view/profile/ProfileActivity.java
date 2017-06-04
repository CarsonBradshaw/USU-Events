package redteam.usuevents.view.profile;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import redteam.usuevents.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //look at way to bind and move method to viewmodel
        ImageView closeButton = (ImageView)findViewById(R.id.profile_toolbar_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logo clicked
                finish();
            }
        });

        //bind these to viewmodel as well
        NavigationView navigationView = (NavigationView) findViewById(R.id.profile_navigation_view);
//      //Loop to remove scrollOver effect
//        for (int i = 0; i < navigationView.getChildCount(); i++) {
//            navigationView.getChildAt(i).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        }
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.profile_header_name);
        //profile image code - look at migrating most to viewmodel and create helper for loading images
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        final CircleImageView profileImage = (CircleImageView) header.findViewById(R.id.profile_header_image);
        Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.fitCenterTransform()).into(profileImage);
        name.setText(user.getDisplayName());



    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
