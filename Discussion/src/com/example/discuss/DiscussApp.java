package com.example.discuss;


import android.app.Application;

/**
 * 全局类  所用activity共享该类中的所有变量
 * */
public class DiscussApp extends Application{
	
	private String PHPSESSID="";
	private String userName="";
	public String getPHPSESSID() {
		return PHPSESSID;
	}
	public void setPHPSESSID(String pHPSESSID) {
		PHPSESSID = pHPSESSID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
