package redteam.usuevents.view.profile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;
import redteam.usuevents.R;
import redteam.usuevents.view.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private ImageView mCloseButton;

    private View mProfileHeaderView;
    private CircleImageView mProfileImage;
    private TextView mProfileNameTextView;

    private NavigationView mNavigationView;
    
    private AlertDialog.Builder mProgressAlertDialogBuilder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mProgressAlertDialogBuilder = new AlertDialog.Builder(this, R.style.ProfileDialogTheme);
        mProgressAlertDialogBuilder.setView(R.layout.profile_progress_bar_dialog);

        bindViews();
        setClickListeners();
        loadProfileImage();
        setUserNameText();
    }

    //need to migrate once switch to MVVM is complete
    private void bindViews() {
        mCloseButton = (ImageView)findViewById(R.id.profile_toolbar_close);
        mNavigationView = (NavigationView) findViewById(R.id.profile_navigation_view);
        mProfileHeaderView = mNavigationView.getHeaderView(0);
        mProfileNameTextView = (TextView) mProfileHeaderView.findViewById(R.id.profile_header_name);
        mProfileImage = (CircleImageView) mProfileHeaderView.findViewById(R.id.profile_header_image);

    }

    private void setClickListeners() {

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logo clicked
                finish();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profile_notifications:
                        setResult(Activity.RESULT_OK, null);
                        Log.d("testing1", "inProfileAcitivityBeforeFinish");
                        finish();
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
                        mProgressAlertDialogBuilder.show();
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String shareText = getString(R.string.share_text) + getPackageName().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                        startActivity(Intent.createChooser(shareIntent, "Share USU Events via"));
                        finish();
                        break;
                    case R.id.profile_rate:
                        mProgressAlertDialogBuilder.show();
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
                        mProgressAlertDialogBuilder.show();
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


    }

    private void loadProfileImage() {
        String profileImageURL = "";
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            for(UserInfo profile : mFirebaseUser.getProviderData()) {
                profileImageURL =  "https://graph.facebook.com/" + profile.getUid() + "/picture?height=500";
            }
        }else{
            profileImageURL = mFirebaseUser.getPhotoUrl().toString();
        }
        Glide.with(this).load(profileImageURL).apply(RequestOptions.fitCenterTransform()).into(mProfileImage);
    }

    private void setUserNameText() {
        mProfileNameTextView.setText(mFirebaseUser.getDisplayName());
    }

    //method to remove OverScroll effect in menu on swipe past up and down
    private void removeMenuOverScrollEffect(){
        for (int i = 0; i < mNavigationView.getChildCount(); i++) {
            mNavigationView.getChildAt(i).setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_down);
    }
}
