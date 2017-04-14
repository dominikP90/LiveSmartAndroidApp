package livesmart.com.utility;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Room;
import livesmart.com.dataModel.TypeOverview;

import static livesmart.com.main.LiveSmartMain.rooms;
import static livesmart.com.main.LiveSmartMain.types;

/**
 * Created by Dominik Poppek on 11.03.2017.
 */

public class UpdateGlobalArrays {

    /**
     * Update global ArrayLists rooms + types for switch action
     * @param deviceIntent
     * @param newState
     */
    public static void updateGlobalArrayListsForSwitch(Device deviceIntent, boolean newState) {
        //Types
        if(deviceIntent.getDeviceType().equals("AlarmDevice")) {
            TypeOverview alarms = types.get(0);
            for (Device d : alarms.getDeviceList()) {
                if(d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        } else if(deviceIntent.getDeviceType().equals("CameraDevice")) {
            TypeOverview alarms = types.get(1);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("DoorDevice")) {
            TypeOverview alarms = types.get(2);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("HeatingDevice")) {
            TypeOverview alarms = types.get(3);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("LightingDevice")) {
            TypeOverview alarms = types.get(4);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("MusicDevice")) {
            TypeOverview alarms = types.get(5);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("StoveDevice")) {
            TypeOverview alarms = types.get(6);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("WindowDevice")) {
            TypeOverview alarms = types.get(7);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceTurnedOn(newState);
                }
            }
        }
        //Rooms
        for (Room r : rooms) {
            if (r.getRoomName().equals(deviceIntent.getRoomName())) {
                for (Device d : r.getDeviceList()) {
                    if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                        d.setDeviceTurnedOn(newState);
                    }
                }
            }
        }
    }

    /**
     * Update global ArrayLists rooms + types for seekBar action
     * @param deviceIntent
     * @param newProgressValue
     */
    public static void updateGlobalArrayListsForSeeker(Device deviceIntent, int newProgressValue) {
        //Types
        if(deviceIntent.getDeviceType().equals("AlarmDevice")) {
            TypeOverview alarms = types.get(0);
            for (Device d : alarms.getDeviceList()) {
                if(d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        } else if(deviceIntent.getDeviceType().equals("CameraDevice")) {
            TypeOverview alarms = types.get(1);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("DoorDevice")) {
            TypeOverview alarms = types.get(2);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("HeatingDevice")) {
            TypeOverview alarms = types.get(3);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("LightingDevice")) {
            TypeOverview alarms = types.get(4);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("MusicDevice")) {
            TypeOverview alarms = types.get(5);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("StoveDevice")) {
            TypeOverview alarms = types.get(6);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }else if(deviceIntent.getDeviceType().equals("WindowDevice")) {
            TypeOverview alarms = types.get(7);
            for (Device d : alarms.getDeviceList()) {
                if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                    d.setDeviceSeekerValue(newProgressValue);
                }
            }
        }
        //Rooms
        for (Room r : rooms) {
            if (r.getRoomName().equals(deviceIntent.getRoomName())) {
                for (Device d : r.getDeviceList()) {
                    if (d.getDeviceID() == deviceIntent.getDeviceID()) {
                        d.setDeviceSeekerValue(newProgressValue);
                    }
                }
            }
        }
    }
}
