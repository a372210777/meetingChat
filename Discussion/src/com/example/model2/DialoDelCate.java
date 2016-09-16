package com.example.model2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.example.datamodel.Group;
import com.example.model.DelCategory;
import com.example.utilities.DialogDo;
import com.example.utilities.NetWork;

public class DialoDelCate implements DialogDo{

	private Activity activity;
	private List<Group> list;
	private int which;
	
	public DialoDelCate(Activity activity, List<Group> list,int which){
		this.activity=activity;
		this.list=list;
		this.which=which;
	}
	
	@Override
	public void work() {
		
		DelCategory dc = new DelCategory(list.get(which).getId());
		DelCategoryWorker worker = new DelCategoryWorker(dc, activity,
				list, which);
		new NetWork(worker, activity).execute("");
	}

}
