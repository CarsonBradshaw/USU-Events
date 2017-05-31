package redteam.usuevents.viewmodel.login;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import redteam.usuevents.BR;
import redteam.usuevents.firebase.FirebaseAuthHelper;
import redteam.usuevents.util.GoogleClientHelper;
import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.main.MainActivity;
import redteam.usuevents.viewmodel.ViewModel;

/**
 * Created by Admin on 5/29/2017.
 */

public class LoginViewModel extends BaseObservable implements ViewModel, OnCompleteListener {

    private static final int RC_GOOGLE_SIGN_IN = 1;
    private static final String TAG = LoginViewModel.class.getName();

    private Context mContext;
    public boolean mIsLoading;


    public LoginViewModel(){
        mIsLoading = false;
    }


    @Override
    public void destroy() {

    }

    @Bindable
    public boolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading = isLoading;
        notifyPropertyChanged(BR.isLoading);
    }

    public void googleSignIn(@NonNull final View v) {
        Log.d("WORKING","WORKING");
        ((Activity) mContext).startActivityForResult(GoogleClientHelper.getGoogleSignInIntent(mContext, (FragmentActivity) mContext,
                (GoogleApiClient.OnConnectionFailedListener) mContext), RC_GOOGLE_SIGN_IN);
    }

    //callback for signInWithCredentials
    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");
            FirebaseAuthHelper.setFirebaseUser();
            //updateUI(user);
            mContext.startActivity(MainActivity.newIntent(mContext));
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            Toast.makeText(mContext, "Goole Authentication failed. Check network connection and try again.",
                    Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
        setIsLoading(false);
    }

    public void handleSuccessfullGoogleSignInActivityResult(Intent data, GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        setIsLoading(true);
        FirebaseAuthHelper.signInWithCredential(credential,this);
    }
}
