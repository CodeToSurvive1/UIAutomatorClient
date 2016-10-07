package com.android.uiautomator.client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xdf
 *
 */
public class Utils {
	/**
	 * @param log
	 */
	public static void output(String log) {
		System.out.println(log);
	}

	/**
	 * @param str
	 * @return res
	 * @throws JSONException
	 */
	public static JSONObject parseJSON(String str) throws JSONException {
		return new JSONObject(str);
	}
}
