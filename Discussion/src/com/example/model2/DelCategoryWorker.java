package com.example.model2;

import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.model.DelCategory;
import com.example.model.ParseJson;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

/**
 * É¾³ý·ÖÀà 
 * */
public class DelCategoryWorker implements Worker{

	private DelCategory delCategory;
	private Activity activity;
	private  List<Group> list;
	private int position;
	
	public DelCategoryWorker(DelCategory delCategory,Activity activity, List<Group> list,int position){
		this.delCategory=delCategory;
		this.activity=activity;
		this.list=list;
		this.position=position;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(delCategory, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("ÍøÂçÒì³££¡", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("É¾³ýÊ§°Ü!"+json.getReason(), activity);
			return;
		}else{
			list.remove(position);
			Tool.showToast("É¾³ý³É¹¦£¡", activity);
		}
	}

}
