package com.example.whatsapp.Users;

public class Users {
        String profileps, username, email, password, userid, listMassege, status;

    public Users(String profileps, String username, String email, String password, String userid, String listMassege) {
        this.profileps = profileps;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.listMassege = listMassege;
    }

    public Users(String profileps, String username, String email, String password, String userid, String listMassege, String status) {
        this.profileps = profileps;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.listMassege = listMassege;
        this.status = status;
    }

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Users() {}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileps() {
        return profileps;
    }

    public void setProfileps(String profileps) {
        this.profileps = profileps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getListMassege() {
        return listMassege;
    }

    public void setListMassege(String listMassege) {
        this.listMassege = listMassege;
    }
}
