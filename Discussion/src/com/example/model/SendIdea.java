package com.example.model;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.example.datamodel.Room;
import com.example.utilities.Constant;

public class SendIdea implements InitParam{

	private String idea;
	private Room room;
	private String author;
	public SendIdea(String idea,Room room,String author){
		this.idea=idea;
		this.room=room;
		this.author=author;
	}
	@Override
	public void init(List<BasicNameValuePair> pairs) {
		pairs.add(new BasicNameValuePair("idea", idea));
		pairs.add(new BasicNameValuePair("roomid", room.getId()));
		pairs.add(new BasicNameValuePair("step", room.getStep()));
		pairs.add(new BasicNameValuePair("author", author));
	}

	@Override
	public String getUrl() {
		return Constant.MN_sendIdea;
	}

}
