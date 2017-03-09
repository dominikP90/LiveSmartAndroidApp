package livesmart.com.messaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
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
import livesmart.com.main.LiveSmartMain;
import livesmart.com.utility.FirebaseNotificationUtils;

/**
 * Created by Dominik Poppek on 11.01.2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private FirebaseNotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Notification n = null;
        //Check for data
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            n = handleDataMessage(remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        sendNotification(n);

    }

    private void sendNotification(Notification n) {
        Intent intent = new Intent(this, LiveSmartMain.class);
        //Add Notification
        LiveSmartMain.notifications.add(0, n);
        LiveSmartMain.showNotificationTab = true;

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("NOTIFICATION", n);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        this.sendBroadcast(new Intent().setAction("newNotification"));

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getApplicationInfo().icon)
                .setContentTitle("LiveSmart Notification")
                .setContentText(n.getNotificationText())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


    /**
     * Deserialize notification data and create Notification entity
     * @param remoteMessageData
     */
    private Notification handleDataMessage(Map<String, String> remoteMessageData) {
        Notification n = null;
        try {
            Map<String, String> params = remoteMessageData;
            JSONObject jsonObj = new JSONObject(params);
            Log.e("JSON_OBJECT", jsonObj.toString());

            JSONNotificationData notificationData = new JSONNotificationData();
            notificationData.setNotificationID(Integer.parseInt((String)  jsonObj.get("notificationID")));
            notificationData.setNotificationText("TESTFCM__: " + (String) jsonObj.get("notificationText"));
            notificationData.setDeviceId(Integer.parseInt((String) jsonObj.get("deviceID")));
            notificationData.setSeverity((String) jsonObj.get("severity"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            notificationData.setTimestamp(sdf.parse((String) jsonObj.get("timestamp")));
            //reasons
            String reasonString = (String) jsonObj.get("reasons");
            reasonString = reasonString.substring(1, reasonString.length() - 1);
            List<String> reasons = Arrays.asList(reasonString.split(","));
            notificationData.setReasons(reasons);
            n = createNotification(notificationData);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return n;
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
        s1.setDeviceTurnedOn(true);
        s1.setDeviceSeekerValue(70);
        n.setDevice(s1);
        return n;
    }


}