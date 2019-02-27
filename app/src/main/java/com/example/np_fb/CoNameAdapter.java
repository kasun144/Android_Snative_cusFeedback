package com.example.np_fb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CoNameAdapter extends ArrayAdapter<CoName> {

    //storing all the names in the list
    private List<CoName> names;

    //context object
    private Context context;

    //constructor
    public CoNameAdapter(Context context, int resource, List<CoName> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.conames, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewStatus = listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        CoName name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getName());

        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listViewItem;
    }
}
