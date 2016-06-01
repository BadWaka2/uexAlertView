package org.zywx.wbpalmstar.plugin.uexalertview;

/**
 * 前端交互常量
 * 
 * @author waka
 * @version createTime:2016年5月26日 上午8:45:30
 */
public class JsConstant {

	/*
	 * 前端传进来
	 */
	public static final String HEAD = "head";// 弹框标题
	public static final String MESSAGE = "message";// 具体内容
	public static final String BUTTON = "button";// 按钮
	public static final String TITLE = "title";// 文字
	public static final String COLOR = "color";// 文字颜色

	/*
	 * 传给前端
	 */
	public static final String BTN_INDEX = "index";// 该按钮的下标
	public static final String BTN_NAME = "name";// 该按钮的文字

	// 回调
	public static final String CB_SHOW = "uexAlertView.cbShow";// 打开弹框回调,打开进度条弹框后回调数字1，其他弹框回调数字0
	public static final String CB_ON_ITEM_CLICK = "uexAlertView.onItemClick";// 点击弹框按钮回调

}
