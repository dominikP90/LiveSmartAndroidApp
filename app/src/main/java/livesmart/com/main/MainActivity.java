package livesmart.com.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import livesmart.com.dataModel.Notification;
import livesmart.com.restClient.LivesmartWebserviceInterface;
import livesmart.com.utility.FirebaseConfig;
import livesmart.com.utility.FirebaseNotificationUtils;
import livesmart.com.restClient.Communicator;
import livesmart.com.restClient.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static livesmart.com.main.LiveSmartMain.notifications;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SERVER_URL = "http://192.168.1.19:8080/";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private  Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livesmart_login);

        loginButton = (Button) findViewById(R.id.loginButton);

        //Put Firebase-API-Token into SharedPreferences if it's not already saved
        Log.e(TAG, "Firebase-Token: " + FirebaseInstanceId.getInstance().getToken());
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FirebaseConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        if (regId == null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("regId", FirebaseInstanceId.getInstance().getToken());
            editor.commit();
        }


        //txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        //txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(FirebaseConfig.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FirebaseConfig.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(FirebaseConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                    //txtMessage.setText(message);
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickProcessLogin(v);
            }
        });
    }

    /**
     * Validates login after login-button was pressed
     * @param view
     */
    public void onClickProcessLogin(View view) {
        EditText enterUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText enterPassword = (EditText)findViewById(R.id.editTextPassword);

        String username = enterUsername.getText().toString();
        String password = enterPassword.getText().toString();

        // Create an instance of LivesmartWebservice interface.
        LivesmartWebserviceInterface livesmartWebservice = LivesmartWebserviceInterface.retrofit.create(LivesmartWebserviceInterface.class);

        // Create a call instance for performing user log-in
        Call<LoginResponse> call = livesmartWebservice.postLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse callResponse = response.body();
                //If correct credentials
                if (callResponse.getResult().equals("SUCCESS")){
                    Toast.makeText(getApplicationContext(),
                            "Login successful. Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LiveSmartMain.class);
                    startActivity(intent);
                }
                //Wrong credentials
                else {
                    Toast.makeText(getApplicationContext(),callResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * Show Firebase-API-Key for testing purpose
     * @param view
     */
    public void onClickCancelButton(View view) {
        TextView apiKey = (TextView) findViewById(R.id.apiKey);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(FirebaseConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        if (regId == null) {
            apiKey.setText(FirebaseInstanceId.getInstance().getToken());
        } else {
            apiKey.setText(regId);
        }
        view.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register FCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FirebaseConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FirebaseConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        FirebaseNotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}