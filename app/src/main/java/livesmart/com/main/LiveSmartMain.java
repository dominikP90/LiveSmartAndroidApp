package livesmart.com.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.DeviceType;
import livesmart.com.dataModel.HeatingDevice;
import livesmart.com.dataModel.LightningDevice;
import livesmart.com.dataModel.MusicDevice;
import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Room;
import livesmart.com.dataModel.Severity;
import livesmart.com.dataModel.StovenDevice;
import livesmart.com.dataModel.TypeOverview;
import livesmart.com.databaseAccess.DeviceModel;
import livesmart.com.databaseAccess.RoomModel;
import livesmart.com.fragments.NotificationsFragment;
import livesmart.com.fragments.RoomsFragment;
import livesmart.com.fragments.TypesFragment;
import livesmart.com.restClient.LivesmartWebserviceInterface;
import livesmart.com.restClient.UserPOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static livesmart.com.main.MainActivity.retrofit;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class LiveSmartMain extends AppCompatActivity{

    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<TypeOverview> types = new ArrayList<>();
    public static ArrayList<Notification> notifications = new ArrayList<>();
    public static boolean showNotificationTab = false;
    private boolean isFirstLogin;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_home_view);

        isFirstLogin = (boolean) getIntent().getSerializableExtra("FIRSTLOGIN");
        if (isFirstLogin) {
            loadUserdataFirstTime();
        } else {
            //TODO load data from db
        }

        /** Testdata */
        setUpTestData();

        /** Code for tabs layout */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Requests userdata from webservice and saves it into database
     */
    private void loadUserdataFirstTime() {
            SharedPreferences prefs = getSharedPreferences("livesmart.com", Context.MODE_PRIVATE);
            int userIdSharedPref = prefs.getInt("livesmart.com.userId", -1);

            //Load userdata from webservice
            LivesmartWebserviceInterface livesmartWebservice = retrofit.create(LivesmartWebserviceInterface.class);

            Call<UserPOJO> callGetUserDataById = livesmartWebservice.getUserbyUserId(userIdSharedPref);

            callGetUserDataById.enqueue(new Callback<UserPOJO>() {
                @Override
                public void onResponse(Call<UserPOJO> call, Response<UserPOJO> response) {
                    UserPOJO callResponse = response.body();
                    //Write userData into database
                    // writeUserdataToDatabase(callResponse);
                }

                @Override
                public void onFailure(Call<UserPOJO> call, Throwable t) {
                    t.printStackTrace();
                }
            });
    }

    /**
     * Writes userData into database
     * @param userData
     */
    private void writeUserdataToDatabase(UserPOJO userData) {
        for (Room room : userData.getRooms()) {
            //Save room
            RoomModel rm = new RoomModel();
            rm.setRoomID(room.getRoomID());
            rm.setRoomName(room.getRoomName());
            rm.setIcon_path(room.getIcon_path());
            rm.save();
            //Save devices for room
            for (Device device : room.getDeviceList()) {
                DeviceModel dm = new DeviceModel();
                dm.setDeviceID(device.getDeviceID());
                dm.setDeviceName(device.getDeviceName());
                dm.setDeviceMAC(device.getDeviceMAC());
                dm.setDeviceType(device.getClass().toString());
                dm.setDeviceTurnedOn(device.isDeviceTurnedOn());
                dm.setDeviceSeekerValue(device.getDeviceSeekerValue());
                dm.roomModel = rm;
                dm.save();
            }

        }
    }

    /**
     * ViewPager for swiping between tabs
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RoomsFragment(), "Rooms");
        adapter.addFragment(new TypesFragment(), "Types");
        adapter.addFragment(new NotificationsFragment(), "Notifications");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showNotificationTab) {
            viewPager.setCurrentItem(2);
            tabLayout.setupWithViewPager(viewPager);
            showNotificationTab = false;
        }

    }



    private void setUpTestData() {
        /** Rooms **/
        Room bathroom = new Room(1, "Bathroom", "ic_bathroom_black_18dp");
        Room kitchen = new Room(4, "Kitchen", "ic_local_cafe_black_18dp");

        /** Devices */
        HeatingDevice h1 = new HeatingDevice();
        h1.setDeviceID(1);
        h1.setDeviceMAC("00:80:41:ae:fd:7e");
        h1.setDeviceName("Bathroom Heating");
        h1.setDeviceType(DeviceType.HEATING);
        h1.setRoomName(bathroom.getRoomName());
        h1.setCurrentTemp(26);

        HeatingDevice h2 = new HeatingDevice();
        h2.setDeviceID(2);
        h2.setDeviceMAC("00:80:41:ae:fd:7e");
        h2.setDeviceName("Kitchen Heating");
        h2.setDeviceType(DeviceType.HEATING);
        h2.setCurrentTemp(26);
        h2.setRoomName(kitchen.getRoomName());

        LightningDevice l1 = new LightningDevice();
        l1.setDeviceID(3);
        l1.setDeviceMAC("00:80:41:ae:fd:7e");
        l1.setDeviceName("Bathroom Lightning");
        l1.setDeviceType(DeviceType.LIGHTNING);
        l1.setRoomName(bathroom.getRoomName());

        MusicDevice m1 = new MusicDevice();
        m1.setDeviceID(4);
        m1.setDeviceMAC("00:80:41:ae:fd:7e");
        m1.setDeviceName("Bathroom Music");
        m1.setDeviceType(DeviceType.MUSIC);
        m1.setRoomName(bathroom.getRoomName());

        StovenDevice s1 = new StovenDevice();
        s1.setDeviceID(1);
        s1.setDeviceMAC("00:80:41:ae:fd:7e");
        s1.setDeviceName("Kitchen Stoven");
        s1.setDeviceType(DeviceType.STOVEN);
        s1.setRoomName(kitchen.getRoomName());
        s1.setDeviceTurnedOn(true);
        s1.setDeviceSeekerValue(70);


        /** Bathroom add devices */
        ArrayList<Device> bathroomDevices = new ArrayList<Device>();
        bathroomDevices.add(h1);
        bathroomDevices.add(l1);
        bathroomDevices.add(m1);
        bathroom.setDeviceList(bathroomDevices);

        /** Kitchen add Devices*/
        ArrayList<Device> kitchenDevices = new ArrayList<Device>();
        kitchenDevices.add(s1);
        kitchenDevices.add(l1);
        kitchenDevices.add(m1);
        kitchen.setDeviceList(kitchenDevices);

        /** Create rooms list */
        rooms = new ArrayList<Room>();

        rooms.add(bathroom);
        rooms.add(new Room(2, "Bedroom", "ic_hotel_black_18dp"));
        rooms.add(new Room(3, "Dining Room", "ic_restaurant_black_18dp"));
        rooms.add(kitchen);
        rooms.add(new Room(5, "Library", "ic_filter_drama_black_18dp"));
        rooms.add(new Room(6, "Toilette", "ic_wc_black_18dp"));

        /** Heating Devices*/
        TypeOverview heating = new TypeOverview(4, "Heatingdevices");
        ArrayList<Device> heatingDevices = new ArrayList<>();
        heatingDevices.add(h1);
        heatingDevices.add(h2);
        heating.setDeviceList(heatingDevices);

        /** Stoven Devices*/
        TypeOverview stoven = new TypeOverview(7, "Stovendevices");
        ArrayList<Device> stovenDevices = new ArrayList<>();
        stovenDevices.add(s1);
        stoven.setDeviceList(stovenDevices);

        /** Create Types Devices list*/
        types = new ArrayList<TypeOverview>();
        types.add(new TypeOverview(1, "Alarmdevices"));
        types.add(new TypeOverview(2, "Cameradevices"));
        types.add(new TypeOverview(3, "Doordevices"));
        types.add(heating);
        types.add(new TypeOverview(5, "Lightningdevices"));
        types.add(new TypeOverview(6, "Musicdevices"));
        types.add(stoven);
        types.add(new TypeOverview(8, "Windowdevices"));

        /** Notifications */
        Notification n1 = new Notification();
        n1.setNotificationText("You left the stoven turned on! System turns off stoven (in 3 mins)!");
        n1.setNotificationID(1);
        n1.setTimestamp(new Date());
        n1.setSeverity(Severity.criticalAlertTimeToReact);
        n1.setDevice(s1);
        n1.getReasons().add("User left home at: " + n1.getTimestamp().getHours() + ":" + n1.getTimestamp().getMinutes());
        n1.getReasons().add("Stoven turned on since: " + n1.getTimestamp().getHours() + ":" + n1.getTimestamp().getMinutes());

        Notification n2 = new Notification();
        n2.setNotificationText("Lightning in bathroom is still on! And we write some more to test linebreak!!!!!!");
        n2.setNotificationID(2);
        n2.setTimestamp(new Date());
        n2.setSeverity(Severity.infoAlert);
        n2.setDevice(l1);
        n2.getReasons().add("User left bathroom at:"+ n2.getTimestamp().getHours() + ":" + n2.getTimestamp().getMinutes());
        n2.getReasons().add("Lightning in bathroom is on since:"+ n2.getTimestamp().getHours() + ":" + n2.getTimestamp().getMinutes());


        Notification n3 = new Notification();
        n3.setNotificationText("You forgot your keys at home!");
        n3.setNotificationID(3);
        n3.setTimestamp(new Date());
        n3.setSeverity(Severity.criticalAlertNoAction);
        n3.setDevice(l1);
        n3.getReasons().add("User left home at:"+ n2.getTimestamp().getHours() + ":" + n2.getTimestamp().getMinutes());
        n3.getReasons().add("Keys at home since:"+ n2.getTimestamp().getHours() + ":" + n2.getTimestamp().getMinutes());

        notifications = new ArrayList<Notification>();
        notifications.add(n1);
        notifications.add(n2);
        notifications.add(n3);

    }
}
