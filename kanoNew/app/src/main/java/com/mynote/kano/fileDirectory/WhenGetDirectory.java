package com.mynote.kano.fileDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.mynote.kano.GetDirectoryQuery;
import com.mynote.kano.R;
import com.mynote.kano.gitSource.gitConnection.GitConnectApplication;
import com.mynote.kano.vo.Directory;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class WhenGetDirectory extends AppCompatActivity {

    public String dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_directory);
    }

    //Dirctory
    class NewThread extends Thread {
        public void run(String owner_name, String repository_name, String url) {

            GitConnectApplication gitConnectApplication = new GitConnectApplication();
            ApolloClient apolloClient = gitConnectApplication.getApolloClient();

            GetDirectoryQuery getQuery
                    = GetDirectoryQuery.builder()
                    .owner_name(owner_name)
                    .repository_name(repository_name)
                    .url(url)
                    .build();

            apolloClient.query(getQuery).enqueue(new ApolloCall.Callback<GetDirectoryQuery.Data>() {
                @Override
                public void onResponse(Response<GetDirectoryQuery.Data> response) {
                    //데이터를 가져오는 식
                    dataString = response.data().toString();
                    return;
                }

                @Override
                public void onFailure(ApolloException e) {
                    Log.e("1", e.getMessage(), e);
                }
            });
        }
    }


    public void onStart() {
        super.onStart();

        //데이터를 가져오는 방법
        WhenGetDirectory.NewThread newThread = new WhenGetDirectory.NewThread();
        newThread.setDaemon(true);

        Intent intent = getIntent();
        String url = "master:";
        try{
            String urlMake = intent.getExtras().getString("name");
            url+= urlMake + "/";
        } catch(RuntimeException e) {
            //ignore
        }
        newThread.run("jeongjiyoun","chieUniversity",url);

        synchronized (newThread) {
            try {
                newThread.wait(3000);
            } catch (Exception e) {
                Log.e("error", e.toString(), e);
            } finally {
                //text 세팅하는 방법 - 예시
                ArrayList<Directory> dList = filedirectory(dataString);
                getFileDirectorySource(dList);
            }
        }
    }

    private void getFileDirectorySource(ArrayList<Directory> dList){
        Directory directory;

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

    //파일 디렉토리 형식 리턴
    public ArrayList<Directory> filedirectory(String fileDirectory) {
        //여기 원래 매개변수로 스트링 들어와야합니다.
        int a = fileDirectory.indexOf("[");
        int b = fileDirectory.indexOf("]");
        fileDirectory = fileDirectory.substring(a + 1, b);

        ArrayList<String> list = new ArrayList();

        String[] array = fileDirectory.split(Pattern.quote("},"));
        for (int i = 0; i < array.length; i++) {
            list.add(array[i].trim());
        }

        Log.v("null체크",list.get(0));

        ArrayList<Directory> dList = new ArrayList<Directory>();

        for (String ss : list) {
            Directory dd = new Directory();
            int c = ss.indexOf("oid=");
            int d = ss.indexOf(",",c);
            String g = ss.substring(c+4, d);
            dd.setOid(g);

            c = ss.indexOf("type=");
            d = ss.indexOf(",", c);
            g = ss.substring(c + 5, d);
            dd.setType(g);

            c = ss.indexOf("name=");
            d = ss.indexOf("", c + 7);
            g = ss.substring(c + 7, d);

            dd.setName(g);
            dList.add(dd);
        }
        return dList;
    }


}
