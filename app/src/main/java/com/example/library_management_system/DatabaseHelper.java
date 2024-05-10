package com.example.library_management_system;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE Book (" +
                "BOOK_ID VARCHAR(13) PRIMARY KEY," +
                "TITLE VARCHAR(30)," +
                "PUBLISHER_NAME VARCHAR(20)" +
                ")");

        db.execSQL("CREATE TABLE Publisher (" +
                "NAME VARCHAR(20) PRIMARY KEY," +
                "ADDRESS VARCHAR(30)," +
                "PHONE VARCHAR(10)" +
                ")");

        db.execSQL("CREATE TABLE Branch (" +
                "BRANCH_ID VARCHAR(5) PRIMARY KEY," +
                "BRANCH_NAME VARCHAR(20)," +
                "ADDRESS VARCHAR(30)" +
                ")");

        db.execSQL("CREATE TABLE Member (" +
                "CARD_NO VARCHAR(10) PRIMARY KEY," +
                "NAME VARCHAR(20)," +
                "ADDRESS VARCHAR(30)," +
                "PHONE VARCHAR(10)," +
                "UNPAID_DUES NUMBER(5,2)" +
                ")");

        db.execSQL("CREATE TABLE Book_Author (" +
                "BOOK_ID VARCHAR(13)," +
                "AUTHOR_NAME VARCHAR(20)," +
                "PRIMARY KEY(BOOK_ID, AUTHOR_NAME)," +
                "FOREIGN KEY(BOOK_ID) REFERENCES Book" +
                ")");

        db.execSQL("CREATE TABLE Book_Copy (" +
                "BOOK_ID VARCHAR(13)," +
                "BRANCH_ID VARCHAR(5)," +
                "ACCESS_NO VARCHAR(5)," +
                "PRIMARY KEY(ACCESS_NO, BRANCH_ID)," +
                "FOREIGN KEY(BOOK_ID) REFERENCES Book," +
                "FOREIGN KEY(BRANCH_ID) REFERENCES Branch" +
                ")");

        db.execSQL("CREATE TABLE Book_Loan (" +
                "ACCESS_NO VARCHAR(5)," +
                "BRANCH_ID VARCHAR(5)," +
                "CARD_NO VARCHAR(5)," +
                "DATE_OUT DATE," +
                "DATE_DUE DATE," +
                "DATE_RETURNED DATE," +
                "PRIMARY KEY(ACCESS_NO, BRANCH_ID, CARD_NO, DATE_OUT)," +
                "FOREIGN KEY(ACCESS_NO, BRANCH_ID) REFERENCES Book_Copy," +
                "FOREIGN KEY(CARD_NO) REFERENCES Member," +
                "FOREIGN KEY(BRANCH_ID) REFERENCES Branch" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS Book_Loan");
        db.execSQL("DROP TABLE IF EXISTS Book_Copy");
        db.execSQL("DROP TABLE IF EXISTS Book_Author");
        db.execSQL("DROP TABLE IF EXISTS Member");
        db.execSQL("DROP TABLE IF EXISTS Branch");
        db.execSQL("DROP TABLE IF EXISTS Publisher");
        db.execSQL("DROP TABLE IF EXISTS Book");

        onCreate(db);
    }
}

