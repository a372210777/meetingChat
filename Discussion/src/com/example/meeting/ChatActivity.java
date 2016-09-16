package com.example.meeting;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.datamodel.CommonMes;
import com.example.datamodel.CurrentUserMes;
import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.datamodel.Message;
import com.example.datamodel.Room;
import com.example.discuss.DiscussApp;
import com.example.discuss.R;
import com.example.model.ParseJson;
import com.example.model.RoomData;
import com.example.model.SendMessage;
import com.example.model2.DialogExit;
import com.example.utilities.Constant;
import com.example.utilities.Tool;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ChatActivity extends SlidingFragmentActivity implements OnClickListener,Runnable{

	protected MoreMunuFragment mFrag;
	SlidingMenu sm;
	
	private EditText content_et;
	private ListView message_lv;
	private ChatMsgViewAdapter adapter;
	private ArrayList<Message> msList=new ArrayList<Message>();//其他人的消息
	private ArrayList<CurrentUserMes> CurMesList=new ArrayList<CurrentUserMes>();//我的消息
	private ArrayList<CommonMes> comList=new ArrayList<CommonMes>();//展现消息的数据源
	
	Room room=new Room();
	public Room getRoom() {
		return room;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		getSupportActionBar().setTitle("会议室");
		
		findViewById(R.id.btn_send).setOnClickListener(this);
		content_et=(EditText) findViewById(R.id.et_sendmessage);
		message_lv=(ListView) findViewById(R.id.listview);
		adapter=new ChatMsgViewAdapter(this,comList);
		message_lv.setAdapter(adapter);
		message_lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				return false;
			}
		});
		message_lv.setSelection(message_lv.getCount()-1);
		
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new  MoreMunuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (MoreMunuFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
		 sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		new Thread(this).start();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_send:
			if(content_et.getText()!=null && !content_et.getText().toString().trim().equals("")){
				
				
				String time=Tool.getCurrentDate();
				String user=((DiscussApp)getApplication()).getUserName();
				String content=content_et.getText().toString();
				
				CurrentUserMes mes=new CurrentUserMes(time,user,content);
				mes.setSending(true);
				CurMesList.add(mes);
				comList.add(mes);
				new sendMes(mes).execute("");
				
				adapter.notifyDataSetChanged();
				message_lv.setSelection(message_lv.getCount()-1);
			}
			break;
		}
	}
	/**
	 * 发送消息
	 * */
	class sendMes extends AsyncTask<String, Integer, String>{
		private CurrentUserMes mes;
		public sendMes(CurrentUserMes mes){
			this.mes=mes;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			String response=Tool.requestData(new SendMessage(content_et.getText().toString()), ChatActivity.this);
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			content_et.setText("");
			mes.setSending(false);
			adapter.hidePb();//通知适配器关闭进度圈
			if(result==null){
				Tool.showToast("网络异常！", ChatActivity.this);
				return;
			}
			ParseJson json=new ParseJson(result, ChatActivity.this);
			if(json.getState().equals("1")){//发送成功
				
			}else{
//				adapter.showWarm();
			}
			
		}
	}

	@Override
	public void run() {
		while(true){
			 //获取玩数据后要监听以刷新列表
			if(Tool.isNetConn(this)){//网络连接
				String response=Tool.requestData(new RoomData(), ChatActivity.this);
				ParseJson json=new ParseJson(response, this);
				if(json.getState().equals("1")){//获取数据成功
					json.getMessage(comList,adapter,this,message_lv);
					if(room.getName()==null){//获取房间信息
						json.getRoomData(room);
						this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								String userName=((DiscussApp)getApplication()).getUserName();
								if(room.getHost().equals(userName)){
									getIntent().putExtra("identity", Constant.HOST);
								}
								
							}
						});
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{
				
			}
			
			
		}
		
	}
	
	//返回键监听
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		 if (keyCode == KeyEvent.KEYCODE_BACK
//                 && event.getRepeatCount() == 0) {
//			 Tool.showToast("hello", this);
//             return true;
//         }
//		return super.onKeyUp(keyCode, event);
//	}
	
	@Override
	public void onBackPressed() {
		if (sm.isMenuShowing()) {
			sm.showContent();
		} else {
			Tool.showDialog(this, new DialogExit(this));
		}
	}
	
}
