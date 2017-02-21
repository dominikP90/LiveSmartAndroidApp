package livesmart.com.restClient;

import livesmart.com.dataModel.Device;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Dominik Poppek on 26.01.2017.
 */

/**
 * Interface for the REST-Client
 */
public interface LivesmartWebserviceInterface {

    static final String SERVER_URL = "http://192.168.1.22:8080/";
    //static final String SERVER_URL = "http://131.159.214.236:8080/";
    /**
     *User login
     */
    @FormUrlEncoded
    @retrofit2.http.PUT("LiveSmartWebService/rest/livesmart/login")
    Call<LoginResponse> postLogin(@Field("username") String username, @Field("password") String password);

    /**
     * Get device
     * @param deviceId
     * @return
     */
    @GET("LiveSmartWebService/rest/livesmart/device/{deviceId}")
    Call<DevicePOJO> getDeviceById(@Path("deviceId") int deviceId);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
