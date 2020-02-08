package com.example.almighty.navigationdrawer;



import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("mobile")
    String mId;
    @SerializedName("pswrd")
    String pswrd;
    @SerializedName("action")
    String action;
    public User(String id,String pswrd,String action ) {
        this.mId = id;
        this.pswrd=pswrd;
        this.action=action;
    }

}

