package com.example.autosilenceui;

import static androidx.core.content.res.ResourcesCompat.getFont;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class LocationsAdapter extends ArrayAdapter<Location> {

    Context context;
    ArrayList<Location> locations;

    public LocationsAdapter(Context applicationContext, ArrayList<Location> list) {
        super(applicationContext, 0, list);
        context = applicationContext;
        locations = list;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.list, null);
        Location location = getItem(position);
        TextView names = (TextView) view.findViewById(R.id.title);
        TextView addresses = (TextView) view.findViewById(R.id.subtitle);
        ImageView background = (ImageView) view.findViewById(R.id.imageView2);
        names.setText(location.getName());
        names.setTypeface(ResourcesCompat.getFont(context, R.font.fjalla_one));
        addresses.setText(location.getLocation());
        addresses.setTypeface(ResourcesCompat.getFont(context, R.font.fira_sans_thin));
        return view;
    }
}
