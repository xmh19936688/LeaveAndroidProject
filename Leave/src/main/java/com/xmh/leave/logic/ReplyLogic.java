package com.xmh.leave.logic;

import android.os.Looper;
import android.widget.Toast;

import com.xmh.leave.application.LeaveApplicationActivity;
import com.xmh.leave.web.Reply;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReplyLogic {

	/**
	 * 同意请假逻辑
	 * @param userid 用户名
	 * @param startdate 开始日期
	 */
	public static void agree(String userid, String startdate) {

		try{
			//封装数据
			JSONObject jso=new JSONObject();
			jso.put("userid", userid);
			jso.put("startdate", startdate);
			String value=jso.toString();
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("agreeinfo",value));
			//提交网络层
			new Reply().agree(list);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 驳回请假逻辑
	 * @param userid 用户名
	 * @param startdate 开始日期
	 */
	public static void disagree(String userid, String startdate) {
		
		try{
			//封装数据
			JSONObject jso=new JSONObject();
			jso.put("userid", userid);
			jso.put("startdate", startdate);
			String value=jso.toString();
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("agreeinfo",value));
			//提交网络层
			new Reply().disagree(list);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void upackReply(String value) {
		
		try {
			//获取回送数据
			JSONObject jso=new JSONObject(value);
			String replyresult=jso.get("replyresult").toString().trim();
			//判断是否成功
			if(replyresult.equals("success")){
				//提示信息
				Looper.prepare();
				Toast.makeText(LeaveApplicationActivity.getAppActivity().getApplicationContext(), "操作成功", 3000).show();
//				//更新界面(重新登录)
//				LoginLogic.packLogin(CurrentUser.CurrentUserid, CurrentUser.CurrentPassword);
			}else{
				//提示信息
				Looper.prepare();
				Toast.makeText(LeaveApplicationActivity.getAppActivity().getApplicationContext(), "操作失败", 3000).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}

	

	

}
