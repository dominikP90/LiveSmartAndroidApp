package livesmart.com.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import livesmart.com.main.R;
import livesmart.com.main.RoomDevicesView;
import livesmart.com.main.TypeDevicesView;
import livesmart.com.utility.RoomsAdapter;
import livesmart.com.utility.TypesAdapter;

import static livesmart.com.main.LiveSmartMain.rooms;
import static livesmart.com.main.LiveSmartMain.types;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class TypesFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TypesAdapter typesAdapter;

    public TypesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_types, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typesAdapter = new TypesAdapter(getActivity(), android.R.layout.simple_list_item_1, types);
        setListAdapter(typesAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TypeDevicesView.class);
        intent.putExtra("TYPE", types.get(position));
        startActivity(intent);
    }
}
