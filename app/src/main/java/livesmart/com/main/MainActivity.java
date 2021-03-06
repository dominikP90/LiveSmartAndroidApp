package livesmart.com.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import livesmart.com.dataModel.AlarmDevice;
import livesmart.com.dataModel.CameraDevice;
import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.DoorDevice;
import livesmart.com.dataModel.HeatingDevice;
import livesmart.com.dataModel.LightingDevice;
import livesmart.com.dataModel.MusicDevice;
import livesmart.com.dataModel.StoveDevice;
import livesmart.com.dataModel.WindowDevice;
import livesmart.com.restClient.LivesmartWebserviceInterface;
import livesmart.com.restClient.LoginResponse;
import livesmart.com.restClient.RuntimeTypeAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static livesmart.com.restClient.LivesmartWebserviceInterface.SERVER_URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static Retrofit retrofit;
    private  Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livesmart_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickProcessLogin(v);
            }
        });
    }

    /**
     * TestLogin without Username + pw + server call
     * @param view
     */
    public void onClickProcessLogin(View view) {
        initalizeWebservice();
        Intent intent = new Intent(getApplicationContext(), LiveSmartMain.class);
        intent.putExtra("FIRSTLOGIN", false);
        startActivity(intent);
    }

    /**
     * Validates login after login-button was pressed
     * @param view
     */
    public void onClickProcessLogin2(View view) {
        EditText enterUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText enterPassword = (EditText)findViewById(R.id.editTextPassword);

        String username = enterUsername.getText().toString();
        String password = enterPassword.getText().toString();

        // Initalize retrofit webservice
        initalizeWebservice();
        final LivesmartWebserviceInterface livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);

        // Create a call instance for performing user log-in
        Call<LoginResponse> call = livesmartWebservice.postLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse callResponse = response.body();
                //If correct credentials
                if (callResponse.getResult().equals("SUCCESS")){
                    //Load SharedPreferences & check if userId (if -1 then first login)
                    final SharedPreferences prefs = getSharedPreferences("livesmart.com", Context.MODE_PRIVATE);
                    final int userIdSharedPref = prefs.getInt("livesmart.com.userId", -1);
                    final int userIdcallResponse = callResponse.getUserId();

                    Intent intent = new Intent(getApplicationContext(), LiveSmartMain.class);
                    // first login if -1?
                    if (userIdSharedPref == -1) {
                        Toast.makeText(getApplicationContext(),
                                "Login successful. Your SmartHome will be set up...",Toast.LENGTH_SHORT).show();
                        intent.putExtra("FIRSTLOGIN", true);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Login successful. Redirecting...",Toast.LENGTH_SHORT).show();
                        intent.putExtra("FIRSTLOGIN", false);
                    }
                    //Send firebase token
                    sendFirebaseTokenToServer();

                    //Save userId
                    prefs.edit().putInt("livesmart.com.userId", userIdcallResponse).commit();
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
                Toast.makeText(getApplicationContext(),
                        "Login-Error: Please try again!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sending firebase token to server
     */
    private void sendFirebaseTokenToServer() {
        SharedPreferences prefs = getSharedPreferences("livesmart.com", Context.MODE_PRIVATE);
        int userIdSharedPref = prefs.getInt("livesmart.com.userId", -1);

        if(userIdSharedPref != -1) {
            LivesmartWebserviceInterface livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);
            // Create a call instance to send Firebase token to server
            Call<ResponseBody> call = livesmartWebservice.sendFirebaseTokenToServer(userIdSharedPref, FirebaseInstanceId.getInstance().getToken());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        Log.d("MainActivity", "Sending firebase token successfull!");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
    /**
     * Initalize retrofit REST-webservice
     */
    public static Retrofit initalizeWebservice() {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // adding all different container classes with their flag to deserialize polymorph object list
        final RuntimeTypeAdapterFactory<Device> typeFactory = RuntimeTypeAdapterFactory
                .of(Device.class, "deviceType") // Here you specify which is the parent class and what field particularizes the child class.
                .registerSubtype(AlarmDevice.class, "ALARM") // if the flag equals the class name, you can skip the second parameter. This is only necessary, when the "type" field does not equal the class name.
                .registerSubtype(CameraDevice.class, "CAMERA")
                .registerSubtype(DoorDevice.class, "DOOR")
                .registerSubtype(HeatingDevice.class, "HEATING")
                .registerSubtype(LightingDevice.class, "LIGHTING")
                .registerSubtype(MusicDevice.class, "MUSIC")
                .registerSubtype(StoveDevice.class, "STOVEN")
                .registerSubtype(WindowDevice.class, "WINDOW");

        builder.registerTypeAdapterFactory(typeFactory);

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
            public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Timestamp(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();

        //HttpClient with logger
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        OkHttpClient httpClient = httpClientBuilder.addInterceptor(logging).build();
        //Create Retrofit instance with costume timestamp adapter
        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

}
