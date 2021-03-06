package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class HeatingDevice extends Device implements Serializable{
    private final String iconPath = "ic_brightness_low_black_18dp";

    public HeatingDevice() {}

    public HeatingDevice(int deviceID, String deviceName, String deviceMAC, DeviceType deviceType, boolean deviceTurnedOn,
                         int deviceSeekerValue, String roomName) {
        this.setDeviceID(deviceID);
        this.setDeviceName(deviceName);
        this.setDeviceMAC(deviceMAC);
        this.setDeviceType(deviceType);
        this.setDeviceTurnedOn(deviceTurnedOn);
        this.setDeviceSeekerValue(deviceSeekerValue);
        this.setRoomName(roomName);
    }


    public String getIconPath() {
        return iconPath;
    }
}
