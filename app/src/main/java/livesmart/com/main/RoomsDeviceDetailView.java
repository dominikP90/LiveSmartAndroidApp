package livesmart.com.main;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Room;

/**
 * Created by Dominik Poppek on 19.12.2016.
 */

public class RoomsDeviceDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO Seekbar implementation if needed (see: Lesezeichen Firefox)

        //TODO Switch between different device views
        // if (DeviceType... || DeviceType...) {
        // set...visible(true/false)

        //Device from RoomDevicesView
        Device deviceIntent = (Device) getIntent().getSerializableExtra("DEVICE");

        //Set Actionbar title
        getSupportActionBar().setTitle("Device Detail");

        //TODO Switch between different device views
        //Devicename
        TextView deviceNameTextView = (TextView) this.findViewById(R.id.deviceNametextView);
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
