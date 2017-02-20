package livesmart.com.dataModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */
public class Notification implements Serializable {

    private int notificationID;
    private String notificationText;
    private Date timestamp;
    private Severity severity;
    private Device device;
    private ArrayList<String> reasons = new ArrayList<>();

    /**
     * @return the notificationID
     */
    public int getNotificationID() {
        return notificationID;
    }
    /**
     * @param notificationID the notificationID to set
     */
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
    /**
     * @return the notificationText
     */
    public String getNotificationText() {
        return notificationText;
    }
    /**
     * @param notificationText the notificationText to set
     */
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
    /**
     * @return the severity
     */
    public Severity getSeverity() {
        return severity;
    }
    /**
     * @param severity the severity to set
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    /**
     * @return the device
     */
    public Device getDevice() {
        return device;
    }
    /**
     * @param device the device to set
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getReasons() {
        return reasons;
    }

    public void setReasons(ArrayList<String> reasons) {
        this.reasons = reasons;
    }
}
