package com.xmh.leave.web;

import com.xmh.leave.constinfo.WebInfo;
import com.xmh.leave.logic.LoginLogic;
import com.xmh.leave.logic.ReplyLogic;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.List;

public class Reply {

	//发送批准请求至服务端
	public void agree(List<NameValuePair> list) {

		try{
			//封装数据到HttpPost
			HttpPost post=new HttpPost(new URI(WebInfo.WEB_ROOT+"appreplyagree.action"));
			post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
			//开启线程传输数据
			new ReplyThread(post).start();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//发送驳回请求至服务端
	public void disagree(List<NameValuePair> list) {
		
		try{
			//封装数据到HttpPost
			HttpPost post=new HttpPost(new URI(WebInfo.WEB_ROOT+"appreplydisagree.action"));
			post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
			//开启线程传输数据
			new ReplyThread(post).start();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private class ReplyThread extends Thread{
		
		HttpClient client;
		HttpPost post;
		HttpResponse response;
		
		public ReplyThread(HttpPost post){
			this.post=post;
		}
		
		public void run(){
			//创建连接
			client=new DefaultHttpClient();
			//设置超时范围
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			try{
				//获取响应
				response=client.execute(post);
				if(response.getStatusLine().getStatusCode()==200){//200表示连接成功
					//获取返回内容
					HttpEntity entity=response.getEntity();
					if(entity!=null){
						// 从容器中获取Json字符串
						String value=EntityUtils.toString(entity,"UTF-8");
						//交给逻辑层处理
						ReplyLogic.upackReply(value);
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				//网络连接超时
				LoginLogic.upackLogin("neterror");
			}
		}
	}
}
