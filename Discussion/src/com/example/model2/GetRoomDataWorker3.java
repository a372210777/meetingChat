package com.example.model2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.indexlistview.PinyinComparator;
import com.example.meeting.ResultListAdapter;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.utilities.Tool;
import com.example.utilities.Worker;

public class GetRoomDataWorker3 implements Worker{

	private Activity activity;
	private RoomData rd;
	
	private  ArrayList<Idea> listIdea;
	private ArrayList<Group> listG;
	
	private ResultListAdapter adapter;
	
	public GetRoomDataWorker3(RoomData rd,Activity activity,ArrayList<Idea> listIdea,ArrayList<Group> listG
			, ResultListAdapter adapter){
		this.activity=activity;
		this.rd=rd;
		this.listG=listG;
		this.listIdea=listIdea;
		this.adapter=adapter;
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
		if(json.getState().equals("1")){	
			json.getIdea(listIdea);
			json.getIdeaGroup(listG);
			sortList();
			adapter.notifyDataSetChanged();
		}else{
			Tool.showToast("获取数据失败!"+json.getReason(), activity);
		}
	}

	public void sortList(){
		
		 //平均分和SD 只保留两位小数
		for(int i=0;i<listIdea.size();i++){
			String score=listIdea.get(i).getScore1()+"";
			String sd=listIdea.get(i).getSd();
			if(score.length()>3){
				listIdea.get(i).setScore1(score.substring(0, 3)+"");
			}
			if(sd.length()>3){
				listIdea.get(i).setSd(sd.substring(0,3));
			}
		
			
		}
		
		//给每个idea初始化分类名称
		for(int i=0;i<listG.size();i++){
			for(int j=0;j<listIdea.size();j++){
				if(listG.get(i).getId().equals(listIdea.get(j).getCategory())){
					listIdea.get(j).setCateName(listG.get(i).getName());
				}
			}
		}
		//分类名称数组
		String[] str = new String[listG.size()];
		for (int i = 0; i < listG.size(); i++) {
			str[i] = listG.get(i).getName();
		}
		
		Arrays.sort(str, new PinyinComparator());
		ArrayList<Idea> tempList=new ArrayList<Idea>();
		for(int i=0;i<str.length;i++){
			for(int j=0;j<listIdea.size();j++){
				ArrayList<Idea> tempList2=new ArrayList<Idea>(); 
				if(listIdea.get(j).getCateName().equals(str[i])){
					tempList2.add(listIdea.get(j));
//					tempList.add(listIdea.get(j));
				}
				sorrBubble(tempList2);
				tempList.addAll(tempList2);
			}
		}
		listIdea.clear();
		listIdea.addAll(tempList);
	}
	
/**
 * 冒泡排序
 * */
	public static void sorrBubble(ArrayList<Idea> list){
		Idea[] id=new Idea[list.size()];
		for(int i=0;i<list.size();i++){
			id[i]=list.get(i);
		}
		for(int i=id.length;i>0;i--){
			for(int j=0;j<i;j++){
				if(j+1<i &&  id[j].getScore()<id[j+1].getScore()){
					Idea temp=id[j];
					id[j]=id[j+1];
					id[j+1]=temp;
				}
			}
		}
		list.clear();
		for(int i=0;i<id.length;i++){
			list.add(id[i]);
		}
		
	}
	public int  toInt(String str){
		return Integer.parseInt(str.trim());
	}
}
