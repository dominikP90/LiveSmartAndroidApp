package livesmart.com.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import livesmart.com.dataModel.DeviceType;
import livesmart.com.dataModel.LightningDevice;
import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Severity;
import livesmart.com.main.NotificationDetailView;
import livesmart.com.main.R;
import livesmart.com.utility.NotificationAdapter;

import static livesmart.com.main.LiveSmartMain.notifications;

/**
 * Created by Dominik Poppek on 16.12.2016.
 */

public class NotificationsFragment extends ListFragment implements AdapterView.OnItemClickListener{

    //private ArrayList<Notification> notifications2;
    private ListView listView;
    private NotificationAdapter notificationsAdapter;

    public NotificationsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        notificationsAdapter = new NotificationAdapter(getActivity(), android.R.layout.simple_list_item_1, notifications);
        setListAdapter(notificationsAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), NotificationDetailView.class);
        intent.putExtra("NOTIFICATION", notifications.get(position));
        startActivity(intent);
    }
}
