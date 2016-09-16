//package com.example.discuss;
//
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.CookieStore;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.cookie.Cookie;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
////import java.net.CookieStore;
//import java.util.ArrayList;
//import java.util.List;
//
////import java.net.HttpCookie;
//
//public class AndroidLoginActivity extends Activity {
//	public static String MyCookie[];
//
//	private EditText username;
//
//	private EditText password;
//
//	private Button login;
//
//	private Button userregister;
//	private Intent intent;
//	public static String PHPSESSID = null;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.main);
//
//		login = (Button) findViewById(R.id.userlogin);
//
//		login.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//
//				username = (EditText) findViewById(R.id.username);
//				password = (EditText) findViewById(R.id.password);
//
//				if (checkUser()) {
//					System.out.println("你点击了按钮");
//
//					intent = new Intent(AndroidLoginActivity.this,
//							SecondActivity.class);
//
//					startActivity(intent);
//				} else {
//
//					Toast.makeText(AndroidLoginActivity.this, "用户登录信息错误",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//		});
//
//		userregister = (Button) findViewById(R.id.userregister1);
//
//		userregister.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				System.out.println("你点击了按钮");
//				intent = new Intent(AndroidLoginActivity.this,
//						RegisterActivity.class);
//
//				startActivity(intent);
//			}
//		});
//	}
//
//	private boolean checkUser() {
//
//		String username1 = username.getText().toString();
//
//		String pass = password.getText().toString();
//
//		DefaultHttpClient mHttpClient = new DefaultHttpClient();
//
//		HttpPost mPost = new HttpPost("http://brainstorming.sinaapp.com/user/login");
//
//		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
//
//		pairs.add(new BasicNameValuePair("username", username1));
//
//		pairs.add(new BasicNameValuePair("password", pass));
//
//		try {
//
//			mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
//
//		} catch (UnsupportedEncodingException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		}
//
//		try {
//
//			HttpResponse response = mHttpClient.execute(mPost);
//
//			int res = response.getStatusLine().getStatusCode();
//
//			if (res == 200) {
//
//				HttpEntity entity = response.getEntity();
//
//				if (entity != null) {
//					// session-cookie初始化
//					AndroidSession appCookies = ((AndroidSession) getApplication());
//					CookieStore cookies = mHttpClient.getCookieStore();
//					appCookies.setCookie(cookies);
//					List<Cookie> cookies1;
//					cookies1 = cookies.getCookies();
//					if (!cookies1.isEmpty()) {
//						for (int i = 0; i < cookies1.size(); i++) {
//							// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
//							if ("PHPSESSID".equals(cookies1.get(i).getName())) {
//								PHPSESSID = cookies1.get(i).getValue();
//								break;
//							}
//						}
//					}
//					// 初始化结束
//					String info = EntityUtils.toString(entity);
//
//					System.out.println("info-----------" + info);
//
//					JSONObject jsonObject = null;
//
//					String flag = "";
//
//					try {
//
//						jsonObject = new JSONObject(info);
//
//						// name = jsonObject.getString("name");
//						flag = jsonObject.getString("state");
//
//					} catch (JSONException e) {
//
//						// TODO Auto-generated catch block
//
//						e.printStackTrace();
//
//					}
//
//					if (flag.equals("1")) {
//
//						return true;
//
//					}
//
//					else {
//
//						return false;
//
//					}
//
//				}
//
//				else {
//
//					return false;
//
//				}
//
//			}
//
//		} catch (ClientProtocolException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		} catch (IOException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		}
//
//		return false;
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add(0, 0, 0, "关于");
//		menu.add(0, 1, 1, "退出");
//		return true;
//	}
//
//	public boolean onOptionsItemSelected(MenuItem mi) {
//		switch (mi.getItemId()) {
//		case 0:
//			Intent intent = new Intent();
//			intent.setClass(AndroidLoginActivity.this, About.class);
//			startActivity(intent);
//			break;
//		case 1:
//			new AlertDialog.Builder(AndroidLoginActivity.this)
//					.setTitle("")
//					.setMessage("确定退出吗？")
//					.setPositiveButton("确定",
//							new DialogInterface.OnClickListener() {
//								public void onClick(
//										DialogInterface dialoginterface, int i) {
//									finish();
//
//								}
//							})
//					.setNegativeButton("取消",
//							new DialogInterface.OnClickListener() {
//								public void onClick(
//										DialogInterface dialoginterface, int i) {
//								}
//							}).show();
//			break;
//		}
//		return true;
//
//	}
//}
