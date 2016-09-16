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
	private int position;//listView���λ��
	private int which;//�ƶ����൯���б�λ��
	
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
			Tool.showToast("�����쳣��", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("�ƶ�ʧ��!"+json.getReason(), activity);
		}else {
			listIdea.get(position-1).setCategory(listG.get(which).getId());
			listIdea.get(position-1).setCateName(listG.get(which).getName());
//			listIdea.get(position-1).getCate__et().setText(listG.get(which).getName());
			Tool.showToast("�ƶ��ɹ�", activity);
			adapter.notifyDataSetChanged();
		}
	}

}
