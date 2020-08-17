package com.mynote.kano.fileSource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mynote.kano.R;

public class FileSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_source);
    }

    public void filesource(){
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
    }



}
