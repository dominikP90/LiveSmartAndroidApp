package livesmart.com.messaging;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import livesmart.com.dataModel.DeviceType;
import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Severity;
import livesmart.com.dataModel.StoveDevice;
import livesmart.com.utility.FirebaseConfig;
import livesmart.com.utility.FirebaseNotificationUtils;

/**
 * Created by Dominik Poppek on 11.01.2017.
 */

public class MyFirebaseMessagingService_BIG extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService_BIG.class.getSimpleName();

    private FirebaseNotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(remoteMessage.getData());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!FirebaseNotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(FirebaseConfig.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);

            // play notification sound
            FirebaseNotificationUtils notificationUtils = new FirebaseNotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    /**
     * Deserialize notification data and create Notification entity
     * @param remoteMessageData
     */
    private void handleDataMessage(Map<String, String> remoteMessageData) {

        try {
            Map<String, String> params = remoteMessageData;
            JSONObject jsonObj = new JSONObject(params);
            Log.e("JSON_OBJECT", jsonObj.toString());

            JSONNotificationData notificationData = new JSONNotificationData();
            notificationData.setNotificationID(Integer.parseInt((String)  jsonObj.get("notificationID")));
            notificationData.setNotificationText((String) jsonObj.get("notificationText"));
            notificationData.setDeviceId(Integer.parseInt((String) jsonObj.get("deviceID")));
            notificationData.setSeverity((String) jsonObj.get("severity"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            notificationData.setTimestamp(sdf.parse((String) jsonObj.get("timestamp")));
            //reasons
            String reasonString = (String) jsonObj.get("reasons");
            reasonString = reasonString.substring(1, reasonString.length() - 1);
            List<String> reasons = Arrays.asList(reasonString.split(","));
            notificationData.setReasons(reasons);
            Notification n = createNotification(notificationData);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Creates Notification objecty
     * @param jsonNotification
     * @return
     */
    private Notification createNotification(JSONNotificationData jsonNotification) {
        Notification n = new Notification();
        n.setNotificationID(jsonNotification.getNotificationID());
        n.setNotificationText(jsonNotification.getNotificationText());
        //Severity
        if (jsonNotification.getSeverity().equals("infoAlert")) {
            n.setSeverity(Severity.infoAlert);
        } else if (jsonNotification.getSeverity().equals("inforAlertSuggestion")) {
            n.setSeverity(Severity.inforAlertSuggestion);
        } else if (jsonNotification.getSeverity().equals("criticalAlertNoAction")) {
            n.setSeverity(Severity.criticalAlertNoAction);
        } else if (jsonNotification.getSeverity().equals("criticalAlertAction")) {
            n.setSeverity(Severity.criticalAlertAction);
        } else if (jsonNotification.getSeverity().equals("criticalAlertTimeToReact")) {
            n.setSeverity(Severity.criticalAlertTimeToReact);
        }

        n.setTimestamp(jsonNotification.getTimestamp());
        n.getReasons().addAll(jsonNotification.getReasons());
        //TODO get device from db
     /*               //Load device via deviceId
                    LivesmartWebserviceInterface livesmartWebservice = LivesmartWebserviceInterface.retrofit.create(LivesmartWebserviceInterface.class);
                    Call<DevicePOJO> call = livesmartWebservice.getDeviceById(jsonNotification.getDeviceId());
                    DevicePOJO device = null;
                    try {
                        device = call.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } */
        //Testdevice
        StoveDevice s1 = new StoveDevice();
        s1.setDeviceID(56);
        s1.setDeviceMAC("00:80:41:ae:fd:7e");
        s1.setDeviceName("Kitchen Stoven");
        s1.setDeviceType(DeviceType.STOVE);
        s1.setRoomName("Kitchen");
        s1.setDeviceTurnedOn(false);
        s1.setDeviceSeekerValue(70);
        n.setDevice(s1);
        return n;
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new FirebaseNotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new FirebaseNotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }


}