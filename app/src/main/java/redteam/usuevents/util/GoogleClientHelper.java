package redteam.usuevents.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import redteam.usuevents.R;

/**
 * Created by Admin on 5/29/2017.
 */

public class GoogleClientHelper {

    private static GoogleApiClient mGoogleApiClient;

    private static void configureGoogleApiClient(Context context, FragmentActivity fragmentActivity,
                                                 GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(fragmentActivity /* FragmentActivity */, onConnectionFailedListener/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public static Intent getGoogleSignInIntent(Context context, FragmentActivity fragmentActivity,
                                               GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener){
        configureGoogleApiClient(context, fragmentActivity, onConnectionFailedListener);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        return signInIntent;
    }
}
