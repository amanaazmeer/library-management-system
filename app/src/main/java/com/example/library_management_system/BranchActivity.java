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

public class BranchActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private EditText editTextBranchId, editTextBranchName, editTextBranchAddress;
    private Button buttonAddBranch, buttonUpdateBranch, buttonDeleteBranch, buttonViewBranches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        dbHelper = new DatabaseHelper(this);

        editTextBranchId = findViewById(R.id.editTextBranchId);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextBranchAddress = findViewById(R.id.editTextBranchAddress);

        buttonAddBranch = findViewById(R.id.buttonAddBranch);
        buttonUpdateBranch = findViewById(R.id.buttonUpdateBranch);
        buttonDeleteBranch = findViewById(R.id.buttonDeleteBranch);
        buttonViewBranches = findViewById(R.id.buttonViewBranches);

        buttonAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBranch();
            }
        });

        buttonUpdateBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBranch();
            }
        });

        buttonDeleteBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBranch();
            }
        });

        buttonViewBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBranches();
            }
        });
    }

    private void addBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("BRANCH_NAME", editTextBranchName.getText().toString());
        values.put("ADDRESS", editTextBranchAddress.getText().toString());

        long newRowId = db.insert("Branch", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding branch", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BRANCH_NAME", editTextBranchName.getText().toString());
        values.put("ADDRESS", editTextBranchAddress.getText().toString());

        String selection = "BRANCH_ID = ?";
        String[] selectionArgs = { editTextBranchId.getText().toString() };

        int count = db.update(
                "Branch",
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            Toast.makeText(this, "No branch found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBranch() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "BRANCH_ID = ?";
        String[] selectionArgs = { editTextBranchId.getText().toString() };
        int deletedRows = db.delete("Branch", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No branch found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Branch deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewBranches() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "BRANCH_ID", "BRANCH_NAME", "ADDRESS" };
        Cursor cursor = db.query(
                "Branch",
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String branchId = cursor.getString(cursor.getColumnIndexOrThrow("BRANCH_ID"));
            String branchName = cursor.getString(cursor.getColumnIndexOrThrow("BRANCH_NAME"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS"));
            stringBuilder.append("Branch ID: ").append(branchId).append(", Branch Name: ").append(branchName)
                    .append(", Address: ").append(address).append("\n");
        }
        cursor.close();

        if (stringBuilder.length() == 0) {
            Toast.makeText(this, "No branches found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
