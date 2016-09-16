package com.example.model2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.ExpandableAdapter;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * ��ȡ�۵����
 * */
public class GetCategoryWork implements Worker{

	private RoomData roomData;
	private Activity activity;
	
	private ArrayList<Group> parents;
	private ArrayList<List<Idea>> childs ;
	private  ExpandableAdapter adapter;
	
	public GetCategoryWork(RoomData room,Activity activity,
			ArrayList<Group> list,ArrayList<List<Idea>> childs,ExpandableAdapter adapter){
		this.roomData=room;
		this.activity=activity;
		this.parents=list;
		this.childs=childs;
		this.adapter=adapter;
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
		Tool.showToast(result, activity);
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("��ȡ����ʧ��!"+json.getReason(), activity);
		}else {
			String res=json.getIdeaGroup(parents);
			if(res==null){
				Tool.showToast("��ǰ���䲻���ڷ���!", activity);
				Group g=new Group();
				g.setId("id");g.setName("Ĭ��");
				parents.add(g);
				
				ArrayList<Idea> list=new ArrayList<Idea>();
				json.getIdea(list);
				childs.add(list);
				
			}else{
				Tool.showToast("��ȡ����ɹ���", activity);
				
				ArrayList<Idea> list=new ArrayList<Idea>();
				json.getIdea(list);
				childs.add(list);
			}
		}
		adapter.notifyDataSetChanged();
	}

}
