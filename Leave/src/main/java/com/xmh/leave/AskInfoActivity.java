package com.xmh.leave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xmh.leave.application.LeaveApplicationList;
import com.xmh.leave.base.BaseActivity;
import com.xmh.leave.constinfo.AskInfo;
import com.xmh.leave.constinfo.LoginInfo;
import com.xmh.leave.currentinfo.CurrentUser;
import com.xmh.leave.logic.ReplyLogic;

public class AskInfoActivity extends BaseActivity {

	//控件列表
	TextView tv_userid;
	TextView tv_classid;
	TextView tv_username;
	TextView tv_startdate;
	TextView tv_enddate;
	TextView tv_reason;
	TextView tv_state;
	Button btn_agree;
	Button btn_disagree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.askinfo);

		//初始化控件
		initView();
		//给控件添加数据
		initData();
	}

	/**
	 * 给控件添加数据
	 */
	private void initData() {

		//获取信息
		Intent intent=getIntent();
		Bundle bundle = intent.getExtras();
		tv_userid.setText(bundle.getString("userid"));
		tv_classid.setText(bundle.getString("classid"));
		tv_username.setText(bundle.getString("username"));
		tv_startdate.setText(bundle.getString("startdate"));
		tv_enddate.setText(bundle.getString("enddate"));
		tv_reason.setText(bundle.getString("reason"));
		tv_state.setText(bundle.getString("state"));
		//默认已审批
		tv_state.setVisibility(View.VISIBLE);
		btn_agree.setVisibility(View.GONE);
		btn_disagree.setVisibility(View.GONE);
		//若当前用户为教师，状态未审批时显示审批按钮
		if(bundle.getString("state").equals(AskInfo.ASK_UNDO)&& CurrentUser.CurrentPower== LoginInfo.TEACHER){
			tv_state.setVisibility(View.GONE);
			btn_agree.setVisibility(View.VISIBLE);
			btn_disagree.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		//初始化控件
		tv_userid=(TextView) findViewById(R.id.tv_userid_info);
		tv_classid=(TextView) findViewById(R.id.tv_classid_info);
		tv_username=(TextView) findViewById(R.id.tv_username_info);
		tv_startdate=(TextView) findViewById(R.id.tv_startdate_info);
		tv_enddate=(TextView) findViewById(R.id.tv_enddate_info);
		tv_reason=(TextView) findViewById(R.id.tv_reason_info);
		tv_state=(TextView) findViewById(R.id.tv_state_info);
		btn_agree=(Button) findViewById(R.id.btn_agree_info);
		btn_disagree=(Button) findViewById(R.id.btn_disagree_info);

		//为批准按钮设置监听
		btn_agree.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				//获取关键字
				String userid=tv_userid.getText().toString();
				String startdate=tv_startdate.getText().toString();
				//更新ApplicationList
				LeaveApplicationList.setAsk(userid, startdate, AskInfo.ASK_AGREE);
				//更新View
				tv_state.setText(AskInfo.ASK_AGREE);
				btn_agree.setVisibility(View.GONE);
				btn_disagree.setVisibility(View.GONE);
				tv_state.setVisibility(View.VISIBLE);
				//执行批准操作
				ReplyLogic.agree(userid, startdate);
			}
		});
		//为驳回按钮设置监听
		btn_disagree.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				//获取关键字
				String userid=tv_userid.getText().toString();
				String startdate=tv_startdate.getText().toString();
				//更新ApplicationList
				LeaveApplicationList.setAsk(userid,startdate,AskInfo.ASK_DISAGREE);
				//更新View
				tv_state.setText(AskInfo.ASK_DISAGREE);
				btn_agree.setVisibility(View.GONE);
				btn_disagree.setVisibility(View.GONE);
				tv_state.setVisibility(View.VISIBLE);
				//执行批准操作
				ReplyLogic.disagree(userid,startdate);
			}
		});
	}
}