package com.example.discuss;


import android.app.Application;

/**
 * ȫ����  ����activity��������е����б���
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
