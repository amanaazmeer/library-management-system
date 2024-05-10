package com.example.library_management_system;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class PublisherActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private EditText editTextPublisherName, editTextAddress, editTextPhone;
    private Button buttonAddPublisher, buttonUpdatePublisher, buttonDeletePublisher, buttonViewPublishers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);

        dbHelper = new DatabaseHelper(this);

        editTextPublisherName = findViewById(R.id.editTextPublisherName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);

        buttonAddPublisher = findViewById(R.id.buttonAddPublisher);
        buttonUpdatePublisher = findViewById(R.id.buttonUpdatePublisher);
        buttonDeletePublisher = findViewById(R.id.buttonDeletePublisher);
        buttonViewPublishers = findViewById(R.id.buttonViewPublishers);

        buttonAddPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPublisher();
            }
        });

        buttonUpdatePublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePublisher();
            }
        });

        buttonDeletePublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePublisher();
            }
        });

        buttonViewPublishers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPublishers();
            }
        });
    }

    private void addPublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", editTextPublisherName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());

        long newRowId = db.insert("Publisher", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding publisher", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Publisher added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());

        String selection = "NAME = ?";
        String[] selectionArgs = { editTextPublisherName.getText().toString() };

        int count = db.update(
                "Publisher",
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            Toast.makeText(this, "No publisher found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Publisher updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePublisher() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "NAME = ?";
        String[] selectionArgs = { editTextPublisherName.getText().toString() };
        int deletedRows = db.delete("Publisher", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No publisher found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Publisher deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPublishers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "NAME", "ADDRESS", "PHONE" };
        Cursor cursor = db.query(
                "Publisher",
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("PHONE"));
            stringBuilder.append("Name: ").append(name).append(", Address: ").append(address)
                    .append(", Phone: ").append(phone).append("\n");
        }
        cursor.close();

        if (stringBuilder.length() == 0) {
            Toast.makeText(this, "No publishers found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
