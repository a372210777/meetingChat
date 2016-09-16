package com.example.meeting;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.datamodel.Room;
import com.example.discuss.DiscussApp;
import com.example.discuss.LoginActivity;
import com.example.discuss.R;
import com.example.model.ParseJson;
import com.example.model.SendIdea;
import com.example.utilities.Tool;

public class SendIdeaActivity extends SherlockActivity implements
		OnClickListener {

	private EditText content_et;
	private boolean isSending = false;
	private Room room;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendidea);
		getSupportActionBar().setTitle("���͹۵�");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		content_et = (EditText) findViewById(R.id.idea_et);
		room = (Room) getIntent().getSerializableExtra("room");

		findViewById(R.id.ques_content_tv2).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		menu.add("����").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		if (content_et.getText() == null
				|| content_et.getText().toString().trim().equals("")) {
			Tool.showToast("������۵�", this);
			return super.onOptionsItemSelected(item);
		}
		if (!isSending) {
			isSending = true;
			new SendIdeaWorker().execute("");
		} else {
			Tool.showToast("��һ���۵����ڷ���!�����ĵȴ�", this);
		}

		return super.onOptionsItemSelected(item);
	}

	class SendIdeaWorker extends AsyncTask<String, Integer, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(SendIdeaActivity.this, "", "������..",
					true, true);
		}

		@Override
		protected String doInBackground(String... params) {
			String userName = ((DiscussApp) getApplication()).getUserName();
			SendIdea si = new SendIdea(filterSpace(content_et.getText()
					.toString()), room, userName);
			String response = Tool.requestData(si, SendIdeaActivity.this);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			isSending = false;
			if (result == null) {
				Tool.showToast("�����쳣��", SendIdeaActivity.this);
				return;
			}
			ParseJson json = new ParseJson(result, SendIdeaActivity.this);
			if (json.getState().equals("1")) {// ���ͳɹ�
				Tool.showToast("���ͳɹ���", SendIdeaActivity.this);
				content_et.setText("");
			} else {
				Tool.showToast("����ʧ�ܣ�\n" + json.getReason(),
						SendIdeaActivity.this);
			}
			super.onPostExecute(result);

		}
	}

	public String filterSpace(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (!(str.charAt(i) == ' ')) {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ques_content_tv2:
			content_et.setFocusable(true);
			content_et.setFocusableInTouchMode(true);
			content_et.requestFocus();
			//���������
			InputMethodManager inputManager =
			(InputMethodManager) content_et.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(content_et, 0);
			break;
		}
	}
}
