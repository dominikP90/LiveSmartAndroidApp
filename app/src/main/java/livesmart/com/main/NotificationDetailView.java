package livesmart.com.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Severity;
import livesmart.com.databaseAccess.DeviceModel;
import livesmart.com.restClient.LivesmartWebserviceInterface;
import livesmart.com.restClient.SwitchResponse;
import livesmart.com.utility.UpdateGlobalArrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Dominik Poppek on 18.12.2016.
 */

public class NotificationDetailView extends AppCompatActivity {

    private Notification notificationIntent;
    private Device deviceIntent;
    private SwitchCompat switchButton;
    private SeekBar seekBar;
    private TextView seekBarCurrentValue;

    private LivesmartWebserviceInterface livesmartWebservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification Detail");

        //Notification from Intent
        notificationIntent = (Notification) getIntent().getSerializableExtra("NOTIFICATION");
        deviceIntent = notificationIntent.getDevice();

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
            //notification_detail_view.setBackgroundColor(Color.parseColor("#FF6347"));

            //Notification text
            notificationTypeView.setText("Alert: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTextView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#EE0000"));
        } else {
            //Detail view background
            //notification_detail_view.setBackgroundColor(Color.parseColor("#FFEC8B"));

            //Notification text
            notificationTypeView.setText("Info: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTextView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#FFC125"));
        }

        //Devicename
        TextView deviceNameTextView = (TextView) this.findViewById(R.id.deviceNametextView);
        deviceNameTextView.setPaintFlags(deviceNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        deviceNameTextView.setText(deviceIntent.getDeviceName());

        /** Switch button */
        switchButton = (SwitchCompat) findViewById(R.id.deviceSwitchButton);
        switchButton.setChecked(deviceIntent.isDeviceTurnedOn());
        Log.d("NotificationDetailView", "SwitchbuttonState0: " + switchButton.isChecked());
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NotificationDetailView", "SwitchbuttonState1: " + switchButton.isChecked());
                Log.d("NotificationDetailView", "DeviceState1: " + deviceIntent.isDeviceTurnedOn());
                switchDeviceOnOff(!deviceIntent.isDeviceTurnedOn());
            }
        });

        /** Seekbar */
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(deviceIntent.getDeviceSeekerValue());
        seekBarCurrentValue = (TextView) findViewById(R.id.currentValue);
        seekBarCurrentValue.setText(deviceIntent.getDeviceSeekerValue() + "");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newProgressValue, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("NotificationDetailView", "SeekerValueOnClick: " + seekBar.getProgress());
                changeSeekBarValue(seekBar.getProgress());
            }
        });

        //Set notification reason
        TextView notificationReasonsTextView1 = (TextView) findViewById(R.id.notificationReasonText1);
        notificationReasonsTextView1.setPaintFlags(deviceNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView notificationReasonsTextView2 = (TextView) findViewById(R.id.notificationReasonText2);
        ArrayList<String> notificationReasonList = notificationIntent.getReasons();
        String reasonsString = "";
        for (String reason : notificationReasonList) {
            reasonsString =  reasonsString + "-   " + reason + "\n";
        }
        notificationReasonsTextView2.setText(reasonsString);

        TextView advancedDetailsTextView = (TextView) findViewById(R.id.notificationAdvanced);
        advancedDetailsTextView.setPaintFlags(advancedDetailsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    /**
     * Opens advancedNotificationDetailsView
     */
    public void advancedNotifactionDetails(View view) {
        Intent intent = new Intent(getApplicationContext(), AdvancedNotificationDetailsView.class);
        intent.putExtra("NOTIFICATION", notificationIntent);
        startActivity(intent);
    }

    /**
     * Calling webservice to change seeker value on the device
     * On success change and write to database
     * @param newProgressValue
     */
    private void changeSeekBarValue(final int newProgressValue) {
        Call<SwitchResponse> callSeekerDevice = livesmartWebservice.changeSeekerValueDeviceById(deviceIntent.getDeviceID(), newProgressValue);

        callSeekerDevice.enqueue(new Callback<SwitchResponse>() {
            @Override
            public void onResponse(Call<SwitchResponse> call, Response<SwitchResponse> response) {
                Log.d("NotificationDetailView", "Successfull seekBar call!");
                if (response.code() == 200) {
                    //update device in db
                    Log.d("NotificationDetailView", "SeekerOnSuccess1: " + seekBar.getProgress());
                    seekBar.setProgress(newProgressValue);
                    seekBarCurrentValue.setText(newProgressValue + "");
                    DeviceModel dm = new Select().from(DeviceModel.class).where("deviceId = ?", deviceIntent.getDeviceID()).executeSingle();
                    dm.setDeviceSeekerValue(newProgressValue);
                    dm.save();
                    deviceIntent.setDeviceSeekerValue(newProgressValue);
                    Log.d("NotificationDetailView", "SeekerOnSuccess1: " + seekBar.getProgress());
                    Log.d("NotificationDetailView", "DeviceState1: " + deviceIntent.getDeviceSeekerValue());
                    //Update global type + rooms list
                    UpdateGlobalArrays.updateGlobalArrayListsForSeeker(deviceIntent, newProgressValue);
                    Log.d("RoomsDeviceDetailView", "Global lists updated!");
                } else {
                    seekBar.setProgress(deviceIntent.getDeviceSeekerValue());
                    Log.d("NotificationDetailView", "SwitchbuttonState2-ServerError: " + switchButton.isChecked());
                    Toast.makeText(NotificationDetailView.this,
                            "Server-Error: Slider wasn't changed!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(NotificationDetailView.this,
                        "Server-Error: Slider wasn't changed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Calling webservice to switch on/off the device
     * On success switch and write to database
     * @param newState
     */
    private void switchDeviceOnOff(final boolean newState) {
        Call<SwitchResponse> callSwitchDevice = livesmartWebservice.switchOnOffDeviceById(deviceIntent.getDeviceID(), newState);

        callSwitchDevice.enqueue(new Callback<SwitchResponse>() {
            @Override
            public void onResponse(Call<SwitchResponse> call, Response<SwitchResponse> response) {
                Log.d("NotificationDetailView", "switchOnOffDevice() successful");
                /** Check if server-side was successful */
                if(response.code() == 200) {
                    //update device in db
                    Log.d("NotificationDetailView", "SwitchbuttonState2: " + switchButton.isChecked());
                    switchButton.setChecked(newState);
                    DeviceModel dm = new Select().from(DeviceModel.class).where("deviceId = ?", deviceIntent.getDeviceID()).executeSingle();
                    dm.setDeviceTurnedOn(newState);
                    dm.save();
                    deviceIntent.setDeviceTurnedOn(newState);
                    Log.d("NotificationDetailView", "SwitchbuttonState3: " + switchButton.isChecked());
                    Log.d("NotificationDetailView", "DeviceState1: " + deviceIntent.isDeviceTurnedOn());
                    //Update global type + rooms list
                    UpdateGlobalArrays.updateGlobalArrayListsForSwitch(deviceIntent, newState);
                    Log.d("NotificationDetailView", "Global lists updated!");
                } else {
                    //   seekbar.set
                    switchButton.setChecked(!newState);
                    Log.d("NotificationDetailView", "SwitchbuttonState2-ServerError: " + switchButton.isChecked());
                    if (newState == true) {
                        Toast.makeText(NotificationDetailView.this,
                                "Server-Error: Device could not turned on!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NotificationDetailView.this,
                                "Server-Error: Device could not turned off!",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
