package com.xmh.leave.application;

import android.app.Activity;
import android.app.Application;

public class LeaveApplicationActivity extends Application{

	//记录当前活动Activity
	private static Activity activity;

	//设置当前活动Activity
	public static void setAppActivity(Activity value) {
		activity=value;
	}

	//获取当前活动Activity
	public static Activity getAppActivity() {
		return activity;
	}
}