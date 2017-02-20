package livesmart.com.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import livesmart.com.dataModel.TypeOverview;
import livesmart.com.main.R;


/**
 * Created by Dominik Poppek on 06.01.2017.
 */

public class TypesAdapter extends ArrayAdapter<TypeOverview> {

    private Context context;

    public TypesAdapter(Context context,int textViewResourceId, ArrayList<TypeOverview> types) {
        super(context, textViewResourceId, types);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        TypeOverview type = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_type, parent, false);
        }

        // Lookup view for data population
        TextView typesName = (TextView) convertView.findViewById(R.id.typeName);

        // Populate the data into the template view using the data object
        typesName.setText(type.getTypesName());


        // Return the completed view to render on screen
        return convertView;

    }
}
