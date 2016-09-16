package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class CreateRoom implements InitParam{

	private String roomName;
	private String roomTheme;
	private String pwd;
	
	public CreateRoom(String name,String theme,String pwd){
		this.roomName=roomName;
		this.roomTheme=theme;
		this.pwd=pwd;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("theme", roomTheme));
		pairs.add(new BasicNameValuePair("name",roomName));
		pairs.add(new BasicNameValuePair("password",pwd));
		pairs.add(new BasicNameValuePair("steptheme",""));
		pairs.add(new BasicNameValuePair("steptime",""));
	}

	@Override
	public String getUrl() {
		return Constant.MN_createRoom;
	}

}
