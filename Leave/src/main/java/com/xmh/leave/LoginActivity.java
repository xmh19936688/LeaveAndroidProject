package com.xmh.leave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xmh.leave.base.BaseActivity;
import com.xmh.leave.constinfo.LoginInfo;
import com.xmh.leave.logic.LoginLogic;

public class LoginActivity extends BaseActivity {

	//控件列表
	Button btn_login;
	EditText et_userid;
	EditText et_password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置显示
		setContentView(R.layout.login);

		// 为登录按钮设置监听
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new LoginListener());
	}

	// 登录按钮监听事件
	public class LoginListener implements OnClickListener {

		public void onClick(View view) {

			// 设置当前状态为登录中，不可重复登录
			btn_login.setText("登录中");
			btn_login.setClickable(false);

			// 获取用户输入的用户名密码
			et_userid = (EditText) findViewById(R.id.login_edit_account);
			String userid = et_userid.getText().toString().trim();
			et_password = (EditText) findViewById(R.id.login_edit_pwd);
			String password = et_password.getText().toString().trim();

			// 判断用户输入是否完整
			if (userid.length() == 0 || password.length() == 0) {
				Toast.makeText(getApplicationContext(), "请输入完整信息",
						Toast.LENGTH_SHORT).show();
				btn_login.setText("登 录");
				btn_login.setClickable(true);
				return;
			}

			// 提交逻辑层处理数据
			LoginLogic.packLogin(userid, password);
		}
	}

	//界面跳转返回信息
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//若登录成功
		if(resultCode== LoginInfo.LOGIN_SUCCESS){
			//结束activity
			this.finish();
			return;
		}
		//登录不成功，设置登录状态为可登录
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setText("登 录");
		btn_login.setClickable(true);
	}
}