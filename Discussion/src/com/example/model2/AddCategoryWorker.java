package com.example.model2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.meeting.AdapterOfLvHost;
import com.example.model.AddCategory;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.NetWork;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * 添加分类
 * */
public class AddCategoryWorker implements Worker{

	private AddCategory addCategory;
	private Activity activity;
	private List<Group> list;
	private ArrayList<Idea> listIdea;
	private AdapterOfLvHost adapter;
	public AddCategoryWorker(AddCategory addCategory,Activity activity,List<Group> list,ArrayList<Idea> listIdea,AdapterOfLvHost adapter){
		this.addCategory=addCategory;
		this.activity=activity;
		this.list=list;
		this.listIdea=listIdea;
		this.adapter=adapter;
	}
	
	@Override
	public String getResponse() {
		String response=Tool.requestData(addCategory,activity );
		return response;
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast(json.getReason(), activity);
			return;
		}else{
			//添加分类后重新请求房间数据 刷新分类数组
			RoomData rd=new RoomData();
			GetCategoryWorker2 work=new GetCategoryWorker2(rd, activity, (ArrayList)list,false,listIdea,adapter);
			new NetWork(work, activity,false).execute("");
			
			Tool.showToast("添加分类成功！", activity);
		}
		
	}

}
