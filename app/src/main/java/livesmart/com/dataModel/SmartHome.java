package livesmart.com.dataModel;

import java.util.ArrayList;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class SmartHome {
    private static SmartHome smartHomeInstance;

    private String smartHomeName;
    private int smartHomeID;
    private String serverURL;
    private ArrayList<Room> smartHomeRooms;

    private SmartHome (String smartHomeName, int smartHomeID, String serverURL) {
        this.smartHomeName = smartHomeName;
        this.smartHomeID = smartHomeID;
        this.serverURL = serverURL;
    }


    /**
     * Makes sure that only one instance of the SmartHome exists. If the user uses application for first time,
     * the data will be safed in a file. Afterwards the data to create the singleton will be loaded from file.
     *
     * @return SmartHome
     */
    public static SmartHome getInstance() {
        if (smartHomeInstance == null) {
            //TODO At first, create new Instance and write to file(?), then load it
            //Check if first time (read file?), if yes return null!
            //Load Smarthome from file?
            smartHomeInstance = new SmartHome("MyTestSmartHome", 1, "https://testHome.com");
            //TODO Load data (rooms, devices) from db

           }
        return smartHomeInstance;
    }


    /**
     * @return the smartHomeName
     */
    public String getSmartHomeName() {
        return smartHomeName;
    }
    /**
     * @param smartHomeName the smartHomeName to set
     */
    public void setSmartHomeName(String smartHomeName) {
        this.smartHomeName = smartHomeName;
    }
    /**
     * @return the smartHomeID
     */
    public int getSmartHomeID() {
        return smartHomeID;
    }
    /**
     * @param smartHomeID the smartHomeID to set
     */
    public void setSmartHomeID(int smartHomeID) {
        this.smartHomeID = smartHomeID;
    }
    /**
     * @return the serverURL
     */
    public String getServerURL() {
        return serverURL;
    }
    /**
     * @param serverURL the serverURL to set
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }


    /**
     * @return the smartHomeRooms
     */
    public ArrayList<Room> getSmartHomeRooms() {
        return smartHomeRooms;
    }


    /**
     * @param smartHomeRooms the smartHomeRooms to set
     */
    public void setSmartHomeRooms(ArrayList<Room> smartHomeRooms) {
        this.smartHomeRooms = smartHomeRooms;
    }

}
