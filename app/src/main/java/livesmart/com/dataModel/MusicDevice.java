package livesmart.com.dataModel;

import java.io.Serializable;
import java.util.MissingFormatArgumentException;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class MusicDevice extends Device implements Serializable {
    private final String iconPath = "ic_queue_music_black_18dp";

    public MusicDevice() {}

    public MusicDevice(int deviceID, String deviceName, String deviceMAC, DeviceType deviceType, boolean deviceTurnedOn,
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
