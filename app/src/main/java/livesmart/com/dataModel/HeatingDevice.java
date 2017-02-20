package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class HeatingDevice extends Device implements Serializable{
    private final String iconPath = "ic_brightness_low_black_18dp";
    private int currentTemp;


    /**
     * @return the currentTemp
     */
    public int getCurrentTemp() {
        return currentTemp;
    }
    /**
     * @param currentTemp the currentTemp to set
     */
    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getIconPath() {
        return iconPath;
    }
}
