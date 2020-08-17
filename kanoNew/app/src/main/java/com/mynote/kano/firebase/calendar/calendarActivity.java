package com.mynote.kano.firebase.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.firebase.database.FirebaseDatabase;
import com.mynote.kano.CommitActivity;
import com.mynote.kano.R;

import java.util.Calendar;

public class calendarActivity extends AppCompatActivity {

    private DatePicker datePicker;  //  datePicker - 날짜를 선택하는 달력
    private String pickedDate;
    // Write a message to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static final int REQUEST_CODE_MENU = 101;//diary에서 받는 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        //오늘 날씨를 받게해주는 것들
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        pickedDate = cYear + "," + (cMonth+1)+ "," + cDay;

    }
    public void onStart(){
        super.onStart();

        //datePick 기능 만들기
        //datePicker.init(연도, 달, 일)
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                pickedDate = year + "," + monthOfYear+ "," + dayOfMonth;
            }
        });

        Button button = (Button) findViewById(R.id.goDiaryButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommitActivity.class);
                intent.putExtra("diaryDate",pickedDate);

                startActivity(intent);
                finish();
            }
        });

        Button button2 = (Button) findViewById(R.id.goCommitButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), write_diaryActivity.class);
                intent.putExtra("diaryDate",pickedDate);
                startActivity(intent);
                finish();
            }
        });

    }
}