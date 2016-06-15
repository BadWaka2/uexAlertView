package org.zywx.wbpalmstar.plugin.uexalertview.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * @author waka
 * @version createTime:2016年6月15日 下午12:46:33
 */
public class DeviceUtil {

	/**
	 * 得到设备宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDeviceWidth(Context context) {

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		Point outSize = new Point();
		wm.getDefaultDisplay().getSize(outSize);

		return outSize.x;
	}

	/**
	 * 得到设备高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDeviceHeight(Context context) {

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		Point outSize = new Point();
		wm.getDefaultDisplay().getSize(outSize);

		return outSize.y;
	}
}
