package com.example.FoodManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.FoodManager.entities.SimpleItemEntity;

import java.util.ArrayList;


public class NewListAdapter extends ArrayAdapter<SimpleItemEntity> {

    private ListView listView;
    private final Context context;
//    private final String[] values;
    public ViewHolder holder;
    private ArrayList<SimpleItemEntity> itemsList;
    public NewListAdapter(Context context, int textViewResourceId, ArrayList<SimpleItemEntity> objects, ListView listView) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.listView = listView;
        this.itemsList = objects;

    }

    static class ViewHolder{
        TextView textView;
        ImageView btn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        String text = getItem(position).getName();

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.create_new_list_row, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
            holder.btn = (ImageView) rowView.findViewById(R.id.actionButton);
            rowView.setTag(holder);
        }

        holder = (ViewHolder) rowView.getTag();

        holder.textView.setText(text);

       holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsList.remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddItemActivity.class);
                intent.putExtra("itemForEdit",getItem(position));
                intent.putExtra("pos",position);
                ((Activity)v.getContext()).startActivityForResult(intent, 2);
            }
        });

        return rowView;
    }



}
