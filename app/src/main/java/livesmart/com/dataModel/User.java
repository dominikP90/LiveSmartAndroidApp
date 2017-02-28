package livesmart.com.dataModel;

import java.sql.Timestamp;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class User {
    private int id;
    private String userName;
    private String userPassword;
    private String authentificationToken;
    private Timestamp lastUpdate;
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the authentificationToken
     */
    public String getAuthentificationToken() {
        return authentificationToken;
    }

    /**
     * @param authentificationToken the authentificationToken to set
     */
    public void setAuthentificationToken(String authentificationToken) {
        this.authentificationToken = authentificationToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
