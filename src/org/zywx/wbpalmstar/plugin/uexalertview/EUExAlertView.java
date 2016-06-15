package org.zywx.wbpalmstar.plugin.uexalertview;

import org.json.JSONException;
import org.json.JSONObject;
import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.engine.universalex.EUExUtil;
import org.zywx.wbpalmstar.plugin.uexalertview.utils.DeviceUtil;
import org.zywx.wbpalmstar.plugin.uexalertview.utils.FormatAmendUtil;
import org.zywx.wbpalmstar.plugin.uexalertview.bean.AlertViewBean;
import org.zywx.wbpalmstar.plugin.uexalertview.progressbar.HorizontalProgressBarWithNumber;
import org.zywx.wbpalmstar.plugin.uexalertview.utils.JSONParseUtil;
import org.zywx.wbpalmstar.plugin.uexalertview.utils.MLog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author waka
 * @version createTime:2016年5月21日 下午1:45:20
 */
public class EUExAlertView extends EUExBase {

	private Dialog mDialog;// 对话框对象，全局变量保证一次只有一个对话框
	private int mDialogType = 0;// 对话框类型
	public static final int DIALOG_TYPE_NORMAL = 1;// 普通对话框
	public static final int DIALOG_TYPE_PROGRESS = 2;// 进度条对话框
	private HorizontalProgressBarWithNumber mProgressBar;// 保存一个全局的进度条对象，方便改变进度

	public EUExAlertView(Context arg0, EBrowserView arg1) {
		super(arg0, arg1);
	}

	@Override
	protected boolean clean() {
		return false;
	}

	/**
	 * 回调给前端
	 * 
	 * @param cbMethodName
	 * @param string
	 */
	public void cb2Front(String cbMethodName, String string) {
		String js = EUExBase.SCRIPT_HEADER + "if(" + cbMethodName + "){" + cbMethodName + "('" + string + "');}";
		onCallback(js);
	}

	/**
	 * 回调给前端一个JSON对象
	 * 
	 * @param cbMethodName
	 * @param jsonString
	 */
	public void cb2FrontJSONObject(String cbMethodName, String jsonString) {
		String js = SCRIPT_HEADER + "if(" + cbMethodName + "){" + cbMethodName + "(" + jsonString + SCRIPT_TAIL;
		onCallback(js);
	}

	/**
	 * 创建alertView对象
	 * 
	 * @param params
	 */
	public void open(String[] params) {

		MLog.getIns().d("start");

		if (params.length < 1) {
			MLog.getIns().e("params.length < 1");
			return;
		}
		MLog.getIns().i("params[0] = " + params[0]);

		// 在当前window上添加AlertView
		// addAlertView2CurrentWindow(params[0]);

		// 打开Dialog
		openDialog(params[0]);

	}

