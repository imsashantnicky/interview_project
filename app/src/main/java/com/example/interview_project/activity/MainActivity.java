package com.example.interview_project.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interview_project.R;
import com.example.interview_project.adapters.UserRecvAdapter;
import com.example.interview_project.models.FavoriteUserDataModel;
import com.example.interview_project.models.UserDataModel;
import com.example.interview_project.utils.Utils;
import com.example.interview_project.viewModels.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DataViewModel dataViewModel;
    private RecyclerView recyclerView;
    private UserRecvAdapter userRecvAdapter;
    private AppCompatButton favoriteButton;
    private TextView noData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noData = findViewById(R.id.noDataFoundTV);
        favoriteButton = findViewById(R.id.favoritButton);
        recyclerView = findViewById(R.id.recv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecvAdapter = new UserRecvAdapter(this, null); // Initialize with null data
        recyclerView.setAdapter(userRecvAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        if (isInternetAvailable()) {
            showProgressDialog(); // Show progress dialog if internet is available
            dataViewModel.getUserDataListObserver().observe(this, new Observer<List<UserDataModel>>() {
                @Override
                public void onChanged(List<UserDataModel> userDataModels) {
                    hideProgressDialog(); // Hide progress dialog after data is fetched
                    if (userDataModels != null) {
                        noData.setVisibility(View.GONE);
                        userRecvAdapter.setUserList(userDataModels);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dataViewModel.getUsersData(2);
        } else {
            hideProgressDialog(); // Hide progress dialog if internet is not available
            Toast.makeText(MainActivity.this, "Internet connection is not available", Toast.LENGTH_SHORT).show();
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FavoriteUserDataModel> favoriteUserList = Utils.getFavoriteUserList(MainActivity.this);
                if (favoriteUserList != null) {
                    Intent intent = new Intent(MainActivity.this, FavoriteUserActivity.class);
                    intent.putParcelableArrayListExtra("favoriteUserList", (ArrayList<? extends Parcelable>) favoriteUserList);
                    startActivity(intent);
                } else {
                    Log.d("favoriteUserList", "favoriteUserList is null");
                    Toast.makeText(MainActivity.this, "No favorite users found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
