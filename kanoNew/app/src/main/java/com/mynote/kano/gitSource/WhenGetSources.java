package com.mynote.kano.gitSource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.mynote.kano.GetFileSourceQuery;
import com.mynote.kano.R;
import com.mynote.kano.gitSource.gitConnection.GitConnectApplication;

public class WhenGetSources extends AppCompatActivity {

    public String dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터를 가져오는 방법
        WhenGetSources.NewThread newThread = new WhenGetSources.NewThread();
        newThread.setDaemon(true);

        newThread.run("jeongjiyoun","chieUniversity","dfe0770424b2a19faf507a501ebfc23be8f54e7b");

        synchronized (newThread) {
            try {
                newThread.wait(3000);
            } catch (Exception e) {
                Log.e("error", e.toString(), e);
            } finally {
                //text 세팅하는 방법 - 예시
                TextView textView = findViewById(R.id.textView2);
                textView.setText(dataString);
            }
        }
    }

    //Dirctory
    class NewThread extends Thread {
        public void run(String owner_name, String repository_name, String file_oid) {

            GitConnectApplication gitConnectApplication = new GitConnectApplication();
            ApolloClient apolloClient = gitConnectApplication.getApolloClient();

            GetFileSourceQuery getQuery
                    = GetFileSourceQuery.builder()
                    .owner_name(owner_name)
                    .repository_name(repository_name)
                    .file_oid(file_oid)
                    .build();


            //loginId를 여기에 넣으시면 됩니다.
            apolloClient.query(getQuery).enqueue(new ApolloCall.Callback<GetFileSourceQuery.Data>() {
                @Override
                public void onResponse(Response<GetFileSourceQuery.Data> response) {
                    //데이터를 가져오는 식
                    String k = response.data().toString();
                    dataString = k;
                    Log.v("?",k);
                    return;
                }

                @Override
                public void onFailure(ApolloException e) {
                    Log.e("1", e.getMessage(), e);
                }
            });
        }
    }
}
