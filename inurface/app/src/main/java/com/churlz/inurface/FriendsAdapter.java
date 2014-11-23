package com.churlz.inurface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CHURLZ on 14-11-23.
 */
public class FriendsAdapter extends ArrayAdapter<Friend> {
    public FriendsAdapter(Context context, ArrayList<Friend> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Friend friend = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textView2);
        // Populate the data into the template view using the data object
        tvName.setText(friend.name);
        // Return the completed view to render on screen
        return convertView;
    }
}