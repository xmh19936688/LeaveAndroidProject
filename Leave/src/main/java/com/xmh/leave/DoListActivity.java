package com.xmh.leave;

import com.xmh.leave.constinfo.AskInfo;
import com.xmh.leave.vo.Ask;

import java.util.ArrayList;
import java.util.List;


public class DoListActivity extends AllListActivity{

	@Override
	protected List<Ask> selectList(List<Ask> allList) {

		//从allList中选出未审批的ask
		List<Ask> askList=new ArrayList<Ask>();
		for (Ask ask : allList) {

			if(!(ask.getState().equals(AskInfo.ASK_UNDO))){
				askList.add(ask);
			}
		}

		return askList;
	}

	@Override
	protected void setTitle() {
		tv_title.setText("已审批请假单");
	}
}