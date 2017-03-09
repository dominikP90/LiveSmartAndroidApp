package livesmart.com.databaseAccess;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dominik Poppek on 22.02.2017.
 */

@Table(name = "devices")
public class DeviceModel extends Model {

    @Column(name="deviceId", unique= true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int deviceID;
    @Column (name="deviceName")
    private String deviceName;
    @Column (name="deviceMAC")
    private String deviceMAC;
    @Column (name="deviceType")
    private String deviceType;
    @Column (name="deviceTurnedOn")
    private boolean deviceTurnedOn;
    @Column (name="deviceSeekerValue")
    private int deviceSeekerValue;

    @Column(name = "RoomModel", onDelete = Column.ForeignKeyAction.CASCADE)
    public RoomModel roomModel;

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
}
