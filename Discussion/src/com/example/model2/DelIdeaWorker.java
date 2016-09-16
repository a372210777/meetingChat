package com.example.model2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfLvHost;
import com.example.model.DelIdea;
import com.example.model.ParseJson;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class DelIdeaWorker implements Worker{

	private DelIdea delIdea;
	private Activity activity;
	AdapterOfLvHost adapter;
	List<Idea> ideaLsit;
	int position;
	
	public DelIdeaWorker( DelIdea delIdea, Activity activity,AdapterOfLvHost adapter,List<Idea> ideaLsit,int position){
		this.delIdea=delIdea;
		this.activity=activity;
		this.adapter=adapter;
		this.ideaLsit=ideaLsit;
		this.position=position;
	}

	@Override
	public String getResponse() {
		return Tool.requestData(delIdea, activity);
	}
	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("ÍøÂçÒì³££¡", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("É¾³ýÊ§°Ü£¡"+json.getReason(), activity);
			return;
		}else{
			ideaLsit.remove(position-1);
			adapter.notifyDataSetChanged();
			Tool.showToast("É¾³ý³É¹¦£¡", activity);
		}
	}
}
