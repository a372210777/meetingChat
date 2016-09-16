package com.example.datamodel;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setField(JSONObject obj){
		
		try {
			this.userName=obj.getString("username");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
