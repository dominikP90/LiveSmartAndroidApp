package livesmart.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import livesmart.com.main.R;
import livesmart.com.main.TypeDevicesView;
import livesmart.com.utility.TypesAdapter;

import static livesmart.com.main.LiveSmartMain.types;


/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class TypesFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TypesAdapter typesAdapter;
    private boolean firstCreated = true;

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
        firstCreated = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), types.get(position).getTypesName() + " selected...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TypeDevicesView.class);
        intent.putExtra("TYPE", types.get(position));
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !firstCreated) {
            typesAdapter = new TypesAdapter(getActivity(), android.R.layout.simple_list_item_1, types);
            setListAdapter(typesAdapter);
        }
    }
}
