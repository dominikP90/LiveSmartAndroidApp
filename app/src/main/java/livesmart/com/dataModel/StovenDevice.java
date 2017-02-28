package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 05.01.2017.
 */

public class StovenDevice extends Device implements Serializable {
    private final String iconPath = "ic_date_range_black_18dp";

    /**
     * Switch hotplate
     * @return
     */
    private void switchHotplate() {
            if (isDeviceTurnedOn()) {
                //TODO Send action to server, if successful switch
                deviceTurnedOn = false;
            } else {
                //TODO Send action to server, if successful switch
                deviceTurnedOn = true;
            }
        }

        /**
         * Switch hotplate
         * @return
         */

    public String getIconPath() {
        return iconPath; }

}
