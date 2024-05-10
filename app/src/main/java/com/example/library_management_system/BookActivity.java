package com.example.library_management_system;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;


public class BookActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextBookId, editTextTitle, editTextPublisher;
    private Button buttonAddBook, buttonUpdateBook, buttonDeleteBook, buttonViewBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        dbHelper = new DatabaseHelper(this);

        editTextBookId = findViewById(R.id.editTextBookId);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextPublisher = findViewById(R.id.editTextPublisher);

        buttonAddBook = findViewById(R.id.buttonAddBook);
        buttonUpdateBook = findViewById(R.id.buttonUpdateBook);
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook);
        buttonViewBooks = findViewById(R.id.buttonViewBooks);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        buttonUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        buttonDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        buttonViewBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBooks();
            }
        });
    }

    private void addBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BOOK_ID", editTextBookId.getText().toString());
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());

        long newRowId = db.insert("Book", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", editTextTitle.getText().toString());
        values.put("PUBLISHER_NAME", editTextPublisher.getText().toString());

        String selection = "BOOK_ID = ?";
        String[] selectionArgs = { editTextBookId.getText().toString() };

        int count = db.update(
                "Book",
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            Toast.makeText(this, "No book found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "BOOK_ID = ?";
        String[] selectionArgs = { editTextBookId.getText().toString() };
        int deletedRows = db.delete("Book", selection, selectionArgs);

        if (deletedRows == 0) {
            Toast.makeText(this, "No book found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { "BOOK_ID", "TITLE", "PUBLISHER_NAME" };
        Cursor cursor = db.query(
                "Book",
                projection,
                null,
                null,
                null,
                null,
                null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String bookId = cursor.getString(cursor.getColumnIndexOrThrow("BOOK_ID"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));
            String publisherName = cursor.getString(cursor.getColumnIndexOrThrow("PUBLISHER_NAME"));
            stringBuilder.append("Book ID: ").append(bookId).append(", Title: ").append(title)
                    .append(", Publisher: ").append(publisherName).append("\n");
        }
        cursor.close();

        if (stringBuilder.length() == 0) {
            Toast.makeText(this, "No books found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
