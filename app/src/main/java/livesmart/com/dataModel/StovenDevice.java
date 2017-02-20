package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 05.01.2017.
 */

public class StovenDevice extends Device implements Serializable {
    private final String iconPath = "ic_date_range_black_18dp";

    private boolean hotplateTurnedOn;
    private boolean stoveTurnedOn;

    /**
     * Switch hotplate
     * @return
     */
    private void switchHotplate() {
            if (hotplateTurnedOn) {
                //TODO Send action to server, if successful switch
                hotplateTurnedOn = false;
            } else {
                //TODO Send action to server, if successful switch
                hotplateTurnedOn = true;
            }
        }

        /**
         * Switch hotplate
         * @return
         */
    private void switchStoven() {

        if (stoveTurnedOn) {
            //TODO Send action to server, if successful switch
            stoveTurnedOn = false;
        } else {
            //TODO Send action to server, if successful switch
            stoveTurnedOn = true;
        }
    }

    public String getIconPath() {
        return iconPath; }

    public boolean isHotplateTurnedOn() {
        return hotplateTurnedOn;
    }

    public void setHotplateTurnedOn(boolean hotplateTurnedOn) {
        this.hotplateTurnedOn = hotplateTurnedOn;
    }

    public boolean isStoveTurnedOn() {
        return stoveTurnedOn;
    }

    public void setStoveTurnedOn(boolean stoveTurnedOn) {
        this.stoveTurnedOn = stoveTurnedOn;
    }
}
