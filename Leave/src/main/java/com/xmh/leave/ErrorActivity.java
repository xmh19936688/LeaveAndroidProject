package com.xmh.leave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xmh.leave.base.BaseActivity;
import com.xmh.leave.constinfo.LoginInfo;

public class ErrorActivity extends BaseActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置显示
		setContentView(R.layout.error);

		//尝试获取错误信息（有可能没有错误）
		try{
			//获取错误信息
			Intent intent=getIntent();
			Bundle bundle = intent.getExtras();
			String errorinfo=bundle.getString("errorinfo");

			//设置错误信息
			TextView tv_error=(TextView) findViewById(R.id.tv_error);
			tv_error.setText(errorinfo);
		}catch(Exception e){
			e.printStackTrace();
		}

		//向登录界面返回信息登录失败
		setResult(LoginInfo.LOGIN_FAIL);
	}
}