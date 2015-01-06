package com.example.FoodManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.example.FoodManager.entities.SimpleItemEntity;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class CreateNewListActivity extends Activity {
    private EditText listNameView;
    private String listName;
    public  ArrayList<SimpleItemEntity> itemsList;
    ArrayList<String>  newListItems;
    NewListAdapter adapter ;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list_view);

        listNameView = (EditText) findViewById(R.id.list_name);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        listName = "Lista z " + dateFormat.format(cal.getTime());
        listNameView.setHint(listName);

        itemsList = new ArrayList<SimpleItemEntity>();
        itemsList.add(new SimpleItemEntity("smietana","krasnysytaw","Szt.","ffff",4));
        itemsList.add(new SimpleItemEntity("cosinnego","dddd","kg.","232",4));
        ListView listView = (ListView) findViewById(R.id.new_item_list);

        adapter = new NewListAdapter(this,R.layout.create_new_list_row,itemsList,listView);
        listView.setAdapter(adapter);
    }

    public void addNewItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            //addNewItem Result
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    SimpleItemEntity newItem = (SimpleItemEntity) data.getExtras().get("newItem");
                    itemsList.add(newItem);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 2:
                if(resultCode == Activity.RESULT_OK){
                    SimpleItemEntity newItem = (SimpleItemEntity) data.getExtras().get("newItem");
                    itemsList.get((Integer)data.getExtras().get("pos")).setSimple(newItem);
                    adapter.notifyDataSetChanged();
                }
        }
    }

}
