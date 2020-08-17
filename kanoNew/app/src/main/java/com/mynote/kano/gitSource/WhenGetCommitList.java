package com.mynote.kano.gitSource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.mynote.kano.GetCommitQuery;
import com.mynote.kano.R;
import com.mynote.kano.gitSource.gitConnection.GitConnectApplication;

public class WhenGetCommitList extends AppCompatActivity {

    public String dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터를 가져오는 방법
        WhenGetCommitList.NewThread newThread = new WhenGetCommitList.NewThread();
        newThread.setDaemon(true);

        //5월이라고 가정하면,

        String month_end = "2019-05-31T23:59:59";
        String month_start = "2019-05-01T00:00:00";

        newThread.run("master","jeongjiyoun","chieUniversity", month_start,month_end);

        synchronized (newThread) {
            try {
                newThread.wait(3000);
            } catch (Exception e) {
                Log.e("error", e.toString(), e);
            } finally {
                //text 세팅하는 방법 - 예시
/*                TextView textView = findViewById(R.id.textView3);*/
/*                textView.setText(dataString);*/
            }
        }
    }

    //CommitList
    class NewThread extends Thread {
        public void run(String branch_name,String owner_name, String repository_name, String month_start,String month_end) {

            GitConnectApplication gitConnectApplication = new GitConnectApplication();
            ApolloClient apolloClient = gitConnectApplication.getApolloClient();

            GetCommitQuery getQuery
                    = GetCommitQuery.builder()
                    .branch_name(branch_name)
                    .repository_name(repository_name)
                    .owner_name(owner_name)
                    .month_end(month_end)
                    .month_start(month_start)
                    .build();

            //loginId를 여기에 넣으시면 됩니다.
            apolloClient.query(getQuery).enqueue(new ApolloCall.Callback<GetCommitQuery.Data>() {
                @Override
                public void onResponse(Response<GetCommitQuery.Data> response) {
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
