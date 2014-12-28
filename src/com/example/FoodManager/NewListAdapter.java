package com.example.FoodManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


public class NewListAdapter extends ArrayAdapter<String> {

    private ListView listView;
    private final Context context;
//    private final String[] values;

    public NewListAdapter(Context context, int textViewResourceId, String[] objects, ListView listView) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.listView = listView;
    }

    static class ViewHolder{
        TextView textView;
        ImageView btn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String text = getItem(position);

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.create_new_list_row, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) rowView.findViewById(R.id.label);
            holder.btn = (ImageView) rowView.findViewById(R.id.actionButton);
            rowView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.textView.setText(text);

        if (holder.textView.getText().equals(context.getString(R.string.add_new))) {
            holder.btn.setImageResource(android.R.drawable.ic_menu_add);
            holder.btn.setTag(1);
        } else {
            holder.btn.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            holder.btn.setTag(0);
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch((Integer)v.getTag()){
                   //Removing item
                   case 0:
                       System.out.println("rem");
                       break;
                   //Adding new item
                   case 1:
                       System.out.println("add");
                       break;
               }
            }
        });

        return rowView;
    }



}
