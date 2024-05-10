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

public class BookLoanActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private EditText editTextAccessNo, editTextBranchId, editTextCardNo;
    private Button buttonIssueBook, buttonReturnBook, buttonViewBookLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_loan);

        dbHelper = new DatabaseHelper(this);

        editTextAccessNo = findViewById(R.id.editTextAccessNo);
        editTextBranchId = findViewById(R.id.editTextBranchId);
        editTextCardNo = findViewById(R.id.editTextCardNo);

        buttonIssueBook = findViewById(R.id.buttonIssueBook);
        buttonReturnBook = findViewById(R.id.buttonReturnBook);
        buttonViewBookLoans = findViewById(R.id.buttonViewBookLoans);

        buttonIssueBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueBook();
            }
        });

        buttonReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnBook();
            }
        });

        buttonViewBookLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBookLoans();
            }
        });
    }

    private void issueBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ACCESS_NO", editTextAccessNo.getText().toString());
        values.put("BRANCH_ID", editTextBranchId.getText().toString());
        values.put("CARD_NO", editTextCardNo.getText().toString());
        // You would also set DATE_OUT and DATE_DUE values here

        long newRowId = db.insert("Book_Loan", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error issuing book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book issued successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void returnBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "ACCESS_NO = ? AND BRANCH_ID = ? AND CARD_NO = ?";
        String[] selectionArgs = {
                editTextAccessNo.getText().toString(),
                editTextBranchId.getText().toString(),
                editTextCardNo.getText().toString()
        };
        int deletedRows = db.delete("Book_Loan", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No book loan found with the given details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book returned successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewBookLoans() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "ACCESS_NO", "BRANCH_ID", "CARD_NO" };
        Cursor cursor = db.query(
                "Book_Loan",
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String accessNo = cursor.getString(cursor.getColumnIndexOrThrow("ACCESS_NO"));
            String branchId = cursor.getString(cursor.getColumnIndexOrThrow("BRANCH_ID"));
            String cardNo = cursor.getString(cursor.getColumnIndexOrThrow("CARD_NO"));
            stringBuilder.append("Access No: ").append(accessNo).append(", Branch ID: ").append(branchId)
                    .append(", Card No: ").append(cardNo).append("\n");
        }
        cursor.close();

        if (stringBuilder.length() == 0) {
            Toast.makeText(this, "No book loans found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
