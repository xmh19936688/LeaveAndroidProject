package com.xmh.leave;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.xmh.leave.base.BaseTabActivity;

public class TeacherActivity extends BaseTabActivity implements OnClickListener{
	// 定义常量字符串
	public static String TAB_TAG_ALLLIST = "alllist";
	public static String TAB_TAG_UNLIST = "unlist";
	public static String TAB_TAG_DOLIST = "dolist";
	//当前TabHost
	public static TabHost mTabHost;

	// 定义颜色
	static final int COLOR1 = Color.parseColor("#838992");
	static final int COLOR2 = Color.parseColor("#b87721");

	// 底部3个图片按钮
	ImageView mBut1, mBut2, mBut3;
	// 底部3个图片按钮下的5个文本框（可视属性为gone）
	TextView mCateText1, mCateText2, mCateText3;
	// 界面跳转事件
	Intent AllListIntent, UnListIntent, DoListIntent;
	// 当前显示的界面
	int mCurTabId = R.id.channel3;

	// 猜测是界面切换滑动效果
	private Animation left_in, left_out;
	private Animation right_in, right_out;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacherlist);

		// 准备Animation
		prepareAnim();

		// 准备Intent
		prepareIntent();

		//设置子Activity
		setupIntent();

		// 准备控件
		prepareView();
	}

	/**
	 * 准备Animation
	 */
	private void prepareAnim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);
		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}

	/**
	 * 准备控件
	 */
	private void prepareView() {
		// 获取图片按钮控件
		mBut1 = (ImageView) findViewById(R.id.imageView1);
		mBut2 = (ImageView) findViewById(R.id.imageView2);
		mBut3 = (ImageView) findViewById(R.id.imageView3);
		// 为底部5个按钮添加点击监听（添加到LinearLayout）
		findViewById(R.id.channel1).setOnClickListener(this);
		findViewById(R.id.channel2).setOnClickListener(this);
		findViewById(R.id.channel3).setOnClickListener(this);
		// 获取底部5个文本框
		mCateText1 = (TextView) findViewById(R.id.textView1);
		mCateText2 = (TextView) findViewById(R.id.textView2);
		mCateText3 = (TextView) findViewById(R.id.textView3);
	}

	/**
	 * 准备Intent
	 */
	private void prepareIntent() {

		AllListIntent = new Intent(this, AllListActivity.class);

		UnListIntent = new Intent(this, UnListActivity.class);

		DoListIntent = new Intent(this, DoListActivity.class);
	}

	/**
	 * 添加底部5个按钮
	 */
	private void setupIntent() {

		// 获取界面中的TabHost
		mTabHost = getTabHost();

		mTabHost.addTab(buildTabSpec(TAB_TAG_ALLLIST, R.string.bottomtitle01,R.mipmap.shouyemain1, AllListIntent));

		mTabHost.addTab(buildTabSpec(TAB_TAG_UNLIST, R.string.bottomtitle02,R.mipmap.shouyemain1, UnListIntent));

		mTabHost.addTab(buildTabSpec(TAB_TAG_DOLIST, R.string.bottomtitle03,R.mipmap.msg1, DoListIntent));
	}

	/**
	 * 将独立的元素绑定生成一个完整的TabSpec
	 *
	 * @param tag
	 * @param resLabel
	 *            标题
	 * @param resIcon
	 *            图标
	 * @param content
	 *            事件
	 * @return TabSpec单元
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
										 final Intent content) {
		return mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	/**
	 * 设置当前的Tab
	 *
	 * @param tab
	 *            Tab标识
	 */
	public static void setCurrentTabByTag(String tab) {
		mTabHost.setCurrentTabByTag(tab);
	}

	// 点击监听
	public void onClick(View v) {

		if (mCurTabId == v.getId()) {

			return;
		}

		mBut3.setImageResource(R.mipmap.shouyemain1);
		mBut1.setImageResource(R.mipmap.msg1);
		mBut2.setImageResource(R.mipmap.mimetab1);

		mCateText1.setTextColor(COLOR1);
		mCateText2.setTextColor(COLOR1);
		mCateText3.setTextColor(COLOR1);
		int checkedId = v.getId();
		final boolean o;
		if (mCurTabId < checkedId)
			o = true;
		else
			o = false;
		if (o)
			mTabHost.getCurrentView().startAnimation(left_out);
		else
			mTabHost.getCurrentView().startAnimation(right_out);
		switch (checkedId) {

			case R.id.channel3:
				mTabHost.setCurrentTabByTag(TAB_TAG_ALLLIST);
				mBut3.setImageResource(R.mipmap.shouyemain);
				mCateText3.setTextColor(COLOR2);
				mCurTabId=R.id.channel3;
				break;

			case R.id.channel1:
				mTabHost.setCurrentTabByTag(TAB_TAG_UNLIST);
				mBut1.setImageResource(R.mipmap.msg);
				mCateText1.setTextColor(COLOR2);
				mCurTabId=R.id.channel1;
				break;
			case R.id.channel2:
				mTabHost.setCurrentTabByTag(TAB_TAG_DOLIST);
				mBut2.setImageResource(R.mipmap.mimetab);
				mCateText2.setTextColor(COLOR2);
				mCurTabId=R.id.channel2;
				break;

			default:
				break;
		}

		if (o)
			mTabHost.getCurrentView().startAnimation(left_in);
		else
			mTabHost.getCurrentView().startAnimation(right_in);
		mCurTabId = checkedId;
	}
}