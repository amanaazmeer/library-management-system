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

public class MemberActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private EditText editTextCardNo, editTextName, editTextAddress, editTextPhone;
    private Button buttonAddMember, buttonUpdateMember, buttonDeleteMember, buttonViewMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        dbHelper = new DatabaseHelper(this);

        editTextCardNo = findViewById(R.id.editTextCardNo);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);

        buttonAddMember = findViewById(R.id.buttonAddMember);
        buttonUpdateMember = findViewById(R.id.buttonUpdateMember);
        buttonDeleteMember = findViewById(R.id.buttonDeleteMember);
        buttonViewMembers = findViewById(R.id.buttonViewMembers);

        buttonAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
            }
        });

        buttonUpdateMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMember();
            }
        });

        buttonDeleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });

        buttonViewMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMembers();
            }
        });
    }

    private void addMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CARD_NO", editTextCardNo.getText().toString());
        values.put("NAME", editTextName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());

        long newRowId = db.insert("Member", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding member", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", editTextName.getText().toString());
        values.put("ADDRESS", editTextAddress.getText().toString());
        values.put("PHONE", editTextPhone.getText().toString());

        String selection = "CARD_NO = ?";
        String[] selectionArgs = { editTextCardNo.getText().toString() };

        int count = db.update(
                "Member",
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            Toast.makeText(this, "No member found with the given card number", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMember() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "CARD_NO = ?";
        String[] selectionArgs = { editTextCardNo.getText().toString() };
        int deletedRows = db.delete("Member", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No member found with the given card number", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Member deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewMembers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "CARD_NO", "NAME", "ADDRESS", "PHONE" };
        Cursor cursor = db.query(
                "Member",
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String cardNo = cursor.getString(cursor.getColumnIndexOrThrow("CARD_NO"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("PHONE"));
            stringBuilder.append("Card No: ").append(cardNo).append(", Name: ").append(name)
                    .append(", Address: ").append(address).append(", Phone: ").append(phone).append("\n");
        }
        cursor.close();

        if (stringBuilder.length() == 0) {
            Toast.makeText(this, "No members found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
