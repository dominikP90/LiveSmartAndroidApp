package livesmart.com.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import livesmart.com.dataModel.Notification;
import livesmart.com.dataModel.Severity;
import livesmart.com.main.R;


/**
 * Created by Dominik Poppek on 04.01.2017.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {

    private Context context;

    public NotificationAdapter(Context context,int textViewResourceId, ArrayList<Notification> notifications) {
        super(context, textViewResourceId, notifications);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Notification notification = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }

        TextView notificationTypeView = (TextView) convertView.findViewById(R.id.notificationTypeText);
        TextView notificationTimeView = (TextView) convertView.findViewById(R.id.notificationTime);
        TextView notificationTextView = (TextView) convertView.findViewById(R.id.notificationText);

        notificationTextView.setText(notification.getNotificationText());
        notificationTimeView.setText(notification.getTimestamp().getHours() + ":" + notification.getTimestamp().getMinutes());

        // Define backgroundcolor depending on the severity
        Severity notificationSeverity = notification.getSeverity();
        if (notificationSeverity.equals(Severity.criticalAlertNoAction) || notificationSeverity.equals(Severity.criticalAlertAction) ||
                notificationSeverity.equals(Severity.criticalAlertTimeToReact)) {

            notificationTypeView.setText("Alert: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTextView.setBackgroundColor(Color.parseColor("#EE0000"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#EE0000"));
        } else {
            notificationTypeView.setText("Info: ");
            notificationTypeView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTextView.setBackgroundColor(Color.parseColor("#FFC125"));
            notificationTimeView.setBackgroundColor(Color.parseColor("#FFC125"));
        }

        // Return the completed view to render on screen
        return convertView;

    }
}
