package com.example.discuss;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.example.model.EnterRoom;
import com.example.model.ParseJson;
import com.example.utilities.Constant;
import com.example.utilities.Tool;

public class EnterRoomActivity extends SherlockActivity implements OnClickListener{

	private EditText roomId_et;
	private EditText roomPwd_et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enterroom);
		getSupportActionBar().setTitle("登陆房间");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		findViewById(R.id.enterRoom_btn).setOnClickListener(this);
		roomId_et=(EditText) findViewById(R.id.roomID_et);
		roomPwd_et=(EditText) findViewById(R.id.roomPwd_et);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 menu.add("创建")
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			
			this.finish();
		}
		if(item.getTitle().equals("创建")){
			Intent it2 = new Intent(this, CreateRoomActivity.class);
			startActivity(it2);
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.enterRoom_btn:
			if(roomId_et.getText()==null || roomId_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入房间名", Toast.LENGTH_SHORT).show();
				return;
			}
			if(roomPwd_et.getText()==null || roomPwd_et.getText().toString().trim().equals("")){
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
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
			dialog = ProgressDialog.show(EnterRoomActivity.this, "", "正在进入房间..", true,
					true);
		}
		@Override
		protected String doInBackground(String... params) {
			String response=Tool.requestData(new EnterRoom(roomId_et.getText().toString(), roomPwd_et.getText().toString()), EnterRoomActivity.this);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			super.onPostExecute(result);
			if(result==null){
				Tool.showToast("网络异常！", EnterRoomActivity.this);
				return;
			}
			ParseJson json=new ParseJson(result, EnterRoomActivity.this);
			if(json.getState().equals("0")){
				Tool.showToast(json.getReason(), EnterRoomActivity.this);
				return;
			}
			if(json.getState().equals("1")){
				Intent enterIntent = new Intent(EnterRoomActivity.this, ChatActivity.class);
				enterIntent.putExtra("identity", Constant.AUDIENT);//房主标识
				startActivity(enterIntent);
				setResult(RESULT_OK);
				EnterRoomActivity.this.finish();
			}
			
		}
	}

	
	
}
