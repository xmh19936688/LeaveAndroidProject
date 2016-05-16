package com.xmh.leave.logic;

import android.content.Intent;

import com.xmh.leave.AskActivity;
import com.xmh.leave.ErrorActivity;
import com.xmh.leave.TeacherActivity;
import com.xmh.leave.application.LeaveApplicationActivity;
import com.xmh.leave.application.LeaveApplicationList;
import com.xmh.leave.constinfo.LoginInfo;
import com.xmh.leave.currentinfo.CurrentUser;
import com.xmh.leave.web.Login;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginLogic {
	
	static String UserId;
	static String Password;

	public static void packLogin(String userid, String password) {
		
		//保存数据
		UserId=userid;
		Password=password;
		
		try {
			// 封装数据
			JSONObject jso = new JSONObject();
			jso.put("userid", userid);
			jso.put("password", password);
			String value=jso.toString();
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("logininfo",value));
			//提交网络层
			new Login().login(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void upackLogin(String value){
		try {
			//网络未连接
			if(value.equals("neterror")){
				Intent intent = new Intent();
				intent.putExtra("errorinfo", "网络连接异常");
				intent.setClass(LeaveApplicationActivity.getAppActivity(), ErrorActivity.class);
				LeaveApplicationActivity.getAppActivity().startActivityForResult(intent, 0);
				return;
			}
			
			//网络连接正常则获取回送数据
			JSONObject jso=new JSONObject(value);
			
			//判断是否登录成功，不成功则转到错误界面(登陆成功则loginresult为空)
			String loginresult=jso.get("loginresult").toString().trim();
			//是否学生登陆成功
			if(loginresult.trim().equals("student")){
				//保存用户信息
				CurrentUser.CurrentUserid=UserId;
				CurrentUser.CurrentPassword=Password;
				CurrentUser.CurrentPower= LoginInfo.STUDENT;
				CurrentUser.CurrentUsername=jso.get("username").toString();
				//获取列表json数据
				String jsonMyAskList=jso.get("myasklist").toString();
				//保存list到Application中
				LeaveApplicationList.initListFromJson(jsonMyAskList);
				//界面跳转
				Intent intent = new Intent();//创建事件
				intent.setClass(LeaveApplicationActivity.getAppActivity(), AskActivity.class);//设置事件跳转界面
				LeaveApplicationActivity.getAppActivity().finish();//结束当前界面（登录界面）
				LeaveApplicationActivity.getAppActivity().startActivity(intent);//界面跳转到错误界面并返回数据到登录界面
				return;
			}
			//是否教师登录成功
			if(loginresult.trim().equals("teacher")){
				//保存用户信息
				CurrentUser.CurrentUserid=UserId;
				CurrentUser.CurrentPassword=Password;
				CurrentUser.CurrentPower=LoginInfo.TEACHER;
				CurrentUser.CurrentUsername=jso.get("username").toString();
				//获取列表json数据
				String jsonAskList=jso.get("asklist").toString();
				//保存list到Application中
				LeaveApplicationList.initListFromJson(jsonAskList);
				//界面跳转
				Intent intent = new Intent();//创建事件
				intent.setClass(LeaveApplicationActivity.getAppActivity(), TeacherActivity.class);//设置事件跳转界面
				LeaveApplicationActivity.getAppActivity().finish();//结束当前界面（登录界面）
				LeaveApplicationActivity.getAppActivity().startActivity(intent);//界面跳转到错误界面并返回数据到登录界面
				return;
			}
			//登录失败
			Intent intent = new Intent();//创建事件
			intent.putExtra("errorinfo", loginresult);//向事件中添加登录结果
			intent.setClass(LeaveApplicationActivity.getAppActivity(), ErrorActivity.class);//设置事件跳转界面
			LeaveApplicationActivity.getAppActivity().startActivityForResult(intent, 0);//界面跳转到错误界面并返回数据到登录界面
			return;

		} catch (Exception e) {
			e.printStackTrace();
			//异常情况
			System.out.println(e.getMessage());
			Intent intent = new Intent();//创建事件
			intent.putExtra("errorinfo", "未知登录异常");//向事件中添加登录结果
			intent.setClass(LeaveApplicationActivity.getAppActivity(), ErrorActivity.class);//设置事件跳转界面
			LeaveApplicationActivity.getAppActivity().startActivityForResult(intent, 0);//界面跳转并返回数据
			return;
		}
	}
}