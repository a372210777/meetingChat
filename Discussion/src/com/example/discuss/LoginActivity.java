package com.example.discuss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.utilities.Constant;

public class LoginActivity extends SherlockActivity implements OnClickListener {

	private EditText userName_et;
	private EditText password_et;
	private CheckBox remember_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setTitle("登录");

		userName_et = (EditText) this.findViewById(R.id.phoneNumber_et);
		password_et = (EditText) this.findViewById(R.id.pwd_et);
		remember_pwd = (CheckBox) findViewById(R.id.memoryPwd_cb);

		findViewById(R.id.register_btn).setOnClickListener(this);
		findViewById(R.id.login_btn).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_btn:
			Intent it2 = new Intent(this, RegisterActivity.class);
			startActivity(it2);
			break;
		case R.id.login_btn:
			if (userName_et.getText() == null
					|| userName_et.getText().toString().trim().equals("")) {
				Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
				return;
			}
			if (userName_et.getText().toString().trim().length() < 6
					|| userName_et.getText().toString().trim().length() > 12) {
				Toast.makeText(this, "用户名长度应大于6小于12", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (password_et.getText() == null
					|| password_et.getText().toString().trim().equals("")) {
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password_et.getText().toString().trim().length() < 6
					|| password_et.getText().toString().trim().length() > 12) {
				Toast.makeText(this, "密码长度应大于6小于12", Toast.LENGTH_SHORT).show();
				return;
			}
			new Task().execute("");
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	class Task extends AsyncTask<String, Integer, String> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(LoginActivity.this, "", "登陆中..", true,
					true);
		}

		@Override
		protected String doInBackground(String... params) {
			DefaultHttpClient mHttpClient = new DefaultHttpClient();

			HttpPost mPost = new HttpPost(Constant.MN_LOGIN);
			List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

			pairs.add(new BasicNameValuePair("username", userName_et.getText()
					.toString().trim()));
			pairs.add(new BasicNameValuePair("password", password_et.getText()
					.toString().trim()));
			try {
				mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				HttpResponse response = mHttpClient.execute(mPost);
				int res = response.getStatusLine().getStatusCode();
				if (res == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						CookieStore cookies = mHttpClient.getCookieStore();
						List<Cookie> cookies1 = null;
						;
						cookies1 = cookies.getCookies();
						if (!cookies1.isEmpty()) {
							for (int i = 0; i < cookies1.size(); i++) {
								String PHPSESSID = "";
								if ("PHPSESSID".equals(cookies1.get(i)
										.getName())) {
									PHPSESSID = cookies1.get(i).getValue();
									Log.w("AndroidRuntime", "cookies:"
											+ PHPSESSID);
									((DiscussApp) getApplication())
											.setPHPSESSID(PHPSESSID);
									break;
								}
							}
						}
						String info = EntityUtils.toString(entity);
						return info;
					}

				} else {
					return null;
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result == null) {
				Toast.makeText(LoginActivity.this, "登陆失败,网络异常",
						Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonObject = null;
			String state = "";
			try {
				jsonObject = new JSONObject(result);
				state = jsonObject.getString("state");
				Log.w("url_state", "" + state);
				if (state.equals("1")) {
					((DiscussApp) getApplication()).setUserName(userName_et
							.getText().toString().trim());
					Toast.makeText(LoginActivity.this, "登陆成功",
							Toast.LENGTH_SHORT).show();
					Intent it = new Intent(LoginActivity.this,
							EnterRoomActivity.class);
					startActivityForResult(it, Constant.EnterRoom);//启动页面 并传入页面标识
					// startActivity(it);
					// LoginActivity.this.finish();
				} else {
					Toast.makeText(LoginActivity.this,
							"登陆失败!\n" + jsonObject.getString("reason"),
							Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

		}

	}

	class Task2 extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);

		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {// 主页面重写该方法 来获得子页面传回来的数据
		switch (requestCode) {
		case Constant.EnterRoom://接受到来自进入房间页面返回的数据 且标识号为OK 
			if(resultCode==RESULT_OK){
				this.finish();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
}
