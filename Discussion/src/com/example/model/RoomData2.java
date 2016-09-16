package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;

public class RoomData2 implements InitParam {

	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("messageid", "0"));
		pairs.add(new BasicNameValuePair("getOnlineUsers", "true"));
		pairs.add(new BasicNameValuePair("ideaid", "0"));
		pairs.add(new BasicNameValuePair("groupid", "0"));
		pairs.add(new BasicNameValuePair("stepid", "0"));
	}

	@Override
	public String getUrl() {
		return Constant.MN_roomInfo;
	}

}
