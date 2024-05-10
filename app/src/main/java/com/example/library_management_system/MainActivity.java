package com.example.library_management_system; // Replace this with your actual package name

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonBook, buttonPublisher, buttonBranch, buttonMember, buttonBookLoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        buttonBook = findViewById(R.id.button_book);
        buttonPublisher = findViewById(R.id.button_publisher);
        buttonBranch = findViewById(R.id.button_branch);
        buttonMember = findViewById(R.id.button_member);
        buttonBookLoan = findViewById(R.id.button_book_loan);

        // Set click listeners for buttons
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookActivity.class));
            }
        });

        buttonPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PublisherActivity.class));
            }
        });

        buttonBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BranchActivity.class));
            }
        });

        buttonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MemberActivity.class));
            }
        });

        buttonBookLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookLoanActivity.class));
            }
        });
    }
}
