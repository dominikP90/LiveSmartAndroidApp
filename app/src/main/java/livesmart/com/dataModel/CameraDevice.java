package livesmart.com.dataModel;

import java.io.Serializable;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class CameraDevice extends Device implements Serializable {
    private final String iconPath = "ic_videocam_black_18dp";

    public String getIconPath() {
        return iconPath;
    }
}
