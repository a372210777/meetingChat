package com.example.datamodel;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 观点
 * */
public class Idea implements Serializable {

	private String id;
	private String content;
	private String author;
	private String category;
	private String score1;// 平均分
	private String sd;// 标准差
	private String score2;// 每个人的投票分数"1,3,5"有一人投1分 一人投3 一人投5
	private String step;

	private int score;// 用户评分
	private RatingBar bar;
	private TextView cate__et;

	private String cateName="";
	
	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public TextView getCate__et() {
		return cate__et;
	}

	public void setCate__et(TextView cate__et) {
		this.cate__et = cate__et;
	}

	public RatingBar getBar() {
		return bar;
	}

	public void setBar(RatingBar bar) {
		this.bar = bar;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getScore1() {
		return score1;
	}

	public void setScore1(String score1) {
		this.score1 = score1;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getScore2() {
		return score2;
	}

	public void setScore2(String score2) {
		this.score2 = score2;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public void setField(JSONObject json) {
		try {
			id = json.getString("id");
			author = json.getString("author");
			content = json.getString("content");
			category = json.getString("category");
			score1 = json.getString("score1");
			sd = json.getString("sd");
			score2 = json.getString("score2");
			step = json.getString("step");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
