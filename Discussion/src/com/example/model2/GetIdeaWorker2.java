package com.example.model2;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfLvHost;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.example.utilities.Worker;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class GetIdeaWorker2 implements Worker{

	private Activity activity;
	private RoomData roomData;
	private ArrayList<Idea> list;
	private ArrayList<Group> listG;
	private PullToRefreshListView listView;
	private AdapterOfLvHost adapter;
	public GetIdeaWorker2(RoomData roomData ,Activity activity,ArrayList<Idea> list,
			PullToRefreshListView listView,AdapterOfLvHost adapter, ArrayList<Group> listG){
		this.roomData=roomData;
		this.activity=activity;
		this.list=list;
		this.adapter=adapter;
		this.listView=listView;
		this.listG=listG;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(roomData, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("�����쳣��", activity);
			return;
		}
		Log.w("AndroidRuntime", result);
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("1")){
			json.getIdea(list);
			if(list.size()==0){
				Tool.showToast("��δ���û�����۵㣡",activity);
			}
			//ÿ������idea��ͬʱ  ��ȡgroup Ŀ����ˢ��idea�����еķ�����
			RoomData rd=new RoomData();
			GetCategoryWorker2 work=new GetCategoryWorker2(rd, activity, listG,list,adapter);
			new NetWork(work, activity,false).execute("");
			adapter.notifyDataSetChanged();
			listView.onRefreshComplete();
			
		}else{
			Tool.showToast("��ȡʧ��!"+json.getReason(), activity);
		}
		
		
		
	}

}
