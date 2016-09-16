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
import com.actionbarsherlock.view.Window;
import com.example.utilities.Constant;
import com.example.utilities.Tool;

public class RegisterActivity extends SherlockActivity implements OnClickListener{

	private EditText userName_et;
	private EditText password_et;
	private EditText ensurePas_et;
	private EditText email_et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_register);
		setSupportProgressBarIndeterminateVisibility(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("ע��");

		
		findViewById(R.id.reg_btn).setOnClickListener(this);
		findViewById(R.id.havaCount_btn).setOnClickListener(this);
		userName_et=(EditText) findViewById(R.id.register_phone_et);
		password_et=(EditText) findViewById(R.id.reg_pwd_et);
		ensurePas_et=(EditText) findViewById(R.id.reg_confirmpwd_et);
		email_et=(EditText) findViewById(R.id.reg_email_et);
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_btn:
			if(!Tool.isCorrentLen(userName_et.getText().toString())){
				showToast("�û�������Ӧ����6С��12");
				return;
			}
			if(!Tool.isCorrentLen(password_et.getText().toString())){
				showToast("���볤��Ӧ����6С��12");
				return;
			}
			if(!password_et.getText().toString().trim().equals(ensurePas_et.getText().toString().trim())){
				showToast("�����������벻һ��!");
				return;
			}
			if(email_et.getText()==null || email_et.getText().toString().equals("")){
				showToast("����������");
				return;
			}
			if(!Tool.isEmail(email_et.getText().toString())){
				showToast("��������ȷ�������ʽ");
				return;
			}
			new Task().execute("");
			break;
		case R.id.havaCount_btn:
			this.finish();
			break;
		case android.R.id.home:
			this.finish();
			break;
		}		
	}
	
	class Task extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setSupportProgressBarIndeterminateVisibility(true);
		}
		@Override
		protected String doInBackground(String... params) {
			DefaultHttpClient mHttpClient = new DefaultHttpClient();

			HttpPost mPost = new HttpPost(Constant.MN_REGISTER);
			List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

			pairs.add(new BasicNameValuePair("username", userName_et.getText().toString().trim()));
			pairs.add(new BasicNameValuePair("password", password_et.getText().toString().trim()));
			pairs.add(new BasicNameValuePair("email", email_et.getText().toString().trim()));
			try {
				mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				HttpResponse response = mHttpClient.execute(mPost);
				int res = response.getStatusLine().getStatusCode();
				if (res == 200) {
					Log.w("url_success", "1111");
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						// session-cookie��ʼ��
						// AndroidSession appCookies = ((AndroidSession)
						// getApplication());
						CookieStore cookies = mHttpClient.getCookieStore();
						// appCookies.setCookie(cookies);
						List<Cookie> cookies1=null;;
						cookies1 = cookies.getCookies();
						if (!cookies1.isEmpty()) {
							for (int i = 0; i < cookies1.size(); i++) {
								// �����Ƕ�ȡCookie['PHPSESSID']��ֵ���ھ�̬�����У���֤ÿ�ζ���ͬһ��ֵ
								String PHPSESSID="";
								if ("PHPSESSID".equals(cookies1.get(i)
										.getName())) {
									 PHPSESSID = cookies1.get(i).getValue();
									 ((DiscussApp)getApplication()).setPHPSESSID(PHPSESSID);
									 ((DiscussApp)getApplication()).setUserName(userName_et.getText().toString().trim());
									 Log.w("url_COOKIES", ""+PHPSESSID);
									break;
								}
							}
						}
						// ��ʼ������
						String info = EntityUtils.toString(entity);
						//����JSON��Ϣ
						System.out.println("info-----------" + info);
						Log.w("url_info", ""+info);
						return info;
					}

				}else{
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
			setSupportProgressBarIndeterminateVisibility(false);
			if(result==null){
				Toast.makeText(RegisterActivity.this, "ע��ʧ��,�����쳣", Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonObject = null;
			String state = "";
			try {
				jsonObject = new JSONObject(result);
				state = jsonObject.getString("state");
				Log.w("url_state", ""+state);
				if(state.equals("1")){
					Toast.makeText(RegisterActivity.this, "ע��ɹ�", Toast.LENGTH_LONG).show();
//					Intent it=new Intent(RegisterActivity.this,EnterRoomActivity.class);
//					startActivity(it);
					RegisterActivity.this.finish();
				}else{
					Toast.makeText(RegisterActivity.this, "ע��ʧ��!\n"+jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

			
			
		}

		 
	}
	
	public void showToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
