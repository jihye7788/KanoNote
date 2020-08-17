package com.mynote.kano.gitSource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.mynote.kano.GetBranchQuery;
import com.mynote.kano.R;
import com.mynote.kano.gitSource.gitConnection.GitConnectApplication;

public class WhenGetBranch extends AppCompatActivity {

    public String dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터를 가져오는 방법
        WhenGetBranch.NewThread newThread = new WhenGetBranch.NewThread();
        newThread.setDaemon(true);

        newThread.run("jeongjiyoun","chieUniversity");

        synchronized (newThread) {
            try {
                newThread.wait(3000);
            } catch (Exception e) {
                Log.e("error", e.toString(), e);
            } finally {
                //This is sample how to use data!
                TextView textView = findViewById(R.id.textView2);
                textView.setText(dataString);
            }
        }
    }

    class NewThread extends Thread {
        public void run(String owner_name, String repository_name) {

            GitConnectApplication gitConnectApplication = new GitConnectApplication();
            ApolloClient apolloClient = gitConnectApplication.getApolloClient();

            GetBranchQuery getQuery
                    = GetBranchQuery.builder()
                    .owner_name(owner_name)
                    .repository_name(repository_name)
                    .build();

            apolloClient.query(getQuery).enqueue(new ApolloCall.Callback<GetBranchQuery.Data>() {
                @Override
                public void onResponse(Response<GetBranchQuery.Data> response) {
                    String respon = response.data().toString();
                    dataString = respon;
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
