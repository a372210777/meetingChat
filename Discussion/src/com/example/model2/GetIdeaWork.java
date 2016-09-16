package com.example.model2;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfIdeaLv;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.Tool;
import com.example.utilities.Worker;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class GetIdeaWork implements Worker{

	private Activity activity;
	private RoomData roomData;
	private ArrayList<Idea> list;
	private PullToRefreshListView listView;
	private AdapterOfIdeaLv adapter;
	public GetIdeaWork(RoomData roomData ,Activity activity,ArrayList<Idea> list,
			PullToRefreshListView listView,AdapterOfIdeaLv adapter){
		this.roomData=roomData;
		this.activity=activity;
		this.list=list;
		this.adapter=adapter;
		this.listView=listView;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(roomData, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){
			list.clear();
			json.getIdea(list);
			if(list.size()==0){
				Tool.showToast("还未有用户发表观点！",activity);
			}
			ArrayList<Group> listg=new ArrayList<Group>();
			json.getIdeaGroup(listg);
			if(listg.size()!=0 && list.size()!=0){
				for(int i=0;i<listg.size();i++){
					for(int j=0;j<list.size();j++){
						if(list.get(j).getCategory().equals(listg.get(i).getId())){
							list.get(j).setCateName(listg.get(i).getName());
						}
					}
				}
			}
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete();
		}else{
			Tool.showToast("获取失败!"+json.getReason(), activity);
		}
	
		
	}

}
