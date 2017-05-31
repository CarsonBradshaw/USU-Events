package redteam.usuevents.firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import redteam.usuevents.view.login.LoginActivity;
import redteam.usuevents.view.main.MainActivity;

/**
 * Created by Admin on 5/29/2017.
 */

public class FirebaseAuthHelper {

    private static final String TAG = FirebaseAuthHelper.class.getName();

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mUser;

    public static void setFirebaseUser(){
        mUser = mAuth.getCurrentUser();
    }

    public FirebaseAuthHelper(){
        mAuth = FirebaseAuth.getInstance();
    }

    public static void signInWithCredential(AuthCredential credential, OnCompleteListener<AuthResult> completeListener) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(completeListener);
    }
}
