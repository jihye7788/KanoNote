package com.mynote.kano.fileDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mynote.kano.R;

public class dListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_list);


        TextView tx1 = (TextView)findViewById(R.id.textView1); //*TextView선언*//*
        TextView tx2 = (TextView)findViewById(R.id.textView2);
        Intent intent = getIntent(); //*데이터 수신*//*

        String name = intent.getExtras().getString("name"); //*String형*//*

        //여기에서 oid로 검색해서 받아온 파일소스를 text로 넘겨줘야한다.

        tx1.setText(name);

        tx2.setText(filesource());


    }



    public String filesource(){
        String s = "{\r\n" +
                "  \"data\": {\r\n" +
                "    \"repository\": {\r\n" +
                "      \"object\": {\r\n" +
                "        \"text\": \"# Auto detect text files and perform LF normalization\\n* text=auto\\n\"\r\n" +
                "      }\r\n" +
                "    }\r\n" +
                "  }\r\n" +
                "}";
        System.out.println(s);

        //text만 뽑아오는 것.
        int a = s.indexOf("\"text\":");
        int b = s.indexOf("\"", a+9);
        String g = s.substring(a+9, b);
        System.out.println(g);

        return g;
    }

}
