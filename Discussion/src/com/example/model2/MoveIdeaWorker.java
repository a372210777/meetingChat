package com.example.model2;

import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfLvHost;
import com.example.model.MoveIdea;
import com.example.model.ParseJson;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class MoveIdeaWorker implements Worker{

	private Activity activity;
	private MoveIdea mi;
	
	private List<Group> listG;
	private List<Idea> listIdea;
	private int position;//listView点击位置
	private int which;//移动分类弹出列表位置
	
	private AdapterOfLvHost adapter;
	
	public MoveIdeaWorker(Activity activity,MoveIdea mi,List<Group> listG,List<Idea> listIdea,int position,int which,AdapterOfLvHost adapter){
		this.activity=activity;
		this.mi=mi;
		this.position=position;
		this.listG=listG;
		this.listIdea=listIdea;
		this.which=which;
		this.adapter=adapter;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(mi, activity);
	}

	@Override
	public void parseResult(String result) {
		
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("移动失败!"+json.getReason(), activity);
		}else {
			listIdea.get(position-1).setCategory(listG.get(which).getId());
			listIdea.get(position-1).setCateName(listG.get(which).getName());
//			listIdea.get(position-1).getCate__et().setText(listG.get(which).getName());
			Tool.showToast("移动成功", activity);
			adapter.notifyDataSetChanged();
		}
	}

}
