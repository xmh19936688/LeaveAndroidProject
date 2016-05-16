package com.xmh.leave.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xmh.leave.R;
import com.xmh.leave.application.LeaveApplicationList;
import com.xmh.leave.constinfo.AskInfo;
import com.xmh.leave.logic.ReplyLogic;
import com.xmh.leave.vo.Ask;

import java.util.List;

public class AskListAdapter extends BaseAdapter {

	private Context mContext;
	private List<Ask> askList;

	/**
	 * 构造函数
	 * @param context
	 * @param list
	 */
	public AskListAdapter(Context context, List<Ask> list) {

		this.mContext = context;
		this.askList = list;

	}

	public int getCount() {
		return askList.size();
	}

	public Object getItem(int arg0) {
		return askList.get(arg0);
	}

	public long getItemId(int arg0) {
		return askList.size();
	}

	public View getView(final int index, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {

			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_item, arg2, false);

			//实例化Item中的控件
			holder.tv_userid=(TextView)convertView.findViewById(R.id.tv_userid);
			holder.tv_classid=(TextView)convertView.findViewById(R.id.tv_classid);
			holder.tv_username=(TextView)convertView.findViewById(R.id.tv_username);
			holder.tv_startdate=(TextView)convertView.findViewById(R.id.tv_startdate);
			holder.tv_enddate=(TextView)convertView.findViewById(R.id.tv_enddate);
			holder.tv_reason=(TextView)convertView.findViewById(R.id.tv_reason);
			holder.tv_state=(TextView)convertView.findViewById(R.id.tv_state);
			holder.btn_agree=(Button)convertView.findViewById(R.id.btn_agree);
			holder.btn_disagree=(Button)convertView.findViewById(R.id.btn_disagree);
			holder.ll_reply=(LinearLayout) convertView.findViewById(R.id.ll_reply);
			convertView.setTag(holder);
		}

		//为控件添加属性
		holder.tv_userid.setText(askList.get(index).getUserid().trim());
		holder.tv_classid.setText(askList.get(index).getClassid().trim());
		holder.tv_username.setText(askList.get(index).getUsername().trim());
		holder.tv_startdate.setText(askList.get(index).getStartdate().trim());
		holder.tv_enddate.setText(askList.get(index).getEnddate().trim());
		holder.tv_reason.setText(askList.get(index).getReason().trim());
		//根据状态判断该显示或隐藏的控件(默认的Item显示为未审批的情况)
		String state = askList.get(index).getState().trim();
		if(state.equals(AskInfo.ASK_AGREE)){//批准
			//显示批准的TextView
			holder.tv_state.setText(AskInfo.ASK_AGREE);
		}else if(state.equals(AskInfo.ASK_DISAGREE)){//驳回
			holder.tv_state.setText(AskInfo.ASK_DISAGREE);
		}else if(state.equals(AskInfo.ASK_UNDO)){//未审批
			holder.tv_state.setText(AskInfo.ASK_UNDO);
			//为批准按钮添加事件
			holder.btn_agree.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					//获取关键字
					String userid=holder.tv_userid.getText().toString();
					String startdate=holder.tv_startdate.getText().toString();
					//更新List
					askList.get(index).setState(AskInfo.ASK_AGREE);
					//更新ApplicationList
					LeaveApplicationList.setAsk(userid, startdate, AskInfo.ASK_AGREE);
					//更新View
					holder.tv_state.setText(AskInfo.ASK_AGREE);
					//隐藏两个按钮
					holder.ll_reply.setVisibility(View.GONE);
					//执行批准操作
					ReplyLogic.agree(userid, startdate);
				}
			});
			//为驳回按钮添加事件
			holder.btn_disagree.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					//获取关键字
					String userid=holder.tv_userid.getText().toString();
					String startdate=holder.tv_startdate.getText().toString();
					//更新List
					askList.get(index).setState(AskInfo.ASK_DISAGREE);
					//更新ApplicationList
					LeaveApplicationList.setAsk(userid,startdate,AskInfo.ASK_DISAGREE);
					//更新View
					holder.tv_state.setText(AskInfo.ASK_DISAGREE);
					//隐藏两个按钮
					holder.ll_reply.setVisibility(View.GONE);
					//执行批准操作
					ReplyLogic.disagree(userid,startdate);
				}
			});
			//为两个按钮所在Layout添加单击事件，单击消失
			holder.ll_reply.setOnClickListener(new OnClickListener() {

				public void onClick(View view) {
					view.setVisibility(view.GONE);
				}
			});
		}
		return convertView;
	}

	public static class ViewHolder {

		//Item中的控件
		TextView tv_userid;
		TextView tv_classid;
		TextView tv_username;
		TextView tv_startdate;
		TextView tv_enddate;
		TextView tv_reason;
		TextView tv_done;
		TextView tv_state;
		TextView tv_disagree;
		Button btn_agree;
		Button btn_disagree;
		LinearLayout ll_reply;
	}
}