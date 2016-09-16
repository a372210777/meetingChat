package com.example.discuss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.meeting.ChatActivity;
import com.example.model.CreateRoom;
import com.example.model.ParseJson;
import com.example.utilities.Constant;
import com.example.utilities.Tool;

public class CreateRoomActivity  extends SherlockActivity implements OnClickListener{

	private EditText roomName_et;
	private EditText roomSubject_et;
	private EditText roomPwd_et;
	private EditText ensurePwd_et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createroom);
		getSupportActionBar().setTitle("创建房间");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		findViewById(R.id.createRoom_btn).setOnClickListener(this);
		roomName_et = (EditText) this.findViewById(R.id.roomID_et);
		roomSubject_et = (EditText) this.findViewById(R.id.roomSubject_et);
		roomPwd_et = (EditText) this.findViewById(R.id.roomPwd_et);
		ensurePwd_et = (EditText) this.findViewById(R.id.ensurePwd_et);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			this.finish();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createRoom_btn:
			if(roomName_et.getText()==null || roomName_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入房间名", Toast.LENGTH_SHORT).show();
				return;
			}
			if(roomSubject_et.getText()==null || roomSubject_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入主题", Toast.LENGTH_SHORT).show();
				return;
			}
			if(roomPwd_et.getText()==null || roomPwd_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if(ensurePwd_et.getText()==null || ensurePwd_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!roomPwd_et.getText().toString().trim().equals(ensurePwd_et.getText().toString().trim())){
				Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
				return;
			}
			new Task().execute("");
			break;
		}
	}

	class Task extends AsyncTask<String, Integer, String>{

		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(CreateRoomActivity.this, "", "正在创建房间..", true,
					true);
		}
		@Override
		protected String doInBackground(String... params) {
			String respone=Tool.requestData(new CreateRoom(roomName_et.getText().toString().trim(),roomSubject_et.getText().toString().trim() ,
					roomPwd_et.getText().toString().trim()), CreateRoomActivity.this);
			return respone;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if(result==null){
				Tool.showToast("网络异常！", CreateRoomActivity.this);
				return;
			}
			Toast.makeText(CreateRoomActivity.this, result, Toast.LENGTH_LONG).show();
			Log.w("AndroidRuntime", result);
			ParseJson json=new ParseJson(result, CreateRoomActivity.this);
			if(json.getState().equals("0")){
				Tool.showToast(json.getReason(), CreateRoomActivity.this);
				return;
			}
			if(json.getState().equals("1")){
				Tool.showToast("进入房间", CreateRoomActivity.this);
				Intent enterIntent = new Intent(CreateRoomActivity.this, ChatActivity.class);
				enterIntent.putExtra("identity", Constant.HOST);//房主标识
				startActivity(enterIntent);
				CreateRoomActivity.this.finish();
			}
			
			
			
		}
	}
}
