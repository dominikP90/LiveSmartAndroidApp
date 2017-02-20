package livesmart.com.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import livesmart.com.dataModel.Device;
import livesmart.com.dataModel.DeviceType;
import livesmart.com.dataModel.HeatingDevice;
import livesmart.com.dataModel.LightningDevice;
import livesmart.com.dataModel.MusicDevice;
import livesmart.com.dataModel.Room;
import livesmart.com.main.R;
import livesmart.com.main.RoomDevicesView;
import livesmart.com.utility.RoomsAdapter;

import static livesmart.com.main.LiveSmartMain.rooms;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */
public class RoomsFragment extends ListFragment implements AdapterView.OnItemClickListener{

    private RoomsAdapter roomsAdapter;

    public RoomsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        roomsAdapter = new RoomsAdapter(getActivity(), android.R.layout.simple_list_item_1, rooms);
        setListAdapter(roomsAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RoomDevicesView.class);
        intent.putExtra("ROOM", rooms.get(position));
        startActivity(intent);
    }

}
