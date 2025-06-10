package com.minovative.noteit;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SecondActivity extends AppCompatActivity {

    FloatingActionButton addText;
    EditText editText;
    ImageView trash;
    int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        addText = findViewById(R.id.addText);
        editText = findViewById(R.id.editText);
        trash = findViewById(R.id.trash);

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        itemPosition = intent.getIntExtra("itemPosition", -1);

        if (selectedItem != null) {

            editText.setText(selectedItem);
        }

        addText.setOnClickListener(view -> {

            String userInput = editText.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newItem", userInput);
            resultIntent.putExtra("itemPosition", itemPosition);
            setResult(RESULT_OK, resultIntent);
            finish();

        });

        trash.setOnClickListener(view -> {

                AlertDialog.Builder alert = new AlertDialog.Builder(SecondActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete?");
                alert.setNegativeButton("No", (dialog, i) -> dialog.cancel());
                alert.setPositiveButton("Yes", (dialog, i) -> {

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("deleteItem", true);
                        resultIntent.putExtra("itemPosition", itemPosition);
                        setResult(RESULT_OK, resultIntent);
                        finish();

                });
                alert.show();
        });
    }
}