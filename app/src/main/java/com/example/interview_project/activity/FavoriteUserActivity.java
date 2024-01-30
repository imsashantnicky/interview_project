package com.example.interview_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interview_project.R;
import com.example.interview_project.adapters.FavoriteUserAdapter;
import com.example.interview_project.models.FavoriteUserDataModel;

import java.util.List;

public class FavoriteUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavoriteUserAdapter favoriteUserAdapter;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_user);

        List<FavoriteUserDataModel> favoriteUserList = getIntent().getParcelableArrayListExtra("favoriteUserList");

        if (favoriteUserList == null || favoriteUserList.isEmpty()) {
            Toast.makeText(this, "Favorite user list is empty", Toast.LENGTH_SHORT).show();
            noData = findViewById(R.id.noDataFoundTV);
            noData.setVisibility(View.VISIBLE);
        } else {
            recyclerView = findViewById(R.id.recv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            favoriteUserAdapter = new FavoriteUserAdapter(this, favoriteUserList);
            recyclerView.setAdapter(favoriteUserAdapter);
        }
    }

}
