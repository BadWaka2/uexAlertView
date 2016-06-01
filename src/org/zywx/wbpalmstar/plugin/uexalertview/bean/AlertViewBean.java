package org.zywx.wbpalmstar.plugin.uexalertview.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zywx.wbpalmstar.plugin.uexalertview.utils.MLog;

/**
 * 对话框Bean
 * 
 * @author waka
 * @version createTime:2016年5月26日 上午11:55:16
 */
public class AlertViewBean {

	public String headTitle;// 标题文字
	public String headColor;// 标题文字颜色
	public String messageTitle;// 内容文字
	public String messageColor;// 内容文字颜色
	public List<Map<String, String>> buttonList;// 按钮列表

	public AlertViewBean() {
		headTitle = "";
		headColor = "";
		messageTitle = "";
		messageColor = "";
		buttonList = new ArrayList<Map<String, String>>();
	}

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public String getHeadColor() {
		return headColor;
	}

	public void setHeadColor(String headColor) {
		this.headColor = headColor;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageColor() {
		return messageColor;
	}

	public void setMessageColor(String messageColor) {
		this.messageColor = messageColor;
	}

	public String toString() {

		MLog.getIns().i("headTitle = " + headTitle);
		MLog.getIns().i("headColor = " + headColor);
		MLog.getIns().i("messageTitle = " + messageTitle);
		MLog.getIns().i("messageColor = " + messageColor);
		MLog.getIns().i("buttonList.size = " + buttonList.size());

		//@formatter:off
		
		String s = "";
		s += "headTitle = " + headTitle + "\n";
		s += "headColor = " + headColor + "\n";
		s += "messageTitle = " + messageTitle + "\n";
		s += "messageColor = " + messageColor + "\n";
		s += "buttonList.size = " + buttonList.size() + "\n";
		
		//@formatter:on

		return s;
	}
}
