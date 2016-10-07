package com.android.uiautomator.client;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xdf
 *
 */
public abstract class CommandBase {
	/**
	 * @param data
	 * @param string
	 * @return res
	 * @throws JSONException
	 */
	protected String success(Object data) throws JSONException {
		JSONObject res = new JSONObject();
		res.put("success", true);
		res.put("data", data);
		return res.toString();
	}

	/**
	 * @param data
	 * @return res
	 * @throws JSONException
	 */
	protected String failed(Object data) throws JSONException {
		JSONObject res = new JSONObject();
		res.put("success", false);
		res.put("errMsg", data);
		return res.toString();
	}

	/**
	 * @param args
	 * @return res
	 * @throws JSONException
	 * @throws ParserConfigurationException
	 */
	public String execute(JSONObject args) throws JSONException,
			ParserConfigurationException {
		return null;
	}
}
