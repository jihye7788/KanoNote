package com.mynote.kano.gitSource.gitConnection;

import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GitConnectApplication {
    /*github graphQL 주소*/

    private static final String BASE_URL = "https://api.github.com/graphql";

    private static String authHeader = "bearer ";
    private static ApolloClient apolloClient;
    private static OkHttpClient okHttpClient;


    //Creating a Client
    public GitConnectApplication() {

      /*
      With the installation complete and schema downloaded,
      let's create your Apollo Client.
      ApolloClient uses OkHttp under the hood for handling network requests.
      So you will need to create an instance of the OkHttpClient
      and pass it to the ApolloClient builder.
       */

        /*create an instance of the OkHttpClient*/

        okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            /*
                              This android project Java version does not supports Lambda Expressions
                              (it needs at least java 7) : lambda expressions are not supported at language level '7'
                              so you have to use @override annotation
            */
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder().method(original.method(), original.body());
/*                      because you need an authorization to access Github GraphQL,
                      you put a data into header */
                        builder.header("User-Agent", "Android Apollo Client");
                        builder.header("Authorization", authHeader);
                        return chain.proceed(builder.build());
                    }
                }).build();

        if (okHttpClient != null) {
            /*pass instance of the OkHttpClient to the ApolloClient builder*/
            apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(okHttpClient)
                    .build();
        }
    }

    public ApolloClient getApolloClient() {
        return apolloClient;
    }

}
