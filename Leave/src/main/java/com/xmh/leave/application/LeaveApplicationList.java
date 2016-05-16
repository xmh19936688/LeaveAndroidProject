package com.xmh.leave.application;

import com.google.gson.reflect.TypeToken;
import com.xmh.leave.utils.JsonUtils;
import com.xmh.leave.vo.Ask;

import java.lang.reflect.Type;
import java.util.List;


public class LeaveApplicationList {

	/**记录当前list信息*/
	private static List<Ask> list;

	/**获取当前list
	 * @return
	 */
	public static List<Ask> getList() {
		return list;
	}

	/**设置当前list
	 * @param list
	 */
	public static void setList(List<Ask> list) {
		LeaveApplicationList.list = list;
	}

	/**从Json数据初始化list
	 * @param jsonAskList json数据
	 */
	public static void initListFromJson(String jsonAskList) {
		// 获取数据
		Type type = new TypeToken<List<Ask>>() {
		}.getType();
		list=(List<Ask>) JsonUtils.StringToObjet(jsonAskList, type);
	}

	/**根据关键字设置状态
	 * @param userid id关键字
	 * @param startdate 起始如期关键字
	 * @param state 状态
	 */
	public static void setAsk(String userid, String startdate, String state) {
		//根据关键字遍历list
		for (Ask ask : list) {
			//找到匹配项设置状态
			if(ask.getUserid().equals(userid)&&ask.getStartdate().equals(startdate))
				ask.setState(state);
		}

	}
}