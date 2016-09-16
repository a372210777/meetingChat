package com.example.model2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfLvHost;
import com.example.meeting.ExpandableAdapter;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * 获取观点分类
 * */
public class GetCategoryWorker2 implements Worker{

	private RoomData roomData;
	private Activity activity;
	private ArrayList<Group> parents;
	private ArrayList<Idea> listIdea;
	private boolean isShowToast=true;
	
	private AdapterOfLvHost adapter;
	
	public GetCategoryWorker2(RoomData room,Activity activity,
			ArrayList<Group> list,ArrayList<Idea> listIdea,AdapterOfLvHost adapter){
		this.roomData=room;
		this.activity=activity;
		this.parents=list;
		this.listIdea=listIdea;
		this.adapter=adapter;
	}
	public GetCategoryWorker2(RoomData room,Activity activity,
			ArrayList<Group> list,boolean isShowToast,ArrayList<Idea> listIdea,AdapterOfLvHost adapter){
		this.roomData=room;
		this.activity=activity;
		this.parents=list;
		this.isShowToast=isShowToast;
		this.listIdea=listIdea;
		this.adapter=adapter;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(roomData, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			if(isShowToast){
				Tool.showToast("网络异常！", activity);
			}
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			if(isShowToast){
				Tool.showToast("获取分类失败!"+json.getReason(), activity);
			}
			
		}else {
			if(isShowToast){
				Tool.showToast("获取分类成功", activity);
			}
			json.getIdeaGroup(parents);
			
			if(parents.size()==0 && listIdea.size()!=0){
				for(int i=0;i<listIdea.size();i++){
					listIdea.get(i).setCateName("无分类");
				}
			}else if(parents.size()!=0 && listIdea.size()!=0){
				for(int i=0;i<parents.size();i++){
					for(int j=0;j<listIdea.size();j++){
						if(listIdea.get(j).getCategory().equals(parents.get(i).getId())){
							listIdea.get(j).setCateName(parents.get(i).getName());
						}
					}
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

}
