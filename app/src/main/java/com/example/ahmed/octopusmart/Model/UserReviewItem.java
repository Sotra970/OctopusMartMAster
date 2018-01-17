package com.example.ahmed.octopusmart.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ahmed.octopusmart.Model.ServiceModels.UserModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmed on 20/12/2017.
 */

public class UserReviewItem implements Parcelable{

    private String comment;
    @SerializedName("created_at")
    private String date ;
    private int id;
    private String name;
    UserModel user ;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    protected UserReviewItem(Parcel in) {
        comment = in.readString();
        date = in.readString();
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<UserReviewItem> CREATOR = new Creator<UserReviewItem>() {
        @Override
        public UserReviewItem createFromParcel(Parcel in) {
            return new UserReviewItem(in);
        }

        @Override
        public UserReviewItem[] newArray(int size) {
            return new UserReviewItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(date);
        dest.writeString(name);
        dest.writeInt(id);

    }
}
