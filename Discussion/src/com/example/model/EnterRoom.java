package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class EnterRoom implements InitParam{

	private String roomId;
	private String password;
//	private String sessionId;
	
	
	public EnterRoom(String id,String pwd){
		this.roomId=id;
		this.password=pwd;
//		this.sessionId=sessionId;
	}
	
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("roomid", roomId));
		pairs.add(new BasicNameValuePair("password", password));
	}

	@Override
	public String getUrl(){
		return Constant.MN_ENTERROOM;
	}
}
