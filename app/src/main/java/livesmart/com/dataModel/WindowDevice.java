package livesmart.com.dataModel;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class WindowDevice extends Device {
    private final String iconPath = "ic_cloud_black_18dp";

    public WindowDevice() {}

    public WindowDevice(int deviceID, String deviceName, String deviceMAC, DeviceType deviceType, boolean deviceTurnedOn,
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
