package com.example.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.example.datamodel.CommonMes;
import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.datamodel.Message;
import com.example.datamodel.Room;
import com.example.datamodel.User;
import com.example.meeting.ChatMsgViewAdapter;

public class ParseJson {

	private String JsonStr="";
	private Activity activity;
	public ParseJson(String str,Activity activity){
		this.JsonStr=str;
		this.activity=activity;
	}
	public void parse(){
		
	}
	public String getState(){
		JSONObject jsonObject = null;
		String state = null;
		try {
			jsonObject = new JSONObject(JsonStr);
			state = jsonObject.getString("state");
			return state;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return state;
	}
	public String getReason(){
		JSONObject jsonObject = null;
		String reason = null;
		try {
			jsonObject = new JSONObject(JsonStr);
			reason = jsonObject.getString("reason");
			return reason;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return reason;
	}
	/**
	 * 解析消息
	 * */
	public void getMessage(ArrayList<CommonMes> list,final ChatMsgViewAdapter adapter,Activity activity,final ListView listView){
		try {
			JSONObject jsonObject = new JSONObject(JsonStr) ;
			JSONArray array=jsonObject.getJSONArray("messages");
			if(list.size()<array.length()){
				for(int i=list.size();i<array.length();i++){
					JSONObject obj=array.getJSONObject(i);
					Message msg=new Message(activity);
					msg.setField(obj);
					list.add(msg);
				}
				activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
						listView.setSelection(listView.getCount()-1);
					}
				});
				
			}
		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 解析房间信息
	 * */
	public void getRoomData(Room room){
		try {
			JSONObject jsonObject = new JSONObject(JsonStr) ;
			JSONObject obj = jsonObject.getJSONObject("room") ;
			room.setField(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析idea分类
	 * */
	public String getIdeaGroup(ArrayList<Group> list){
		try {
			JSONObject jsonObject = new JSONObject(JsonStr) ;
			JSONArray array=jsonObject.getJSONArray("groups");
			if(array==null){
				return null;
			}
			if(list.size()<array.length()){
				for(int i=list.size();i<array.length();i++){
					JSONObject obj=array.getJSONObject(i);
					Group g=new Group();
					g.setField(obj);
					list.add(g);
				}
			}
		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * 解析ideas
	 * */
	public void getIdea(ArrayList<Idea> list){
		try {
			JSONObject jsonObject = new JSONObject(JsonStr) ;
			JSONArray array=jsonObject.getJSONArray("ideas");
			Log.w("AndroidRuntime", array.length()+" --"+JsonStr);
			if(list.size()<array.length()){
				for(int i=list.size();i<array.length();i++){
					JSONObject obj=array.getJSONObject(i);
					Idea g=new Idea();
					g.setField(obj);
					list.add(g);
				}
			}
		
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 获取在线人数
	 * */
	public void getOnlineUser(ArrayList<User> list){
		try {
			JSONObject jsonObject= new JSONObject(JsonStr);
			JSONArray array=jsonObject.getJSONArray("onlineusers");
			if(list.size()<array.length()){
				for(int i=list.size();i<array.length();i++){
					JSONObject obj=array.getJSONObject(i);
					User g=new User();
					g.setField(obj);
					list.add(g);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
