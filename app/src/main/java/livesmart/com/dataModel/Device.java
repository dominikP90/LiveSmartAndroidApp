package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class Device implements Serializable {

    private int deviceID;
    private String deviceName;
    private String deviceMAC;
    private DeviceType deviceType;
    public boolean deviceTurnedOn;
    private int deviceSeekerValue;
    private String roomName;

    /**
     * @return the deviceID
     */
    public int getDeviceID() {
        return deviceID;
    }
    /**
     * @param deviceID the deviceID to set
     */
    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }
    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }
    /**
     * @param deviceName the deviceName to set
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    /**
     * @return the deviceMAC
     */
    public String getDeviceMAC() {
        return deviceMAC;
    }
    /**
     * @param deviceMAC the deviceMAC to set
     */
    public void setDeviceMAC(String deviceMAC) {
        this.deviceMAC = deviceMAC;
    }
    /**
     * @return the deviceType
     */
    public DeviceType getDeviceType() {
        return deviceType;
    }
    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isDeviceTurnedOn() {
        return deviceTurnedOn;
    }

    public void setDeviceTurnedOn(boolean deviceTurnedOn) {
        this.deviceTurnedOn = deviceTurnedOn;
    }

    public int getDeviceSeekerValue() {
        return deviceSeekerValue;
    }

    public void setDeviceSeekerValue(int deviceSeekerValue) {
        this.deviceSeekerValue = deviceSeekerValue;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
