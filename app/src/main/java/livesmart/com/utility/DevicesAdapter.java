package livesmart.com.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import livesmart.com.dataModel.Device;
import livesmart.com.main.R;

import static livesmart.com.main.R.id.deviceName;

/**
 * Created by Dominik Poppek on 18.12.2016.
 */
public class DevicesAdapter extends ArrayAdapter<Device> {

    private Context context;
    private ArrayList<Device> devices;

    public DevicesAdapter(Context context,int textViewResourceId, ArrayList<Device> devices) {
        super(context, textViewResourceId, devices);
        this.context = context;
        this.devices = devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Device device = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device, parent, false);
        }

        // Lookup view for data population
        TextView deviceNameTextView = (TextView) convertView.findViewById(deviceName);

        // Populate the data into the template view using the data object
        deviceNameTextView.setText(device.getDeviceName());


        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * Get item from the corresponding ArrayList
     * @param position
     * @return
     */
    public Device getItem(int position){
        return devices.get(position);
    }
}
