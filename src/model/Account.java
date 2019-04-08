package model;

import java.util.ArrayList;

public class Account {
	private int id;
	private String userName;
    private String password;
	private ArrayList<Account> followers;

    
    public Account() {
    	id = -1;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public ArrayList<Account> getFollowers() {
		return followers;
	}
	public void setFollowers(ArrayList<Account> followers) {
		this.followers = followers;
	}
}
