package livesmart.com.restClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dominik Poppek on 26.01.2017.
 */

public class Communicator {
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://127.0.0.1:8080/";

    public void loginPost(String username, String password){
        // Create a very simple REST adapter which points Livesmart-Webservice
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();

        // Create an instance of our LivesmartWebservice interface.
        LivesmartWebserviceInterface livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);

        // Create a call instance for performing user log-in
        Call<LoginResponse> call = livesmartWebservice.postLogin(username, password);

        call.enqueue(new Callback<LoginResponse>() {


            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse callResponse = response.body();
                callResponse.getMessage();

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });

    }
}
