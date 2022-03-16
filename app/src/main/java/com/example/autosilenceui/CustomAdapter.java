package com.example.autosilenceui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String namesList[];
    String addressList[];
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, String[] names, String[] addresses) {
        context = applicationContext;
        namesList = names;
        addressList = addresses;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return namesList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list, null);
        TextView names = (TextView) view.findViewById(R.id.title);
        TextView addresses = (TextView) view.findViewById(R.id.subtitle);
        ImageView background = (ImageView) view.findViewById(R.id.imageView2);
        names.setText(namesList[i]);
        addresses.setText(addressList[i]);
        return view;
    }
}
