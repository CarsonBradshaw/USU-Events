package redteam.usuevents.view.profile;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import redteam.usuevents.R;
import redteam.usuevents.view.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profile_notifications:
                        break;
                    case R.id.profile_sign_out:
                        item.setChecked(true);
                        AlertDialog.Builder confirmSignOutDialog = new AlertDialog.Builder(ProfileActivity.this, R.style.ProfileDialogTheme);
                        confirmSignOutDialog.setMessage("Sign Out?");
                        confirmSignOutDialog.setPositiveButton("Sign Out", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                FirebaseAuth.getInstance().signOut();
                                startActivity(loginIntent);
                            }
                        });
                        confirmSignOutDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                item.setChecked(false);
                            }
                        });
                        confirmSignOutDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                item.setChecked(false);
                            }
                        });
                        confirmSignOutDialog.show();
                        break;
                    case R.id.profile_share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String shareText = getString(R.string.share_text) + getPackageName().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                        startActivity(Intent.createChooser(shareIntent, "Share USU Events via"));
                        finish();
                        break;
                    case R.id.profile_rate:
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(playStoreIntent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(ProfileActivity.this, " No Google Play Store available.", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        break;
                    case R.id.profile_contact:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:" + getString(R.string.support_email)));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        try{
                            startActivity(emailIntent);
                        }catch(ActivityNotFoundException e) {
                            Toast.makeText(ProfileActivity.this, " No email service available.", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                }
                //returns whether the selected item should be marked as selected
                return true;
            }
        });
//      //Loop to remove scrollOver effect
//        for (int i = 0; i < navigationView.getChildCount(); i++) {
//            navigationView.getChildAt(i).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        }

        //header code for navigationView
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.profile_header_name);
        //profile image code - look at migrating most to viewmodel and create helper for loading images
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
