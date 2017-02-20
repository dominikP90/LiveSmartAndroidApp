package livesmart.com.messaging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Dominik Poppek on 28.01.2017.
 */

public class JSONNotificationData {

    @SerializedName("notificationID")
    @Expose
    private int notificationID;
    @SerializedName("notificationText")
    @Expose
    private String notificationText;
    @SerializedName("timestamp")
    @Expose
    private Date timestamp;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("device")
    @Expose
    private int deviceId;
    @SerializedName("reasons")
    @Expose
    private List<String> reasons = null;

    public Integer getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Integer notificationID) {
        this.notificationID = notificationID;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }


    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}