package com.example.FoodManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class CreateNewListActivity extends Activity {
    private EditText listNameView;
    private String listName;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list_view);


        listNameView = (EditText) findViewById(R.id.list_name);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        listName = "Lista z " + dateFormat.format(cal.getTime());
        listNameView.setText(listName);

        ArrayList<String> newListItems= new ArrayList<String>();
        newListItems.add("Zupa");
        newListItems.add(getString(R.string.add_new));

        ListView listView = (ListView) findViewById(R.id.new_item_list);

        NewListAdapter adapter = new NewListAdapter(this,R.layout.create_new_list_row,newListItems.toArray(new String[0]),listView);
        listView.setAdapter(adapter);


    }

    public void clearListName(View view){
        listNameView.setText("");
    }

    public void actionButtonClicked(View view){

    }
}
