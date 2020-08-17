package com.mynote.kano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clist);


        TextView tx1 = (TextView)findViewById(R.id.textView1); //*TextView선언*//*
        TextView tx2 = (TextView)findViewById(R.id.textView2);

        Intent intent = getIntent(); //*데이터 수신*//*

        String name = intent.getExtras().getString("name"); //*String형*//*
        tx1.setText(name);

        int age = intent.getExtras().getInt("age"); //*int형*//*
        tx2.setText(String.valueOf(age));
    }
}