package livesmart.com.databaseAccess;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import livesmart.com.dataModel.Device;

import static android.R.attr.name;
import static livesmart.com.main.R.id.roomName;

/**
 * Created by Dominik Poppek on 22.02.2017.
 */

@Table(name = "rooms")
public class RoomModel extends Model{

    @Column (name="roomId", unique= true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int roomID;
    @Column (name="roomName")
    private String roomName;
    @Column (name="romm_icon_path")
    private String icon_path;

    public RoomModel() {
        super();
    }

    // Used to return deviceModels from another table based on the foreign key
    public List<DeviceModel> getDeviceModels() {
        return getMany(DeviceModel.class, "RoomModel");

    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }
}
