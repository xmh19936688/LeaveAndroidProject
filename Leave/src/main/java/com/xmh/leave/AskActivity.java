package com.xmh.leave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xmh.leave.base.BaseActivity;
import com.xmh.leave.constinfo.AskInfo;
import com.xmh.leave.constinfo.LoginInfo;
import com.xmh.leave.currentinfo.CurrentUser;
import com.xmh.leave.logic.AskLogic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AskActivity extends BaseActivity {

	//包含控件列表
	TextView tv_listinfo;
	TextView tv_username;
	EditText et_startdate;
	EditText et_enddate;
	EditText et_reason;
	EditText et_currentdate;
	Button btn_submit;
	Button btn_date;
	DatePicker dp_date;
	RelativeLayout rl_date;
	LinearLayout ll_askinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ask);

		// 实例化控件
		initView();
		//初始化控件
		initData();
		//设置reason输入框获取焦点，因为默认使日期获取焦点，将触发监听
		et_reason.requestFocus();
	}
	
	/**实例化控件
	 */
	private void initView() {
		// 实例化控件
		tv_listinfo=(TextView) findViewById(R.id.tv_listinfo_ask);
		tv_username = (TextView) findViewById(R.id.tv_username_ask);
		et_startdate = (EditText) findViewById(R.id.et_startdate_ask);
		et_enddate = (EditText) findViewById(R.id.et_enddate_ask);
		et_reason = (EditText) findViewById(R.id.et_reason_ask);
		btn_date = (Button) findViewById(R.id.btn_date_ask);
		btn_submit = (Button) findViewById(R.id.btn_submit_ask);
		dp_date = (DatePicker) findViewById(R.id.datePicker_ask);
		rl_date = (RelativeLayout) findViewById(R.id.rl_date_ask);
		ll_askinfo = (LinearLayout) findViewById(R.id.ll_askinfo_ask);
	}

	/**初始化控件
	 */
	private void initData() {
		//控件赋值
		tv_username.setText(CurrentUser.CurrentUsername);
		et_startdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		et_enddate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		btn_submit.setText("提交");
		btn_submit.setClickable(true);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				//设置当前状态为不可重复提交
				btn_submit.setText("提交中");
				btn_submit.setClickable(false);
				//获取信息
				String userid=CurrentUser.CurrentUserid;
				String startdate=et_startdate.getText().toString().trim();
				String enddate=et_enddate.getText().toString().trim();
				String reason=et_reason.getText().toString().trim();
				String state= AskInfo.ASK_UNDO;
				//判断数据是否完整
				if(reason.length()==0){
					Toast.makeText(getApplicationContext(), "请输入完整信息",
							Toast.LENGTH_SHORT).show();
					btn_submit.setText("提交");
					btn_submit.setClickable(true);
					return;
				}
				//提交数据到逻辑层
				AskLogic.packLogin(userid, startdate, enddate, reason, state);
			}
		});
		
		//设置日期编辑框监听
		et_startdate.setOnClickListener(new DateEditTextOnClickListener());
		et_startdate.setOnFocusChangeListener(new DateEditTextOnFocusChangeListener());
		et_enddate.setOnClickListener(new DateEditTextOnClickListener());
		et_enddate.setOnFocusChangeListener(new DateEditTextOnFocusChangeListener());
		
		//日期确定按钮监听
		btn_date.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				//获取值
				Date date=new Date(dp_date.getYear()-1900,dp_date.getMonth(),dp_date.getDayOfMonth());
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				//更新界面
				et_currentdate.setText(df.format(date));
				rl_date.setVisibility(View.GONE);
				et_reason.setEnabled(true);
				et_reason.requestFocus();
			}
		});
		
		//日期选择界面空白处点击监听
		rl_date.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				view.setVisibility(View.GONE);
				//设置info界面可点击
				et_reason.setEnabled(true);
				et_reason.requestFocus();
			}
		});
		
		//请假记录点击监听
		tv_listinfo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(AskActivity.this, TeacherActivity.class);
				startActivity(intent);
			}
		});
	}

	/**返回按钮监听
	 */
	@Override
	public void onBackPressed() {
		if(rl_date.getVisibility()==View.VISIBLE){
			rl_date.setVisibility(View.GONE);
			// 设置info界面可点击
			et_reason.setEnabled(true);
			et_reason.requestFocus();
		}
		else
			super.onBackPressed();
	}

	/**界面跳转返回信息
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//若申请成功
		if(resultCode== LoginInfo.ASK_SUCCESS){
			btn_submit.setText("已提交");
			return;
		}
		//若申请失败
		Toast.makeText(getApplicationContext(), "提交失败，请查看是否重复提交",
				Toast.LENGTH_SHORT).show();
		btn_submit.setText("提交");
		btn_submit.setClickable(true);
	}
	
	/**界面重启后重置控件属性
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		et_startdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		et_enddate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		btn_submit.setText("提交");
		btn_submit.setClickable(true);
		et_reason.requestFocus();
	}


	//日期编辑框单击监听
	class DateEditTextOnClickListener implements OnClickListener {

		public void onClick(View view) {
			//记录当前日期编辑框
			et_currentdate = (EditText) view;
			// 显示日期选择
			rl_date.setVisibility(view.VISIBLE);
			// 设置info界面不可点击
			et_reason.setEnabled(false);
		}
	}
	//日期编辑框焦点监听
	class DateEditTextOnFocusChangeListener implements OnFocusChangeListener{

		public void onFocusChange(View view, boolean isFocus) {
			
			if(isFocus){
				//记录当前日期编辑框
				et_currentdate = (EditText) view;
				// 显示日期选择
				rl_date.setVisibility(view.VISIBLE);
				// 设置info界面不可点击
				et_reason.setEnabled(false);
			}
		}
	}
}
