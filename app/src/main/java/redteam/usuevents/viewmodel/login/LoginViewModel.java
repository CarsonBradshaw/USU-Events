package redteam.usuevents.viewmodel.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.main.MainActivity;
import redteam.usuevents.viewmodel.ViewModel;

/**
 * Created by Admin on 5/29/2017.
 */

public class LoginViewModel extends BaseObservable implements ViewModel, OnCompleteListener {


    private static final String TAG = LoginViewModel.class.getName();

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

    //callback for signInWithCredentials
    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");
            FirebaseAuthHelper.setFirebaseUser();
            //updateUI(user);
            startActivity(MainActivity.newIntent(LoginActivity.this));
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            Toast.makeText(LoginActivity.this, "Goole Authentication failed. Check network connection and try again.",
                    Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }

        // [START_EXCLUDE]
        //hideProgressDialog();
        // [END_EXCLUDE]
        progressDialog.setVisibility(View.GONE);
    }
    }
}
