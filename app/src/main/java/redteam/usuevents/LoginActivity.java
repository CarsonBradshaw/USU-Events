package redteam.usuevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    TextView textStatus;
    LoginButton login_button;
    CallbackManager callbackManager;

    private static final int REQ_CODE = 9001;
    private static final String TAG = "ActivityLifeTracker";


    private LinearLayout login_section;
    private Button SignOut;
    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_section = (LinearLayout)findViewById(R.id.login_section);
        SignOut = (Button)findViewById(R.id.bn_logout);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        SignOut.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        initializeControls();
        loginWithFB();

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bn_login:
                signIn();
                break;
            case R.id.bn_logout:
                signOut();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text = "Connection Failed";
        Toast.makeText(context, text, duration).show();
    }

    private void signIn(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }
    private void signOut(){


        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = "Google Logout Successful";
                Toast.makeText(context, text, duration).show();
            }
        });

    }
    private void handleResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            updateUI(true);
            Intent firstTime = new Intent(this, FirstTimeSubscriptionsActivity.class);

            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFileName),MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            Intent existing = new Intent(this, HomeLandingPage.class);

            if(sharedPreferences.getStringSet("notificationSubscriptions",null)!=null){
                startActivity(existing);
            }else {
                startActivity(firstTime);
            }
        }
        else {
            updateUI(false);
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

    }

    private void updateUI(boolean isLogin){
        if(isLogin){
            SignIn.setVisibility(View.GONE);
            SignOut.setVisibility(View.VISIBLE);
            login_section.setVisibility(View.VISIBLE);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = "Google Login Successful";
            Toast.makeText(context, text, duration).show();
        }
        else {
            SignIn.setVisibility(View.VISIBLE);
            SignOut.setVisibility(View.GONE);
            login_section.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeControls(){
        callbackManager = CallbackManager.Factory.create();
        textStatus = (TextView)findViewById(R.id.textStatus);
        login_button = (LoginButton)findViewById(R.id.login_button);
        login_button.setReadPermissions("email", "public_profile");
    }

    private void loginWithFB(){
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                //textStatus.setText("Login Success\n"+loginResult.getAccessToken());
                textStatus.setText("Login Success\n");

                Intent firstTime = new Intent(LoginActivity.this, FirstTimeSubscriptionsActivity.class);

                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFileName),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                Intent existing = new Intent(LoginActivity.this, HomeLandingPage.class);

                if(sharedPreferences.getStringSet("notificationSubscriptions",null)!=null){
                    startActivity(existing);
                }else {
                    startActivity(firstTime);
                }

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = "Facebook Login Successful";
                Toast.makeText(context, text, duration).show();
            }

            @Override
            public void onCancel() {
                textStatus.setText("Login Cancelled.\n");
            }

            @Override
            public void onError(FacebookException error) {
                textStatus.setText("Login Error: "+error.getMessage());
            }
        });
    }







}