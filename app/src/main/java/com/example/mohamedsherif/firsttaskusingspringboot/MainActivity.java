package com.example.mohamedsherif.firsttaskusingspringboot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());
    }

    public void openEmployeesActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AllEmployeesActivity.class);
        startActivity(intent);
    }

    public void openSearchActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void openAddActivity(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void openUpdateActivity(View view) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        startActivity(intent);
    }

    public void openDeleteActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
        startActivity(intent);
    }
}
