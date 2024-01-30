package com.example.interview_project.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.interview_project.models.UserDataModel;
import com.example.interview_project.sharedPreference.MySharedPreferences;
import com.example.interview_project.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserRecvAdapter extends RecyclerView.Adapter<UserRecvAdapter.ViewHolder> {

    private final Context context;
    private List<UserDataModel> userList;
    private final MySharedPreferences preferences;
    private List<FavoriteUserDataModel> favoriteUserList;

    public UserRecvAdapter(Context context, List<UserDataModel> userList) {
        this.context = context;
        this.userList = userList;
        this.preferences = new MySharedPreferences(context); // Initialize MyPreferences
        this.favoriteUserList = new ArrayList<>(); // Initialize favoriteUserList
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userList != null) {
            UserDataModel user = userList.get(position);
            holder.bind(user);
        }
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public void setUserList(List<UserDataModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView userId;
        private final TextView userEmail;
        private final ImageView avatar;
        private final ImageView favoriteIcon; // Favorite icon ImageView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameTV);
            userId = itemView.findViewById(R.id.userIdTV);
            userEmail = itemView.findViewById(R.id.userEmailTV);
            avatar = itemView.findViewById(R.id.avatarIV);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }

        public void bind(UserDataModel user) {
            userName.setText(user.getFirstName() + " " + user.getLastName());
            userId.setText(String.valueOf(user.getId()));
            userEmail.setText(user.getEmail());
            Glide.with(context).load(user.getAvatar()).centerCrop().into(avatar);

            // Check if the user is in the favorite list and set the favorite icon accordingly
            boolean isFavorite = preferences.getFavoriteState(user.getId());
            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
            } else {
                favoriteIcon.setImageResource(R.drawable.favorite_border_icon);
            }

            // Set OnClickListener to handle favorite icon clicks
            favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favoriteIcon.getDrawable().getConstantState().equals(context.getResources().getDrawable(R.drawable.favorite_border_icon).getConstantState())) {
                        boolean currentFavoriteState = preferences.getFavoriteState(user.getId());
                        boolean newFavoriteState = !currentFavoriteState;
                        preferences.saveFavoriteState(user.getId(), newFavoriteState);
                        if (newFavoriteState) {
                            favoriteIcon.setImageResource(R.drawable.favorite_fill_icon);
                            favoriteUserList.add(new FavoriteUserDataModel(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getAvatar()));
                            Utils.saveFavoriteUserList(context, favoriteUserList); // Save favorite user list
                            Log.d("favoriteUserList", favoriteUserList.toString());
                        }
                    } else {
                        // Handle removing user from the favorite list if needed
                        boolean currentFavoriteState = preferences.getFavoriteState(user.getId());
                        boolean newFavoriteState = !currentFavoriteState;
                        preferences.saveFavoriteState(user.getId(), newFavoriteState);
                        if (!newFavoriteState) {
                            favoriteIcon.setImageResource(R.drawable.favorite_border_icon);
                            removeUserFromFavoriteList(user.getId());
                            Utils.saveFavoriteUserList(context, favoriteUserList); // Save favorite user list
                        }
                    }
                }
            });
        }

        // Method to check if the user is in the favorite user list
        private boolean isUserInFavoriteList(int userId) {
            for (FavoriteUserDataModel favoriteUser : favoriteUserList) {
                if (favoriteUser.getId() == userId) {
                    return true;
                }
            }
            return false;
        }
    }

        private void removeUserFromFavoriteList(int userId) {
        for (FavoriteUserDataModel favoriteUser : favoriteUserList) {
            if (favoriteUser.getId() == userId) {
                favoriteUserList.remove(favoriteUser);
                break;
            }
        }
    }
}
