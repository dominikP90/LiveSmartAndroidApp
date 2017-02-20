package livesmart.com.dataModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class Room implements Serializable{

    private int roomID;
    private String roomName;
    private ArrayList<Device> deviceList = new ArrayList<>();
    private String icon_path;

    /**
     * Constructor
     * @param roomID
     * @param roomName
     * @param icon_path
     */
    public Room(int roomID, String roomName, String icon_path) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.icon_path = icon_path;
    }


    /**
     * @return the roomID
     */
    public int getRoomID() {
        return roomID;
    }
    /**
     * @param roomID the roomID to set
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    /**
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }
    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    /**
     * @return the deviceList
     */
    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }
    /**
     * @param deviceList the deviceList to set
     */
    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }
}
