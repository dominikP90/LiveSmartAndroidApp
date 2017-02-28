package livesmart.com.restClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import livesmart.com.dataModel.Room;

/**
 * Created by Dominik Poppek on 24.02.2017.
 */

public class UserPOJO {

    private int id;
    private String userName;
    private String userPassword;
    private List<Room> rooms = new ArrayList<>();
    private String authentificationToken;
    private Timestamp lastUpdate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getAuthentificationToken() {
        return authentificationToken;
    }

    public void setAuthentificationToken(String authentificationToken) {
        this.authentificationToken = authentificationToken;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
