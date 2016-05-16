package com.xmh.leave.logic;

import android.widget.Button;

import com.xmh.leave.R;
import com.xmh.leave.application.LeaveApplicationActivity;
import com.xmh.leave.web.Ask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AskLogic {

	public static void packLogin(String userid, String startdate,
			String enddate, String reason, String state) {

		try {
			// 封装数据
			JSONObject jso = new JSONObject();
			jso.put("userid", userid);
			jso.put("startdate", startdate);
			jso.put("enddate", enddate);
			jso.put("reason", reason);
			jso.put("state", state);
			String value = jso.toString();
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("askinfo", value));
			// 提交网络层
			new Ask().ask(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void upackAsk(String value) {
		final Button btn_submit = (Button) LeaveApplicationActivity
				.getAppActivity().findViewById(R.id.btn_submit_ask);
		try {
			// 获取回送数据
			JSONObject jso = new JSONObject(value);
			String askresult = jso.get("askresult").toString().trim();
			// 判断是否成功
			if (askresult.equals("success")) {
				btn_submit.post(new Runnable() {

					public void run() {
						btn_submit.setText("已提交");
					}
				});
			} else {
				btn_submit.post(new Runnable() {

					public void run() {
						btn_submit.setText("提交");
						btn_submit.setClickable(true);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			btn_submit.post(new Runnable() {

				public void run() {
					btn_submit.setText("提交失败，点击重试");
					btn_submit.setClickable(true);
				}
			});
		}
	}

}
