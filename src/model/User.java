package model;

import events.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class User {
    private int userId;
    private String userName;
    private String password;
    private String name;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private ArrayList<Song> library;
    private ArrayList<Album> albums;
    private ArrayList<Playlist> playlists;
    private ArrayList<User> followers;


    public User() {
        userId = -1;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void addFollower(User follower) {
        followers.add(follower);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
    }

    public static User parseUser(String s) throws ParseException {
        User user = new User();
        String[] biodata = s.split("\\|");
        user.setUserId(Integer.parseInt(biodata[0]));
        user.setUserName(biodata[1]);
        user.setPassword(biodata[2]);
        user.setFirstName(biodata[3]);
        user.setLastName(biodata[4]);
        user.setGender(biodata[5]);
        Date bday =new SimpleDateFormat("dd/MM/yyyy").parse(biodata[6]);
        user.setBirthday(bday);
        return user;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getUserId());
        sb.append("|");
        sb.append(getUserName());
        sb.append("|");
        sb.append(getPassword());
        sb.append("|");
        sb.append(getFirstName());
        sb.append("|");
        sb.append(getLastName());
        sb.append("|");
        sb.append(getGender());
        sb.append("|");
        sb.append(getBirthday());
        return sb.toString();
    }
}
