package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button buttonAdd;
    EditText editTextItem;
    RecyclerView ReviewItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //attaching elements by their ids
        buttonAdd = findViewById(R.id.buttonAdd);
        editTextItem = findViewById(R.id.editTextItem);
        ReviewItems = findViewById(R.id.ReviewItems);


        loadItems();
       // items.add("Buy Milk");
       // items.add("Eat vitamins");

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            //pos at which user long pressed
            public void onItemLongClicked(int position) {
                //delete the item from model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                //to change screen pos of toast
                Toast toast = Toast.makeText(getApplicationContext(), "item was removed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                toast.show();
                saveItems();
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        ReviewItems.setAdapter((itemsAdapter));
        ReviewItems.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String todoItem = editTextItem.getText().toString();
               // Add item to the model
                items.add(todoItem);
                // Notify adapter that an item was inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                editTextItem.setText("");
                //toast is a feedback for user action
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Item was added", Toast.LENGTH_SHORT);
                //to change screen pos of toast
                toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 0);
                toast.show();
                saveItems();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loadItems()
    {
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            Log.e("MainActivity", "Error Reading items", e);
            items = new ArrayList<>();
        }
    }
    //This function saves items by writing them into data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error Writing items", e);
        }
    }
}