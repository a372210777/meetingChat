package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.utilities.Constant;
/**
 * ·¢ËÍÏûÏ¢
 * */
public class SendMessage implements InitParam{

	private String content;
	public SendMessage(String content){
		this.content=content;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("content", content));
	}

	@Override
	public String getUrl() {
		return Constant.MN_sendMessage;
	}

}
