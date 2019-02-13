package com.example.np_fb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class DNameAdapter extends ArrayAdapter<DName> {

    //storing all the names in the list
    private List<DName> names;


    //context object
    private Context context;

    //constructor
    public DNameAdapter(Context context, int resource, List<DName> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.dnames, null, true);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewEmail = listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewNumber = listViewItem.findViewById(R.id.textViewNumber);
        ImageView imageViewStatus = listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        DName name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getName());
        textViewEmail.setText(name.getEmail());
        textViewNumber.setText(name.getContactno());

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
