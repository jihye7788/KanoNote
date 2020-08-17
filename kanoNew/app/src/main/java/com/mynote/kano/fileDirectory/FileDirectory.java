package com.mynote.kano.fileDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mynote.kano.R;
import com.mynote.kano.vo.Directory;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileDirectory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_directory);
    }

    //파일 디렉토리 형식 리턴
    public ArrayList<Directory> filedirectory() {
        //여기 원래 매개변수로 스트링 들어와야합니다.


        String s = "{\r\n" +
                "  \"data\": {\r\n" +
                "    \"repository\": {\r\n" +
                "      \"folder\": {\r\n" +
                "        \"entries\": [\r\n" +
                "          {\r\n" +
                "            \"oid\": \"4bbda6fe00cbaf3ccb855f1bf0b6f11f2a3e8b0e\",\r\n" +
                "            \"type\": \"blob\",\r\n" +
                "            \"name\": \".classpath\"\r\n" +
                "          },\r\n" +
                "          {\r\n" +
                "            \"oid\": \"dfe0770424b2a19faf507a501ebfc23be8f54e7b\",\r\n" +
                "            \"type\": \"blob\",\r\n" +
                "            \"name\": \".gitattributes\"\r\n" +
                "          },\r\n" +
                "          {\r\n" +
                "            \"oid\": \"fb819e8c0c4a01c8c0b81874dbb64555b2729887\",\r\n" +
                "            \"type\": \"blob\",\r\n" +
                "            \"name\": \"pom.xml\"\r\n" +
                "          },\r\n" +
                "          {\r\n" +
                "            \"oid\": \"a030d34ebfa2770668003db3c52f51d3c5444c11\",\r\n" +
                "            \"type\": \"tree\",\r\n" +
                "            \"name\": \"src\"\r\n" +
                "          },\r\n" +
                "          {\r\n" +
                "            \"oid\": \"56e44f2ac96c91d9fc6feed7839425f772ea3cef\",\r\n" +
                "            \"type\": \"tree\",\r\n" +
                "            \"name\": \"target\"\r\n" +
                "          }\r\n" +
                "        ]\r\n" +
                "      }\r\n" +
                "    }\r\n" +
                "  }\r\n" +
                "";
        //String ss = "aa";
        int a = s.indexOf("[");
        int b = s.indexOf("]");
        s = s.substring(a + 1, b);
        //System.out.println(s);

        ArrayList<String> list = new ArrayList();

        String[] array = s.split(Pattern.quote("},"));
        for (int i = 0; i < array.length; i++) {
            //System.out.println(i+".\n"+array[i]);
            list.add(array[i].trim());
        }

        ArrayList<Directory> dList = new ArrayList<Directory>();

        for (String ss : list) {
            Directory dd = new Directory();
            int c = ss.indexOf("\"oid\":");
            int d = ss.indexOf("\",");
            String g = ss.substring(c + 8, d);
            dd.setOid(g);
            //System.out.println(g);

            c = ss.indexOf("\"type\":");
            d = ss.indexOf("\",", c);
            g = ss.substring(c + 9, d);
            dd.setType(g);

            //System.out.println(g);
            c = ss.indexOf("\"name\":");
            d = ss.indexOf("\"", c + 9);
            g = ss.substring(c + 9, d);
            //System.out.println(g);
            dd.setName(g);
            dList.add(dd);
        }
        return dList;
    }


    public void onStart() {
        super.onStart();

        Directory directory;

        //받아온 디렉토리
        ArrayList<Directory> dList = filedirectory();

        // Adapter 생성
        DirectoryAdapter adapter = new DirectoryAdapter();

        // directory 참조 아이템 추가.
        for (Directory dic : dList) {
            adapter.addItem(dic.getName(), dic.getOid(), dic.getType());
            //ID 혹은 이름, type을 줘야함
        }
        ListView listView = findViewById(R.id.listview1);
        // 위에서 생성한 directory에 클릭 이벤트 핸들러 정의.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                Directory item = (Directory) parent.getItemAtPosition(position);
                Log.v("태그", item.toString());

                String name = item.getName();
                String type = item.getType();

                // TODO : use item data.

                //타입으로 이동 방향 거르기. blob은 파일, tree는 폴더
                if (type.equals("tree")) {
                    Intent intent = new Intent(getApplicationContext(), FileDirectory.class);
                    intent.putExtra("name", name); /*송신*/
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getApplicationContext(), dListActivity.class);
                    intent.putExtra("name", name); /*송신*/
                    startActivity(intent);
                }
            }
        });
        // Adapter달기

    }
}

