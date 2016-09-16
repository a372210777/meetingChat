package com.example.model2;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.TextView;

import com.example.datamodel.User;
import com.example.model.ParseJson;
import com.example.model.RoomData2;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class GetRoomDataWorker6 implements Worker{

	private Activity activity;
	private RoomData2 rd;
	private ArrayList<User> list;
	private TextView onLine_tv;
	public GetRoomDataWorker6(Activity activity,RoomData2 rd,ArrayList<User> list,TextView onLine_tv){
		
		this.activity=activity;
		this.rd=rd;
		this.list=list;
		this.onLine_tv=onLine_tv;
	}
	@Override
	public String getResponse() {
		return Tool.requestData(rd, activity);
	}

	@Override
	public void parseResult(String result) {
		if(result==null){
			Tool.showToast("网络异常！无法投票", activity);
			return;
		}
		ParseJson json=new ParseJson(result, activity);
		json.getOnlineUser(list);
		onLine_tv.setText(list.size()+"");
	}

}
