package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class EndVote implements InitParam{

	private String roomId;
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("roomid", roomId));
	}

	@Override
	public String getUrl() {
		return Constant.MN_endVote;
	}

}
