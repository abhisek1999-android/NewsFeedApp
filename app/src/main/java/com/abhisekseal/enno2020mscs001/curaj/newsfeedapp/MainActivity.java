package com.abhisekseal.enno2020mscs001.curaj.newsfeedapp;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.Bean.News;
import com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.adapter.NewsRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private NewsApiClient newsApiClient ;
    private List<News> listFinal= new ArrayList<>();
    private  RecyclerView newsRecyclerView;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private EditText searchText;
    private FirebaseAuth mAuth;
    CustomProgressDialogue customProgressDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsApiClient = new NewsApiClient(getString(R.string.api_key));
        newsRecyclerView=findViewById(R.id.newsRecyclerView);
        searchText=findViewById(R.id.searchContent);
        mAuth=FirebaseAuth.getInstance();

        customProgressDialogue=new CustomProgressDialogue(MainActivity.this);
        customProgressDialogue.show();
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!searchText.getText().equals(""))
                     generateNewsFeed(searchText.getText().toString());
                else
                    generateNewsFeed("India");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        generateNewsFeed("India");
    }

    private void generateNewsFeed(String query) {

        listFinal.clear();
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {


                    @Override
                    public void onSuccess(ArticleResponse response) {


                        for (Article result:response.getArticles()){

                            listFinal.add(new News(result.getTitle(),result.getDescription(),
                                    result.getPublishedAt(),result.getSource().getName(),result.getUrlToImage()));

                            newsRecyclerAdapter=new NewsRecyclerAdapter(listFinal,MainActivity.this);
                            newsRecyclerView.setAdapter(newsRecyclerAdapter);

                            customProgressDialogue.dismiss();
                        }
//                        System.out.println(response.getArticles().get(0).getTitle());
//                        Toast.makeText(getApplicationContext(), response.getArticles().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                       // Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        customProgressDialogue.dismiss();
                    }
                }
        );

    }

    public void logOut(View view) {

        mAuth.signOut();
        finish();

    }
}