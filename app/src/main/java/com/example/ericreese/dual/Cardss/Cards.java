package com.example.ericreese.dual.Cardss;

/**
 * Created by manel on 9/5/2017.
 */

//Class for each card
public class Cards {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String bio;

    public Cards(String userId, String name, String profileImageUrl, String biography){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.bio = biography;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserID(String userID){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getBio() { return bio;}
    public void setBio(String biography) {this.bio = biography;}

    public String getProfileImageUrl(){
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}
