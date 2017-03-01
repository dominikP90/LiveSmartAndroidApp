package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 05.01.2017.
 */

public class StoveDevice extends Device implements Serializable {
    private final String iconPath = "ic_date_range_black_18dp";

    public StoveDevice() {}

    public StoveDevice(int deviceID, String deviceName, String deviceMAC, DeviceType deviceType, boolean deviceTurnedOn,
                       int deviceSeekerValue, String roomName) {
        this.setDeviceID(deviceID);
        this.setDeviceName(deviceName);
        this.setDeviceMAC(deviceMAC);
        this.setDeviceType(deviceType);
        this.setDeviceTurnedOn(deviceTurnedOn);
        this.setDeviceSeekerValue(deviceSeekerValue);
        this.setRoomName(roomName);
    }

    /**
     * Switch hotplate
     * @return
     */
    private void switchHotplate() {
            if (isDeviceTurnedOn()) {
                //TODO Send action to server, if successful switch
                deviceTurnedOn = false;
            } else {
                //TODO Send action to server, if successful switch
                deviceTurnedOn = true;
            }
        }

        /**
         * Switch hotplate
         * @return
         */

    public String getIconPath() {
        return iconPath; }

}
