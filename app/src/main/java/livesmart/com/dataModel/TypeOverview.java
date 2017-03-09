package livesmart.com.dataModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dominik Poppek on 06.01.2017.
 */
public class TypeOverview implements Serializable {

    private int typesId;
    private String typesName;
    private String icon_path;
    private ArrayList<Device> deviceList = new ArrayList<>();

    /**
     * Constructor
     * @param typesId
     * @param typesName
     */
    public TypeOverview(int typesId, String typesName, String icon_path) {
        this.typesId = typesId;
        this.typesName = typesName;
        this.icon_path = icon_path;
    }

    /**
     * Constructor
     * @param typesId
     * @param typesName
     */
    public TypeOverview(int typesId, String typesName) {
        this.typesId = typesId;
        this.typesName = typesName;
    }

    public String getTypesName() {
        return typesName;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public int getTypesId() {
        return typesId;
    }

    public void setTypesId(int typesId) {
        this.typesId = typesId;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }
}
