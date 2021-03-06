package livesmart.com.restClient;

import android.widget.Switch;

import livesmart.com.dataModel.User;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Dominik Poppek on 26.01.2017.
 */

/**
 */
public interface LivesmartWebserviceInterface {

    static final String SERVER_URL = "http://192.168.178.28:8080/";
    //static final String SERVER_URL = "http://131.159.192.15:8080/";

    /** User login */
    @FormUrlEncoded
    @PUT("LiveSmartWebService/rest/livesmart/login")
    Call<LoginResponse> postLogin(@Field("username") String username, @Field("password") String password);

    /** Get user with full data (first login/update) */
    @GET("LiveSmartWebService/rest/livesmart/user/{userId}")
    Call<UserPOJO> getUserbyUserId(@Path("userId") int userId);

    /** Switch device on/off */
    @FormUrlEncoded
    @PUT("LiveSmartWebService/rest/livesmart/device/switch")
    Call<SwitchResponse> switchOnOffDeviceById(@Field("deviceId") int deviceId, @Field("newState") boolean newState);

    /** Change device seeker value */
    @FormUrlEncoded
    @PUT("LiveSmartWebService/rest/livesmart/device/seeker")
    Call<SwitchResponse> changeSeekerValueDeviceById(@Field("deviceId") int deviceId, @Field("newValue")int newValue);

    /** Send device's firebase token to the server */
    @FormUrlEncoded
    @PUT("LiveSmartWebService/rest/livesmart/registerKey")
    Call<ResponseBody> sendFirebaseTokenToServer(@Field("userId") int userId, @Field("firebaseKey")String firebaseToken);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
