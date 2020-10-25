package com.testing.dev.retrofigetpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testing.dev.retrofigetpost.retrofit.ApiClient;
import com.testing.dev.retrofigetpost.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users;
    private RetrofitInterface retrofitInterface;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getData();


    }

    private void getData() {

        retrofitInterface = ApiClient.getInstance().getApi();

        Call<List<User>> call =retrofitInterface.getData();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                users = response.body();
                userAdapter = new UserAdapter(users);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.userRecyclearview);
        users = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void insertData(View view) {

        Call<ResponseBody> call = retrofitInterface.insertData(1,10,"demo title", "demo body");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
