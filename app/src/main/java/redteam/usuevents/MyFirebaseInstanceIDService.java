package redteam.usuevents;

import android.app.Service;

/**
 * Created by Carson on 3/16/2017.
 */

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Set;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFileName),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //read notificationSubscriptions from sharedPreferences.
        //if exists, subscribe to topics
        Set<String> result = sharedPreferences.getStringSet("notificationSubscriptions", null);
        if(result!=null){
            for(String topic : FirstTimeSubscriptionsActivity.topicList){
                if(result.contains(topic)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(topic);
                    Log.d("Subscribed from prefs", topic);
                }
            }
        }


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}