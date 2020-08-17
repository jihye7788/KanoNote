package com.mynote.kano.firebase.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mynote.kano.R;
import com.mynote.kano.vo.Diary;

import java.util.HashMap;
import java.util.Map;


public class write_diaryActivity extends AppCompatActivity {

    private EditText diaryContent;
    private String dContent;
    private String dContentfromFB;
    private String diaryDate;
    private String userId;
    private String result;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Diary");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        diaryDate = intent.getExtras().getString("diaryDate");

        userId =intent.getExtras().getString("owner_name");

       //firebase에서 값 가져오기
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String c = "/uesr-diarys" + userId + diaryDate +"/";

                Diary dFromFB = dataSnapshot.child(c).getValue(Diary.class);
                if(dFromFB != null) {
                    dContentfromFB = dFromFB.getdContent();
                    TextView textView = findViewById(R.id.diaryContent);
                    textView.setText(dContentfromFB);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        diaryContent = (EditText)findViewById(R.id.diaryContent);


        if(intent!= null) {
            TextView tx1 =findViewById(R.id.dateView);
            tx1.setText(diaryDate);
            diaryContent.setText(dContentfromFB);
        }

       result =  intent.getExtras().getString("checkDiary");
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(result =="false"){ //일기가 없는 경우
                    writeDiary();
                }else{//일기가 있는 경우
                    updateDiary();
                }
                writeDiary();
            }
        });
    }


    public void updateDiary(){
          ValueEventListener diaryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = getIntent();
                diaryDate = intent.getExtras().getString("diaryDate");
                //userId 바꿔주기
                /*   userId = intent.getExtras().getString("userId");*/
                dContent = diaryContent.getText().toString();

                if (!TextUtils.isEmpty(dContent)){

                    String key = myRef.child("Diary").push().getKey();
                    Diary diary = new Diary(userId, diaryDate, dContent);
      /*      myRef.child("diary").child(key).setValue(diary);
            myRef.child("user-diary").child(userId).child(key).setValue(diary);
 */
                    Map<String, Object> postValues = diary.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/diarys/"+key, postValues);
                    childUpdates.put("/uesr-diarys"+userId+diaryDate+"/", postValues);
                    myRef.updateChildren(childUpdates);

                    /* Intent intent3 = new Intent(this, calendarActivity.class);
                    startActivity(intent3);*/

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
         };
    }

    public void writeDiary(){
        Log.v("?","writeDiary");
        Toast.makeText(this,"write",Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        diaryDate = intent.getExtras().getString("diaryDate");
        //userId 바꿔주기
        /*   userId = intent.getExtras().getString("userId");*/

        dContent = diaryContent.getText().toString();
        Log.v("?",dContent);

        if (!TextUtils.isEmpty(dContent)){
            String key = myRef.child("Diary").push().getKey();
            Diary diary = new Diary(userId, diaryDate, dContent);

            Map<String, Object> postValues = diary.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            postValues.put("key",key);

            childUpdates.put("/diarys/"+key, postValues);
            childUpdates.put("/uesr-diarys"+userId+diaryDate+"/", postValues);
            myRef.updateChildren(childUpdates);

            Intent intent2 = new Intent(this, calendarActivity.class);
            startActivity(intent2);

        }else{
            Toast.makeText(this,"Please Insert diary",Toast.LENGTH_LONG).show();
        }
    }
}




