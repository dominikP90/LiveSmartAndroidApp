package livesmart.com.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import livesmart.com.dataModel.Room;
import livesmart.com.dataModel.User;
import livesmart.com.main.R;

/**
 * Created by Dominik Poppek on 18.12.2016.
 */
public class RoomsAdapter extends ArrayAdapter<Room> {

    private Context context;

    public RoomsAdapter(Context context,int textViewResourceId, ArrayList<Room> rooms) {
        super(context, textViewResourceId, rooms);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Room room = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent, false);
        }

        // Lookup view for data population
        TextView roomName = (TextView) convertView.findViewById(R.id.roomName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        // Populate the data into the template view using the data object
        roomName.setText(room.getRoomName());
        int imageId = context.getResources().getIdentifier(room.getIcon_path(), "drawable", context.getPackageName());
        imageView.setImageDrawable(context.getResources().getDrawable(imageId));


        // Return the completed view to render on screen
        return convertView;

    }
}
