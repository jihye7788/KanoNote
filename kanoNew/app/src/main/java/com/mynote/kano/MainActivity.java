package com.mynote.kano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

/*        goDatepickbutton =(Button)findViewById(R.id.goDatepickbutton);
        goDatepickbutton.setOnClickListener(this);*/
    }



    public void goCommit(View V){
        Intent intent2 = new Intent(this, CommitActivity.class);
        startActivity(intent2);
        finish();
    }
    public void goDatepick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, calendarActivity.class);
        startActivity(intent);
        finish();

    }
}
