package livesmart.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import livesmart.com.dataModel.SmartHome;
import livesmart.com.main.R;
import livesmart.com.main.RoomDevicesView;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class RoomsFragment2 extends ListFragment implements AdapterView.OnItemClickListener{

    public RoomsFragment2() {}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Rooms, android.R.layout.simple_list_item_1);
     //   RoomsAdapter roomsAdapter = new RoomsAdapter(getActivity(), SmartHome.getInstance().getSmartHomeRooms());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RoomDevicesView.class);
        intent.putExtra("ROOM", SmartHome.getInstance().getSmartHomeRooms().get(position));
        startActivity(intent);
    }

}
