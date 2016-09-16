package com.example.datamodel;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.discuss.DiscussApp;

import android.app.Activity;

/**
 * 消息对象
 * */
public class Message implements Serializable,CommonMes {

	private String id;
	private String author;
	private String content;
	private String time;
	
	private Activity activity;
	public Message(){}
	public Message(Activity activity){
		this.activity=activity;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setField(JSONObject json) {
		try {
			id = json.getString("id");
			author = json.getString("author");
			content = json.getString("content");
			time = json.getString("time");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSender() {
		String curentUser=((DiscussApp)activity.getApplication()).getUserName();
		if(author.equals(curentUser)){
			return true;
		}
		return false;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public String getContent() {
		return content;
	}
	@Override
	public boolean getIsSending() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setSending(boolean isSending) {
	}
	
	@Override
	public boolean isSuccess() {
		return false;
	}
	@Override
	public void setSuccess(boolean isSuccess) {
		
	}
	


}
