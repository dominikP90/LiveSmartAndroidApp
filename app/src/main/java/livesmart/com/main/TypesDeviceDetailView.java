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

public class TypesDeviceDetailView extends AppCompatActivity{

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
        getSupportActionBar().setTitle("Device Detail");

        //Setup webservice
        livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);

        //Device from RoomDevicesView
        deviceIntent = (Device) getIntent().getSerializableExtra("DEVICE");

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
        Log.d("TypesDeviceDetailView", "SwitchbuttonState0: " + switchButton.isChecked());
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TypesDeviceDetailView", "SwitchbuttonState1: " + switchButton.isChecked());
                Log.d("TypesDeviceDetailView", "DeviceState1: " + deviceIntent.isDeviceTurnedOn());
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
                    Toast.makeText(TypesDeviceDetailView.this,
                            "Server-Error: Slider wasn't changed!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TypesDeviceDetailView.this,
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
                Log.d("TypesDeviceDetailView", "switchOnOffDevice() successful");
                /** Check if server-side was successful */
                if(response.code() == 200) {
                    //update device in db
                    Log.d("TypesDeviceDetailView", "SwitchbuttonState2: " + switchButton.isChecked());
                    switchButton.setChecked(newState);
                    DeviceModel dm = new Select().from(DeviceModel.class).where("deviceId = ?", deviceIntent.getDeviceID()).executeSingle();
                    dm.setDeviceTurnedOn(newState);
                    dm.save();
                    deviceIntent.setDeviceTurnedOn(newState);
                    Log.d("TypesDeviceDetailView", "SwitchbuttonState3: " + switchButton.isChecked());
                    Log.d("TypesDeviceDetailView", "DeviceState1: " + deviceIntent.isDeviceTurnedOn());
                    //Update global type + rooms list
                    updateGlobalArrayLists(newState);
                    Log.d("TypesDeviceDetailView", "Global lists updated!");
                } else {
                    //   seekbar.set
                    switchButton.setChecked(!newState);
                    Log.d("TypesDeviceDetailView", "SwitchbuttonState2-ServerError: " + switchButton.isChecked());
                    if (newState == true) {
                        Toast.makeText(TypesDeviceDetailView.this,
                                "Server-Error: Device could not turned on!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TypesDeviceDetailView.this,
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

    private void changeDeviceSeekerValue(int newValue) {
        LivesmartWebserviceInterface livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);
        Call<SwitchResponse> callSwitchDevice = livesmartWebservice.changeSeekerValueDeviceById(deviceIntent.getDeviceID(), newValue);

        callSwitchDevice.enqueue(new Callback<SwitchResponse>() {

            @Override
            public void onResponse(Call<SwitchResponse> call, Response<SwitchResponse> response) {
            }

            @Override
            public void onFailure(Call<SwitchResponse> call, Throwable t) {

            }
        });
    }


    /**
     * When device gets updated, populate update to global lists rooms, types
     * @param newState
     */
    private void updateGlobalArrayLists(boolean newState) {
        //Types
        if(deviceIntent.getDeviceType().equals("AlarmDevice")) {
            TypeOverview alarms = types.get(0);
            for (Device d : alarms.getDeviceList()) {
                if(d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        } else if(deviceIntent.getDeviceType().equals("CameraDevice")) {
            TypeOverview alarms = types.get(1);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("DoorDevice")) {
            TypeOverview alarms = types.get(2);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("HeatingDevice")) {
            TypeOverview alarms = types.get(3);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("LightningDevice")) {
            TypeOverview alarms = types.get(4);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("MusicDevice")) {
            TypeOverview alarms = types.get(5);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("StoveDevice")) {
            TypeOverview alarms = types.get(6);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("WindowDevice")) {
            TypeOverview alarms = types.get(7);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }
        //Rooms
        for (Room r : rooms) {
            if (r.getRoomName().equals(deviceIntent.getRoomName())) {
                for (Device d : r.getDeviceList()) {
                    if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                        d.setDeviceTurnedOn(newState);
                    }
                }
            }
        }
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
