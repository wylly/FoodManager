package com.example.FoodManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AddItemActivity extends Activity {

    private Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_item_details);
        spinner = (Spinner) findViewById(R.id.sizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_sizes, R.layout.amount_spinner_layout);
        spinner.setAdapter(adapter);
    }
}
