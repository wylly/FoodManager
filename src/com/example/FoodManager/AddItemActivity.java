package com.example.FoodManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.FoodManager.entities.SimpleItemEntity;


public class AddItemActivity extends Activity {

    private Spinner spinner;
    private TextView nameView;
    private TextView brandView;
    private TextView sizeView;
    private TextView additionalInfoView;
    boolean isNew = true;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_item_details_edit);
        spinner = (Spinner) findViewById(R.id.itemSize);
        nameView = (TextView) findViewById(R.id.itemName);
        brandView = (TextView) findViewById(R.id.itemBrand);
        sizeView = (TextView) findViewById(R.id.itemAmount);
        additionalInfoView = (TextView) findViewById(R.id.itemOthers);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.item_sizes, R.layout.amount_spinner_layout);
        spinner.setAdapter(adapter);
        if(getIntent().getExtras()!=null){
            isNew = false;
            SimpleItemEntity forEdit = (SimpleItemEntity)getIntent().getExtras().get("itemForEdit");
            nameView.setText(forEdit.getName());
            brandView.setText(forEdit.getBrand());
            sizeView.setText(String.valueOf(forEdit.getAmount()));
            additionalInfoView.setText(forEdit.getAdditionalInfo());
            spinner.setSelection(adapter.getPosition(forEdit.getSize()));
            position = (Integer)getIntent().getExtras().get("pos");

        }
    }

    public void finishEditing(View view){
        TextView nameView = (TextView) findViewById(R.id.itemName);
        if(!nameView.getText().toString().equals("")){
            TextView brandView = (TextView) findViewById(R.id.itemBrand);
            Spinner sizeView = (Spinner) findViewById(R.id.itemSize);
            TextView additionalInfo = (TextView) findViewById(R.id.itemOthers);
            TextView amountView = (TextView) findViewById(R.id.itemAmount);
            if(amountView.getText().toString().equals("")) amountView.setText("0");

            SimpleItemEntity newItem = new SimpleItemEntity(nameView.getText().toString(),brandView.getText().toString(),sizeView.getSelectedItem().toString(),additionalInfo.getText().toString(),new Integer(amountView.getText().toString()));

            Intent returnIntent = new Intent();
            if(!isNew) returnIntent.putExtra("pos",position);
            returnIntent.putExtra("newItem",newItem);
            setResult(RESULT_OK,returnIntent);
            finish();
        }else{
            Toast toast = Toast.makeText(this,"Pole \"Nazwa\" nie może być puste",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
