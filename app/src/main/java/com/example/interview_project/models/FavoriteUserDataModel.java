package com.example.interview_project.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteUserDataModel implements Parcelable {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public FavoriteUserDataModel(int id, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    protected FavoriteUserDataModel(Parcel in) {
        id = in.readInt();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        avatar = in.readString();
    }

    public static final Creator<FavoriteUserDataModel> CREATOR = new Creator<FavoriteUserDataModel>() {
        @Override
        public FavoriteUserDataModel createFromParcel(Parcel in) {
            return new FavoriteUserDataModel(in);
        }

        @Override
        public FavoriteUserDataModel[] newArray(int size) {
            return new FavoriteUserDataModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(avatar);
    }
}
