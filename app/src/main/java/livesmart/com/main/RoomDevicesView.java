package livesmart.com.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.Room;
import livesmart.com.utility.DevicesAdapter;


/**
 * Created by Dominik Poppek on 18.12.2016.
 */

public class RoomDevicesView extends AppCompatActivity {

    private Context context;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.room_devices_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView)findViewById(R.id.listview);

        //Get Room from intent
        final Room roomIntent = (Room) getIntent().getSerializableExtra("ROOM");
        //Set Actionbar
        getSupportActionBar().setTitle(roomIntent.getRoomName());
        //Create listview
        DevicesAdapter deviceAdapter = new DevicesAdapter(this, android.R.layout.simple_list_item_1, roomIntent.getDeviceList());
        listView.setAdapter(deviceAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Device device = (Device) adapter.getItemAtPosition(position);
                Toast.makeText(context, device.getDeviceName() + " selected...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RoomsDeviceDetailView.class);
                intent.putExtra("DEVICE", device);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
