package livesmart.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import livesmart.com.main.R;
import livesmart.com.main.RoomDevicesView;
import livesmart.com.utility.RoomsAdapter;
import livesmart.com.utility.TypesAdapter;

import static livesmart.com.main.LiveSmartMain.rooms;
import static livesmart.com.main.LiveSmartMain.types;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */
public class RoomsFragment extends ListFragment implements AdapterView.OnItemClickListener{

    private RoomsAdapter roomsAdapter;
    private boolean firstCreated = true;

    public RoomsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        firstCreated = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), rooms.get(position).getRoomName() + " selected...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), RoomDevicesView.class);
        intent.putExtra("ROOM", rooms.get(position));
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser & !firstCreated) {
            roomsAdapter = new RoomsAdapter(getActivity(), android.R.layout.simple_list_item_1, rooms);
            setListAdapter(roomsAdapter);
        }

    }
}
