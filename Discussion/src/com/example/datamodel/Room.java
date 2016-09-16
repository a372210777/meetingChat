package com.example.datamodel;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * ·¿¼ä
 * */
public class Room implements Serializable{

	private String theme;
	private String step;
	private String host;
	private String id;
	private String closed;
	private String categoryId;
	private String ideaID;
	private String voteStart;
	private String voteEnd;
	private String voteNum;
	private String name;
	private String stepId;
	private String timeStart;
	private String timeEnd;
	private String duration;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClosed() {
		return closed;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getIdeaID() {
		return ideaID;
	}

	public void setIdeaID(String ideaID) {
		this.ideaID = ideaID;
	}

	public String getVoteStart() {
		return voteStart;
	}

	public void setVoteStart(String voteStart) {
		this.voteStart = voteStart;
	}

	public String getVoteEnd() {
		return voteEnd;
	}

	public void setVoteEnd(String voteEnd) {
		this.voteEnd = voteEnd;
	}

	public String getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(String voteNum) {
		this.voteNum = voteNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setField(JSONObject json) {
		try {
			theme = json.getString("theme");
			step = json.getString("step");
			host = json.getString("host");
			id = json.getString("id");
			closed = json.getString("closed");
			categoryId = json.getString("categoryid");
			ideaID = json.getString("ideaid");
			voteStart = json.getString("votestart");
			voteEnd = json.getString("voteend");
			voteNum = json.getString("votenum");
			name = json.getString("name");
			stepId = json.getString("stepid");
			timeStart = json.getString("timestart");
			timeEnd = json.getString("timeend");
			duration = json.getString("duration");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
