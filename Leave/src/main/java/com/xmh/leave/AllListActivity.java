package com.xmh.leave;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xmh.leave.adapter.AskListAdapter;
import com.xmh.leave.application.LeaveApplicationList;
import com.xmh.leave.constinfo.AskInfo;
import com.xmh.leave.constinfo.LoginInfo;
import com.xmh.leave.currentinfo.CurrentUser;
import com.xmh.leave.vo.Ask;

import java.util.List;


public class AllListActivity extends Activity {

	private AskListAdapter adapter;
	private ListView mListView;
	private View ll_reply;
	protected TextView tv_title;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alllist);

		//初始化控件
		InitView();

		//初始化列表信息
		initData();

		/**
		 * 为列表控件添加点击事件监听
		 */
		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int index,long arg3) {

				//获取控件信息
				String userid=((TextView)view.findViewById(R.id.tv_userid)).getText().toString();
				String classid=((TextView)view.findViewById(R.id.tv_classid)).getText().toString();
				String username = ((TextView)view.findViewById(R.id.tv_username)).getText().toString();
				String startdate = ((TextView)view.findViewById(R.id.tv_startdate)).getText().toString();
				String enddate = ((TextView)view.findViewById(R.id.tv_enddate)).getText().toString();
				String reason = ((TextView)view.findViewById(R.id.tv_reason)).getText().toString();
				String state = ((TextView)view.findViewById(R.id.tv_state)).getText().toString();

				//放到bundle中
				Bundle bundle=new Bundle();
				bundle.putString("userid", userid);
				bundle.putString("classid", classid);
				bundle.putString("username", username);
				bundle.putString("startdate", startdate);
				bundle.putString("enddate", enddate);
				bundle.putString("reason", reason);
				bundle.putString("state", state);

				//跳转界面
				Intent intent = new Intent(AllListActivity.this,AskInfoActivity.class).putExtras(bundle);
				startActivity(intent);
			}
		});

		//若当前用户为教师，则可以弹出审批按钮
		if(CurrentUser.CurrentPower==LoginInfo.TEACHER){
			/**
			 * 为列表控件添加长按事件监听
			 */
			mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> arg0, View view,
											   int index, long arg3) {

					//如果状态时未审批,显示两个按钮
					TextView tv_state = (TextView)view.findViewById(R.id.tv_state);
					if(tv_state.getText().toString().equals(AskInfo.ASK_UNDO)){
						ll_reply = view.findViewById(R.id.ll_reply);
						ll_reply.setVisibility(View.VISIBLE);
					}
					return false;
				}
			});
		}

	}

	//初始化控件
	private void InitView() {

		mListView = (ListView) findViewById(R.id.ask_list);
		tv_title=(TextView) findViewById(R.id.tv_title);
		setTitle();
	}

	//初始化数据
	private void initData() {

		List<Ask> allList=LeaveApplicationList.getList();
		//筛选list
		List<Ask> askList = selectList(allList);
		//将list添加到控件
		adapter = new AskListAdapter(AllListActivity.this, askList);
		mListView.setAdapter(adapter);
	}

	/**
	 * 筛选list
	 * @param allList 原list
	 * @return 筛选后的list
	 */
	protected List<Ask> selectList(List<Ask> allList) {
		return allList;
	}

	/**
	 * 设置标题
	 */
	protected void setTitle(){
		tv_title.setText("所有请假单");
	}

	/**
	 * 重新激活Activity时重新加载数据
	 */
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	/**
	 * 返回键监听
	 */
	@Override
	public void onBackPressed() {
		//如果批准驳回按钮可视，则隐藏按钮，否则执行返回事件
		if(ll_reply!=null){
			if(ll_reply.getVisibility()==View.VISIBLE){
				ll_reply.setVisibility(View.GONE);
				return;
			}
		}
		super.onBackPressed();
	}
}