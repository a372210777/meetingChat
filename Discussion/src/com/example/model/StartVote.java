package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

/**
 * ¿ªÊ¼Í¶Æ±
 * */
public class StartVote implements InitParam{

	private String roomId;
	private String step;
	public StartVote(String roomId){
		this.roomId=roomId;
	}
	public StartVote(String roomId,String step){
		this.roomId=roomId;
		this.step=step;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
//		pairs.add(new BasicNameValuePair("roomid", roomId));
//		pairs.add(new BasicNameValuePair("step", step));
	}

	@Override
	public String getUrl() {
		return Constant.MN_startVote;
	}

}
