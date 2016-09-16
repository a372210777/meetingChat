package com.example.model2;


import android.app.Activity;

import com.example.utilities.DialogDo;

public class DialogExit   implements DialogDo{
	
	private Activity activity;
	public DialogExit(Activity activity){
		this.activity=activity;
	}
	@Override
	public void work() {
		activity.onBackPressed();
		System.exit(0);
	}

}
