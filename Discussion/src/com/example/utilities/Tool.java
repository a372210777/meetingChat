package com.example.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.datamodel.Group;
import com.example.datamodel.Idea;
import com.example.discuss.DiscussApp;
import com.example.discuss.EnterRoomActivity;
import com.example.discuss.R;
import com.example.meeting.AdapterOfLvHost;
import com.example.meeting.LookIdeaActivity;
import com.example.model.DelCategory;
import com.example.model.DelIdea;
import com.example.model.InitParam;
import com.example.model.MoveIdea;
import com.example.model2.DelCategoryWorker;
import com.example.model2.DelIdeaWorker;
import com.example.model2.DialoDelCate;
import com.example.model2.MoveIdeaWorker;

public class Tool {

	public static void showDialog() {

	}

	public static boolean isNull(String str) {
		if (str != null && !str.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isCorrentLen(String str) {

		if (str == null || str.equals("") || str.length() > 12
				|| str.length() < 6) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmail(String str) {
		if (str != null && str.matches("\\w+@\\w+\\.\\w+")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 网络请求
	 * */
	public static String requestData(InitParam param, Activity activity) {
		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		HttpPost mPost = new HttpPost(param.getUrl());
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		param.init(pairs);
		try {
			mPost.setHeader(
					"Cookie",
					"PHPSESSID="
							+ ((DiscussApp) activity.getApplication())
									.getPHPSESSID());
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
					// 初始化结束
					String info = EntityUtils.toString(entity);
					// 返回JSON信息
					System.out.println("info-----------" + info);
					Log.w("url_info", "" + info);
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

	public static void showToast(String str, Activity activity) {
		Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
	}

	public static String getCurrentDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}

	public static String getCurrentDate2() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		return formatter.format(new Date());
	}

	public static void callPhone(final Activity activity, final String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		builder.setTitle(title);
		builder.create().show();
	}

	public static boolean isNetConn(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 移动分类
	 * */
	public static void createAlert(final Activity activity,
			final List<Group> list, final List<Idea> ideaLsit,
			final int position, final AdapterOfLvHost adapter) {
		if (list.size() == 0) {
			Tool.showToast("当前暂无分类", activity);
			return;
		}
		String array[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).getName();
		}
		new AlertDialog.Builder(activity).setTitle("移动分类")
				.setItems(array, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						MoveIdea mi = new MoveIdea(ideaLsit.get(position - 1)
								.getId(), list.get(which).getId());
						MoveIdeaWorker worker = new MoveIdeaWorker(activity,
								mi, list, ideaLsit, position, which, adapter);
						new NetWork(worker, activity).execute("");
					}
				}).create().show();
	}

	/**
	 * 删除观点，移动观点
	 * 
	 * @param1
	 * @param2 category数据源
	 * @param3 idea数据源 position 列表控件位置
	 * @param5 列表控件适配器
	 * */
	public static void createAlert2(final Activity activity,
			final List<Group> list, final List<Idea> ideaLsit,
			final int position, final AdapterOfLvHost adapter) {
		String array[] = { "删除", "移动","投票" };
		new AlertDialog.Builder(activity).setTitle("选择")
				.setItems(array, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							DelIdea di = new DelIdea(ideaLsit.get(position - 1)
									.getId());
							DelIdeaWorker worker = new DelIdeaWorker(di,
									activity, adapter, ideaLsit, position);
							new NetWork(worker, activity).execute("");
							break;
						case 1:
							createAlert(activity, list, ideaLsit, position,
									adapter);
							break;
						case 2:
							AlertDialog.Builder builder = new AlertDialog.Builder(
									activity);
							builder.setTitle("评分");
							builder.setItems(R.array.score,
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int which) {
											ideaLsit.get(position-1).setScore(which+1);
											adapter.notifyDataSetChanged();
										}
									});
							builder.create().show();
							break;
						}
					}
				}).create().show();
	}

	/**
	 * 查看分类列表 ,删除分类
	 * */
	public static void showCate(final Activity activity,
			final List<Group> list, final List<Idea> ideaList) {
		if (list.size() == 0) {
			Tool.showToast("当前暂无分类", activity);
			return;
		}
		String array[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i).getName();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("分类列表");
		builder.setItems(array, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String id = list.get(which).getId();
				for (int i = 0; i < ideaList.size(); i++) {
					if (id.equals(ideaList.get(i).getCategory())) {
						Tool.showToast("当前分类存在观点,无法删除", activity);
						return;
					}
				}
				DialoDelCate dl=new DialoDelCate(activity, list, which);
				showDialog(activity,dl);
//				DelCategory dc = new DelCategory(list.get(which).getId());
//				DelCategoryWorker worker = new DelCategoryWorker(dc, activity,
//						list, which);
//				new NetWork(worker, activity).execute("");
			}
		});
		builder.create().show();
	}
	
	/**
	 * 弹出确认对话框
	 * 参数2：要做的事
	 * */
	public static void showDialog(Activity activity,final DialogDo dd){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dd.work();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		builder.setTitle("确认退出");
		builder.create().show();
	}
	
	/**
	 * 保存文件至SD卡 实际目录:/mnt/sdcard/dir
	 * */
	public static void SavedToText(Context context, String stringToWrite,
			String dir) {

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + dir;
			File folder = new File(foldername);
			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}
			String fileName = getCurrentDate2() + ".txt";
			File targetFile = new File(foldername + fileName);
			OutputStreamWriter osw;
			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
					osw = new OutputStreamWriter(new FileOutputStream(
							targetFile), "utf-8");
					osw.write(stringToWrite);
					osw.close();
				} else {
					osw = new OutputStreamWriter(new FileOutputStream(
							targetFile, true), "utf-8");
					osw.write("\n" + stringToWrite);
					osw.flush();
					osw.close();
				}
				Toast.makeText(context, "保存成功!\n" + dir + fileName,
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(context, "未发现SD卡！", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * 读SD卡文件
	 * */
	public static  String readFromFile(Context context) {

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			String foldername = Environment.getExternalStorageDirectory()
					.getPath() + "/eryaApp";
			File folder = new File(foldername);

			if (folder == null || !folder.exists()) {
				folder.mkdir();
			}

			File targetFile = new File("/sdcard/eryaApp/eryaShoppingList.txt");
			String readedStr = "";

			try {
				if (!targetFile.exists()) {
					targetFile.createNewFile();
					return "No File error ";
				} else {
					InputStream in = new BufferedInputStream(
							new FileInputStream(targetFile));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
					String tmp;

					while ((tmp = br.readLine()) != null) {
						readedStr += tmp;
					}
					br.close();
					in.close();

					return readedStr;
				}
			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
				return e.toString();
			}
		} else {
			Toast.makeText(context, "未发现SD卡！", Toast.LENGTH_LONG).show();
			return "SD Card error";
		}

	}
}
