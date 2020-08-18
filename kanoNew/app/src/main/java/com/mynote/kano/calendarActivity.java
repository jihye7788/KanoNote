package com.mynote.kano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;


public class calendarActivity extends AppCompatActivity {

    DatePicker datePicker;  //  datePicker - 날짜를 선택하는 달력
    String pickedDate;
    String userId;
    String dContent;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

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


        //datePick 기능 만들기
        //datePicker.init(연도, 달, 일)
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                pickedDate = year + "," + monthOfYear+ "," + dayOfMonth;

            }
        });
        Intent intent = getIntent();
        //userId = intent.getExtras().getString("userId");
        /*userId="jihye2";
        DatabaseReference myRef = database.getReference().child("/uesr-diarys"+userId+pickedDate+"/");
        DatabaseReference dContentRef = myRef.child("dContent");
        dContent =String.valueOf(dContentRef);*/

        Button button = (Button) findViewById(R.id.goDiaryButton);

    }

 /*   public String checkDiary(){
        *//* Intent intent = getIntent();
          userId = intent.getExtras().getString("userId");*//*
        String userId = "jihye2";
        String makeChildName = "/uesr-diarys"+userId+pickedDate+"/";
        Query query = myRef.child(makeChildName).orderByChild("Key");

        if (query==null){
            //일기가 있을경우 false
            Intent intent =new Intent(getApplicationContext(),write_diaryActivity.class);
*//*
            intent.putExtra("diaryKey",query);
*//*
            return "false";
        }
        return "true";
    }*/

    public void goDiary(View v){
        Intent intent = new Intent(getApplicationContext(),write_diaryActivity.class);
        intent.putExtra("diaryDate",pickedDate);
       // intent.putExtra("dContent", dContent);
/*        String result = checkDiary();
        intent.putExtra("checkDiary", result);*/

        startActivity(intent);
        finish();
    }


}