package livesmart.com.main;

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

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Room;
import livesmart.com.dataModel.TypeOverview;
import livesmart.com.databaseAccess.DeviceModel;
import livesmart.com.restClient.LivesmartWebserviceInterface;
import livesmart.com.restClient.SwitchResponse;
import livesmart.com.utility.UpdateGlobalArrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static livesmart.com.main.LiveSmartMain.rooms;
import static livesmart.com.main.LiveSmartMain.types;
import static livesmart.com.main.MainActivity.retrofit;

/**
 * Created by Dominik Poppek on 19.12.2016.
 */

public class RoomsDeviceDetailView extends AppCompatActivity {

    private Device deviceIntent;
    private SwitchCompat switchButton;
    private SeekBar seekBar;
    private TextView seekBarCurrentValue;

    private LivesmartWebserviceInterface livesmartWebservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setup webservice
        livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);

        //TODO Switch between different device views
        // if (DeviceType... || DeviceType...) {
        // set...visible(true/false)

        //Device from RoomDevicesView
        deviceIntent = (Device) getIntent().getSerializableExtra("DEVICE");

        //Set Actionbar title
        getSupportActionBar().setTitle("Device Detail");

        //TODO Switch between different device views
        //Devicename
        TextView deviceNameTextView = (TextView) this.findViewById(R.id.deviceNametextView);
        deviceNameTextView.setPaintFlags(deviceNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        deviceNameTextView.setText(deviceIntent.getDeviceName());

        //Devicetype
        TextView deviceTypeTextView = (TextView) this.findViewById(R.id.detailtable02);
        deviceTypeTextView.setText(deviceIntent.getDeviceType().toString());

        //Deviceroom
        TextView deviceRoomTextView = (TextView) this.findViewById(R.id.detailtable04);
        deviceRoomTextView.setText(deviceIntent.getRoomName());

        //DeviceID
        TextView deviceIdTextView = (TextView) this.findViewById(R.id.detailtable06);
        deviceIdTextView.setText(deviceIntent.getDeviceID() + "");

        //DeviceMAC
        TextView deviceMACTextView = (TextView) this.findViewById(R.id.detailtable08);
        deviceMACTextView.setText(deviceIntent.getDeviceMAC());

        /** Switch button */
        switchButton = (SwitchCompat) findViewById(R.id.deviceSwitchButton);
        switchButton.setChecked(deviceIntent.isDeviceTurnedOn());
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchDeviceOnOff(!deviceIntent.isDeviceTurnedOn());
            }
        });

        /** Seekbar */
        seekBar = (SeekBar)findViewById(R.id.seekBar);
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
                Log.d("RoomsDeviceDetailView", "SeekerValueOnClick: " + seekBar.getProgress());
                changeSeekBarValue(seekBar.getProgress());
            }
        });
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
                Log.d("RoomsDeviceDetailView", "Successfull seekBar call!");
                if (response.code() == 200) {
                    //update device in db
                    Log.d("RoomsDeviceDetailView", "SeekerOnSuccess1: " + seekBar.getProgress());
                    seekBar.setProgress(newProgressValue);
                    seekBarCurrentValue.setText(newProgressValue + "");
                    DeviceModel dm = new Select().from(DeviceModel.class).where("deviceId = ?", deviceIntent.getDeviceID()).executeSingle();
                    dm.setDeviceSeekerValue(newProgressValue);
                    dm.save();
                    deviceIntent.setDeviceSeekerValue(newProgressValue);
                    Log.d("RoomsDeviceDetailView", "SeekerOnSuccess1: " + seekBar.getProgress());
                    Log.d("RoomsDeviceDetailView", "DeviceState1: " + deviceIntent.getDeviceSeekerValue());
                    //Update global type + rooms list
                    UpdateGlobalArrays.updateGlobalArrayListsForSeeker(deviceIntent, newProgressValue);
                    Log.d("RoomsDeviceDetailView", "Global lists updated!");
                } else {
                    seekBar.setProgress(deviceIntent.getDeviceSeekerValue());
                    Log.d("RoomsDeviceDetailView", "SwitchbuttonState2-ServerError: " + switchButton.isChecked());
                    Toast.makeText(RoomsDeviceDetailView.this,
                            "Server-Error: Slider wasn't changed!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RoomsDeviceDetailView.this,
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
                Log.d("RoomsDeviceDetailView", "switchOnOffDevice() successful");
                /** Check if server-side was successful */
                if(response.code() == 200) {
                    //update device in db
                    Log.d("RoomsDeviceDetailView", "SwitchbuttonState2: " + switchButton.isChecked());
                    switchButton.setChecked(newState);
                    DeviceModel dm = new Select().from(DeviceModel.class).where("deviceId = ?", deviceIntent.getDeviceID()).executeSingle();
                    dm.setDeviceTurnedOn(newState);
                    dm.save();
                    deviceIntent.setDeviceTurnedOn(newState);
                    Log.d("RoomsDeviceDetailView", "SwitchbuttonState3: " + switchButton.isChecked());
                    Log.d("RoomsDeviceDetailView", "DeviceState1: " + deviceIntent.isDeviceTurnedOn());
                    //Update global type + rooms list
                    UpdateGlobalArrays.updateGlobalArrayListsForSwitch(deviceIntent, newState);
                    Log.d("RoomsDeviceDetailView", "Global lists updated!");
                } else {
                    switchButton.setChecked(!newState);
                    Log.d("RoomsDeviceDetailView", "SwitchbuttonState2-ServerError: " + switchButton.isChecked());
                    if (newState == true) {
                        Toast.makeText(RoomsDeviceDetailView.this,
                                "Server-Error: Device could not turned on!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RoomsDeviceDetailView.this,
                                "Server-Error: Device could not turned off!",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {
                t.printStackTrace();
                if (newState == true) {
                    Toast.makeText(RoomsDeviceDetailView.this,
                            "Server-Error: Device could not turned on!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RoomsDeviceDetailView.this,
                            "Server-Error: Device could not turned off!",Toast.LENGTH_SHORT).show();
                }
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
