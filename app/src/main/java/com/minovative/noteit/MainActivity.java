package com.minovative.noteit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = findViewById(R.id.button);
        listView = findViewById(R.id.list);

        try {
            itemList = FileHelper.readData(this);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        arrayAdapter = new NoteAdapter(this, itemList);
        listView.setAdapter(arrayAdapter);

        add.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivityForResult(intent, 1);

        });

        listView.setOnItemClickListener((adapterView,view,position,l) -> {

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("selectedItem", itemList.get(position));
            intent.putExtra("itemPosition", position);
            startActivityForResult(intent, 2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                String newItem = data.getStringExtra("newItem");
                itemList.add(newItem);
            }

            else if (requestCode == 2) {

                boolean deleteItem = data.getBooleanExtra("deleteItem", false);
                int position = data.getIntExtra("itemPosition", -1);

                if (deleteItem && position != 1) {

                    itemList.remove(position);

                } else {
                    String updatedItem = data.getStringExtra("newItem");

                    if (position != -1 && updatedItem != null) {

                        itemList.set(position, updatedItem);
                    }
                }
            }

            try {
                FileHelper.writeData(itemList,getApplicationContext());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            arrayAdapter.notifyDataSetChanged();
        }

    }

}