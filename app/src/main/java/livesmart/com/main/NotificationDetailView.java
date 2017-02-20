package livesmart.com.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Severity;
import livesmart.com.utility.DevicesAdapter;
import livesmart.com.utility.NotificationAdapter;

import static livesmart.com.main.R.raw.notification;


/**
 * Created by Dominik Poppek on 18.12.2016.
 */

public class NotificationDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification Detail");

        //Device from RoomDevicesView
        Notification notificationIntent = (Notification) getIntent().getSerializableExtra("NOTIFICATION");

        //Set notification type, time + text
        TextView notificationTypeView = (TextView) findViewById(R.id.notificationTypeText);
        TextView notificationTimeView = (TextView) findViewById(R.id.notificationTime);
        TextView notificationTextView = (TextView) findViewById(R.id.notificationText);

        notificationTextView.setText(notificationIntent.getNotificationText());
        notificationTimeView.setText(notificationIntent.getTimestamp().getHours() + ":" + notificationIntent.getTimestamp().getMinutes());

        // Define backgroundcolor depending on the severity
        View notification_detail_view = findViewById(R.id.notification_detail_view);
        Severity notificationSeverity = notificationIntent.getSeverity();

        if (notificationSeverity.equals(Severity.criticalAlertNoAction) || notificationSeverity.equals(Severity.criticalAlertAction) ||
                notificationSeverity.equals(Severity.criticalAlertTimeToReact)) {
            //Detail view background
            notification_detail_view.setBackgroundColor(Color.parseColor("#FF6347"));

            //Notification text
            notificationTypeView.setText("Alert: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTextView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#EE0000"));
        } else {
            //Detail view background
            notification_detail_view.setBackgroundColor(Color.parseColor("#FFEC8B"));

            //Notification text
            notificationTypeView.setText("Info: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTextView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#FFC125"));
        }

        //Set device name
        TextView deviceNameTextView = (TextView) findViewById(R.id.deviceNameTextView);
        deviceNameTextView.setText(notificationIntent.getDevice().getDeviceName());

        //Set notification reason
        TextView notificationReasonsTextView = (TextView) findViewById(R.id.notificationReasonText2);
        ArrayList<String> notificationReasonList = notificationIntent.getReasons();
        String reasonsString = "";
        for (String reason : notificationReasonList) {
            reasonsString = reasonsString + reason + "\n";
        }
        notificationReasonsTextView.setText(reasonsString);





     //   TextView deviceNameTextView = (TextView) this.findViewById(R.id.TextView02);
     //   deviceNameTextView.setText(notificationIntent.getDeviceName());
     //   TextView deviceIdTextView = (TextView) this.findViewById(R.id.TextView04);
     //   deviceIdTextView.setText(notificationIntent.getDeviceID() + "");
     //   TextView deviceMACTextView = (TextView) this.findViewById(R.id.TextView06);
     //   deviceMACTextView.setText(deviceIntent.getDeviceMAC());
     //   TextView deviceTypeTextView = (TextView) this.findViewById(R.id.TextView08);
        //     deviceTypeTextView.setText(deviceIntent.getDeviceType().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