	/**
	 * 在当前window上添加AlertView
	 * 
	 * @param jsonStr
	 */
	@SuppressWarnings("unused")
	private void addAlertView2CurrentWindow(String jsonStr) {

		/*
		 * 解析数据
		 */
		AlertViewBean bean = JSONParseUtil.parseJsonOpenAlertView(jsonStr);

		/*
		 * 构建View
		 */
		View view = View.inflate(mContext, EUExUtil.getResLayoutID("plugin_uexalertview_normal_dialog"), null);

		// 标题
		TextView tvHead = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_title"));
		tvHead.setText(bean.headTitle);
		if (!FormatAmendUtil.isColorStr(bean.headColor)) {// 颜色修正
			MLog.getIns().e("标题--颜色格式错误!");
			bean.headColor = "#ff000000";
		}
		tvHead.setTextColor(Color.parseColor(bean.headColor));

		// 内容
		TextView tvMessage = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_message"));
		tvMessage.setText(bean.messageTitle);
		if (!FormatAmendUtil.isColorStr(bean.messageColor)) {// 颜色修正
			MLog.getIns().e("内容--颜色格式错误!");
			bean.messageColor = "#ff000000";
		}
		tvMessage.setTextColor(Color.parseColor(bean.messageColor));

		// 按钮
		LinearLayout layoutButtons = (LinearLayout) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_layout_buttons"));
		float weight = (float) 1 / (float) bean.buttonList.size();
		MLog.getIns().i("weight = " + weight);

		for (int i = 0; i < bean.buttonList.size(); i++) {
			TextView btnView = (TextView) View.inflate(mContext, EUExUtil.getResLayoutID("plugin_uexalertview_button"), null);
			btnView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, weight));
			btnView.setId(i);
			String btnTitle = bean.buttonList.get(i).get(Constant.MAP_KEY_BUTTON_TITLE);
			String btnColor = bean.buttonList.get(i).get(Constant.MAP_KEY_BUTTON_COLOR);
			btnView.setText(btnTitle);
			if (!FormatAmendUtil.isColorStr(btnColor)) {// 颜色修正
				MLog.getIns().e("按钮" + i + "--颜色格式错误!");
				btnColor = "#ff000000";
			}
			btnView.setTextColor(Color.parseColor(btnColor));
			btnView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					MLog.getIns().d("button id = " + v.getId());
					Toast.makeText(mContext, "button id = " + v.getId(), Toast.LENGTH_SHORT).show();
				}
			});
			layoutButtons.addView(btnView);
		}

		MLog.getIns().i("getWidth = " + view.getWidth());
		MLog.getIns().i("getHeight = " + view.getHeight());

		final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		mBrwView.addViewToCurrentWindow(view, lp);

	}

	/**
	 * 打开Dialog
	 * 
	 * @param jsonStr
	 */
	private void openDialog(String jsonStr) {

		AlertViewBean bean = JSONParseUtil.parseJsonOpenAlertView(jsonStr);

		mDialog = new Dialog(mContext, EUExUtil.getResStyleID("plugin_uexalertview_dialog_style"));
		View view = View.inflate(mContext, EUExUtil.getResLayoutID("plugin_uexalertview_normal_dialog"), null);

		// 标题
		TextView tvHead = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_title"));
		tvHead.setText(bean.headTitle);
		if (!FormatAmendUtil.isColorStr(bean.headColor)) {// 颜色修正
			MLog.getIns().e("标题--颜色格式错误!");
			bean.headColor = "#ff000000";
		}
		tvHead.setTextColor(Color.parseColor(bean.headColor));

		// 内容
		TextView tvMessage = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_message"));
		tvMessage.setText(bean.messageTitle);
		if (!FormatAmendUtil.isColorStr(bean.messageColor)) {// 颜色修正
			MLog.getIns().e("内容--颜色格式错误!");
			bean.messageColor = "#ff000000";
		}
		tvMessage.setTextColor(Color.parseColor(bean.messageColor));

		// 按钮
		LinearLayout layoutButtons = (LinearLayout) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_layout_buttons"));
		float weight = (float) 1 / (float) bean.buttonList.size();
		MLog.getIns().i("weight = " + weight);

		for (int i = 0; i < bean.buttonList.size(); i++) {
			TextView btnView = (TextView) View.inflate(mContext, EUExUtil.getResLayoutID("plugin_uexalertview_button"), null);
			btnView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, weight));
			btnView.setId(i);
			String btnTitle = bean.buttonList.get(i).get(Constant.MAP_KEY_BUTTON_TITLE);
			String btnColor = bean.buttonList.get(i).get(Constant.MAP_KEY_BUTTON_COLOR);
			btnView.setText(btnTitle);
			if (!FormatAmendUtil.isColorStr(btnColor)) {// 颜色修正
				MLog.getIns().e("按钮" + i + "--颜色格式错误!");
				btnColor = "#ff000000";
			}
			btnView.setTextColor(Color.parseColor(btnColor));
			btnView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					MLog.getIns().d("button id = " + v.getId());
					TextView btnView = (TextView) v;
					JSONObject jsonObject = new JSONObject();
					try {
						jsonObject.put(JsConstant.BTN_INDEX, v.getId());
						jsonObject.put(JsConstant.BTN_NAME, btnView.getText().toString());
					} catch (JSONException e) {
						e.printStackTrace();
						MLog.getIns().e(e);
					}
					cb2FrontJSONObject(JsConstant.CB_ON_ITEM_CLICK, jsonObject.toString());
					if (mDialog != null) {
						mDialog.cancel();
						mDialog = null;
					}
				}
			});
			layoutButtons.addView(btnView);
		}
		mDialog.addContentView(view, new FrameLayout.LayoutParams(DeviceUtil.getDeviceWidth(mContext) * 5 / 6, LayoutParams.MATCH_PARENT, Gravity.CENTER));
		mDialog.show();
		mDialogType = DIALOG_TYPE_NORMAL;

		cb2Front(JsConstant.CB_SHOW, "" + 0);
	}

	/**
	 * 关闭alertView弹框
	 * 
	 * @param params
	 */
	public void close(String[] params) {

		MLog.getIns().d("start");

		if (mDialog != null) {
			mDialog.cancel();
			mDialog = null;
			if (mProgressBar != null) {
				mProgressBar = null;
			}
		}

	}

	/**
	 * 打开进度条弹框
	 * 
	 * @param params
	 */
	public void openCustomProgress(String[] params) {

		MLog.getIns().d("start");

		if (params.length < 1) {
			MLog.getIns().e("params.length < 1");
			return;
		}
		MLog.getIns().i("params[0] = " + params[0]);

		String title = "";// 弹框标题
		String titleColor = "";// 标题字体颜色
		String progressViewColor = "";// 进度条颜色
		String progressTitleColor = "";// 进度条百分比文字颜色

		try {

			JSONObject jsonObject = new JSONObject(params[0]);

			title = jsonObject.optString("title", "");
			titleColor = jsonObject.optString("titleColor", "");
			progressViewColor = jsonObject.optString("progressViewColor", "");
			progressTitleColor = jsonObject.optString("progressTitleColor", "");

		} catch (JSONException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		}

		MLog.getIns().i("title = " + title);
		MLog.getIns().i("titleColor = " + titleColor);
		MLog.getIns().i("progressViewColor = " + progressViewColor);
		MLog.getIns().i("progressTitleColor = " + progressTitleColor);

		mDialog = new Dialog(mContext, EUExUtil.getResStyleID("plugin_uexalertview_dialog_style"));
		View view = View.inflate(mContext, EUExUtil.getResLayoutID("plugin_uexalertview_progress_dialog"), null);

		// 标题
		TextView tvHead = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_title"));
		tvHead.setText(title);
		if (!FormatAmendUtil.isColorStr(titleColor)) {// 颜色修正
			MLog.getIns().e("标题--颜色格式错误!");
			titleColor = "#ff000000";
		}
		tvHead.setTextColor(Color.parseColor(titleColor));

		// 进度条
		HorizontalProgressBarWithNumber progressBar = (HorizontalProgressBarWithNumber) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_progressbar"));
		if (!FormatAmendUtil.isColorStr(progressViewColor)) {// 颜色修正
			MLog.getIns().e("进度条--颜色格式错误!");
			progressViewColor = "#ff000000";
		}
		progressBar.setmIfDrawText(false);
		progressBar.setmReachedBarColor(Color.parseColor(progressViewColor));
		mProgressBar = progressBar;

		// 进度条百分比文字
		TextView tvProgress = (TextView) view.findViewById(EUExUtil.getResIdID("plugin_uexalertview_tv_progress"));
		if (!FormatAmendUtil.isColorStr(progressTitleColor)) {// 颜色修正
			MLog.getIns().e("进度条百分比文字--颜色格式错误!");
			progressTitleColor = "#ff000000";
		}
		tvProgress.setTextColor(Color.parseColor(progressTitleColor));

		mDialog.addContentView(view, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
		mDialog.show();
		mDialogType = DIALOG_TYPE_PROGRESS;

		cb2Front(JsConstant.CB_SHOW, "" + 1);
	}

	/**
	 * 设置进度条百分比
	 * 
	 * @param params
	 */
	public void customProgressPresent(String[] params) {

		MLog.getIns().d("start");

		if (params.length < 1) {
			MLog.getIns().e("params.length < 1");
			return;
		}

		if (mDialog == null) {
			MLog.getIns().e("mDialog == null");
			return;
		}
		if (mDialogType != DIALOG_TYPE_PROGRESS) {
			MLog.getIns().e("mDialogType != DIALOG_TYPE_PROGRESS");
			return;
		}
		if (mProgressBar == null) {
			MLog.getIns().e("mProgressBar == null");
			return;
		}

		try {

			JSONObject jsonObject = new JSONObject(params[0]);
			int progress = Integer.valueOf(jsonObject.getString("num"));
			mProgressBar.setProgress(progress);
			mProgressBar.invalidate();

		} catch (JSONException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			MLog.getIns().e(e);
		}

	}

}
