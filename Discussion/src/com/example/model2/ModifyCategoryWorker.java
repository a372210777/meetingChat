package com.example.model2;

import android.app.Activity;

import com.example.model.ModifyCategory;
import com.example.model.ParseJson;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class ModifyCategoryWorker implements Worker {

	private ModifyCategory modifyCate;
	private Activity activity;
	public ModifyCategoryWorker(ModifyCategory modifyCate, Activity activity){
		this.modifyCate=modifyCate;
		this.activity=activity;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(modifyCate, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("网络异常！", activity);
			return;
		}
		Tool.showToast(result, activity);
		ParseJson json=new ParseJson(result, activity);
		if(json.getState().equals("0")){
			Tool.showToast("修改失败！"+json.getReason(), activity);
			return;
		}else{
			
			Tool.showToast("修改成功！", activity);
		}
	}

}
