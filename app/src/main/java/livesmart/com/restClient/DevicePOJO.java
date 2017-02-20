package livesmart.com.restClient;

import livesmart.com.dataModel.DeviceType;

/**
 * Created by Dominik Poppek on 30.01.2017.
 */

public class DevicePOJO {

    private int deviceID;
    private String deviceName;
    private String deviceMAC;
    private String deviceType;
    private String roomName;
    private boolean hotplateTurnedOn;
    private boolean stoveTurnedOn;
    private int currentTemp;

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMAC() {
        return deviceMAC;
    }

    public void setDeviceMAC(String deviceMAC) {
        this.deviceMAC = deviceMAC;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isHotplateTurnedOn() {
        return hotplateTurnedOn;
    }

    public void setHotplateTurnedOn(boolean hotplateTurnedOn) {
        this.hotplateTurnedOn = hotplateTurnedOn;
    }

    public boolean isStoveTurnedOn() {
        return stoveTurnedOn;
    }

    public void setStoveTurnedOn(boolean stoveTurnedOn) {
        this.stoveTurnedOn = stoveTurnedOn;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }
}
