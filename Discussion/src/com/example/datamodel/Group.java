package com.example.datamodel;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 观点类别
 * */
public class Group implements Serializable {

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setField(JSONObject json) {
		try {
			id = json.getString("id");
			name = json.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
