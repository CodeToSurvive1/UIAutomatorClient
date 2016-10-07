package com.android.uiautomator.client.cmd;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.uiautomator.client.CommandBase;

/**
 * @author xdf
 *
 */
public class Ping extends CommandBase {
	@Override
	public String execute(JSONObject args) throws JSONException {
		try {
			return success((Object) "it works!");
		} catch (JSONException e) {
			e.printStackTrace();
			return failed("UnknownError");
		}
	}
}
