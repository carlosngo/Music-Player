package model;

import events.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class User {
    private int userId;
    private Account account;
    private String name;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private boolean isFollowed;
    private ArrayList<Song> library;
    private ArrayList<Album> albums;
    private ArrayList<Playlist> playlists;

    public User() {
        userId = -1;
        account = new Account();
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Account getAccount() {
    	return account;
    }

    public void setAccount(Account account) {
    	this.account = account;
    }

    public String getUserName() {
        return account.getUserName();
    }
    
    public void setUserName(String userName) {
    	account.setUserName(userName);
    }

    public String getPassword() {
        return account.getPassword();
    }

    public void setPassword(String password) {
        account.setPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public ArrayList<Account> getFollowers() {
        return account.getFollowers();
    }

    public void addFollower(Account follower) {
        account.getFollowers().add(follower);
    }

    public void removeFollower(Account follower) {
        account.getFollowers().remove(follower);
    }

    public static User parseUser(String s) {
        User user = new User();
        String[] biodata = s.split("\\|");
        user.getAccount().setId(Integer.parseInt(biodata[0]));
        user.setUserId(Integer.parseInt(biodata[1]));
        user.setFirstName(biodata[2]);
        user.setLastName(biodata[3]);
        user.setGender(biodata[4]);
        try {
            Date bday = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(biodata[5]);
            user.setBirthday(bday);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("parse exception");
        }
        return user;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getAccount().getId());
        sb.append("|");
        sb.append(getUserId());
        sb.append("|");
        sb.append(getFirstName());
        sb.append("|");
        sb.append(getLastName());
        sb.append("|");
        sb.append(getGender());
        sb.append("|");
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        sb.append(formatter.format(getBirthday()));
        return sb.toString();
    }
}
