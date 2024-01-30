package com.example.interview_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.interview_project.R;
import com.example.interview_project.models.FavoriteUserDataModel;
import com.example.interview_project.sharedPreference.MySharedPreferences;

import java.util.List;

public class FavoriteUserAdapter extends RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder> {

    private final Context context;
    private final List<FavoriteUserDataModel> favoriteUserList;
    private final MySharedPreferences preferences;

    public FavoriteUserAdapter(Context context, List<FavoriteUserDataModel> favoriteUserList) {
        this.context = context;
        this.favoriteUserList = favoriteUserList;
        this.preferences = new MySharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteUserDataModel favoriteUser = favoriteUserList.get(position);
        holder.bind(favoriteUser);
    }

    @Override
    public int getItemCount() {
        return favoriteUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userId;
        private final TextView userEmail;
        private final ImageView avatar;
        private final ImageView favoriteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameTV);
            userId = itemView.findViewById(R.id.userIdTV);
            userEmail = itemView.findViewById(R.id.userEmailTV);
            avatar = itemView.findViewById(R.id.avatarIV);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }

        public void bind(FavoriteUserDataModel favoriteUser) {
            userName.setText(favoriteUser.getFirstName() + " " + favoriteUser.getLastName());
            userId.setText(String.valueOf(favoriteUser.getId()));
            userEmail.setText(favoriteUser.getEmail());
            Glide.with(context).load(favoriteUser.getAvatar()).centerCrop().into(avatar);

            boolean isFavorite = preferences.getFavoriteState(favoriteUser.getId());
            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
            } else {
                favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
            }

            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean currentFavoriteState = preferences.getFavoriteState(favoriteUser.getId());
                    boolean newFavoriteState = !currentFavoriteState;
                    preferences.saveFavoriteState(favoriteUser.getId(), newFavoriteState);
                    if (newFavoriteState) {
                        favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
                        addFavoriteItem(favoriteUser);
                    } else {
                        favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
                        removeFavoriteItem(favoriteUser);
                    }
                }
            });
        }
    }

    private void addFavoriteItem(FavoriteUserDataModel favoriteUser) {
        if (!favoriteUserList.contains(favoriteUser)) {
            favoriteUserList.add(favoriteUser);
            notifyDataSetChanged();
        }
    }

    private void removeFavoriteItem(FavoriteUserDataModel favoriteUser) {
        favoriteUserList.remove(favoriteUser);
        notifyDataSetChanged();
    }
}

