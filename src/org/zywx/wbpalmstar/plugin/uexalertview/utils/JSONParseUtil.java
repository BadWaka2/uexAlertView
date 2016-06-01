package org.zywx.wbpalmstar.plugin.uexalertview.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.plugin.uexalertview.Constant;
import org.zywx.wbpalmstar.plugin.uexalertview.JsConstant;
import org.zywx.wbpalmstar.plugin.uexalertview.bean.AlertViewBean;

/**
 * 解析JSON数据工具类
 * 
 * @author waka
 * @version createTime:2016年5月26日 下午12:04:15
 */
public class JSONParseUtil {

	/**
	 * 解析JSON数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static AlertViewBean parseJsonOpenAlertView(String jsonStr) {

		AlertViewBean bean = new AlertViewBean();

		try {

			JSONObject jsonObject = new JSONObject(jsonStr);

			if (jsonObject.has(JsConstant.HEAD)) {
				JSONObject head = jsonObject.getJSONObject(JsConstant.HEAD);
				bean.setHeadTitle(head.optString(JsConstant.TITLE, ""));
				bean.setHeadColor(head.optString(JsConstant.COLOR, Constant.DEFAULT_COLOR));
			}

			if (jsonObject.has(JsConstant.MESSAGE)) {
				JSONObject message = jsonObject.getJSONObject(JsConstant.MESSAGE);
				bean.setMessageTitle(message.optString(JsConstant.TITLE, ""));
				bean.setMessageColor(message.optString(JsConstant.COLOR, Constant.DEFAULT_COLOR));
			}

			if (jsonObject.has(JsConstant.BUTTON)) {
				JSONArray buttons = jsonObject.getJSONArray(JsConstant.BUTTON);
				for (int i = 0; i < buttons.length(); i++) {
					String buttonTitle = buttons.getJSONObject(i).optString(JsConstant.TITLE, "");
					String buttonColor = buttons.getJSONObject(i).optString(JsConstant.COLOR, Constant.DEFAULT_COLOR);
					Map<String, String> buttonMap = new HashMap<String, String>();
					buttonMap.put(Constant.MAP_KEY_BUTTON_TITLE, buttonTitle);
					buttonMap.put(Constant.MAP_KEY_BUTTON_COLOR, buttonColor);
					bean.buttonList.add(buttonMap);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		}
		return bean;
	}

}
