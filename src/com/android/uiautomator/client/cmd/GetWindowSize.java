package com.android.uiautomator.client.cmd;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.client.CommandBase;

public class GetWindowSize extends CommandBase {
	@Override
	public String execute(JSONObject args) throws JSONException {
		try {
			Integer width = UiDevice.getInstance().getDisplayWidth();
			Integer height = UiDevice.getInstance().getDisplayHeight();
			JSONObject size = new JSONObject();
			size.put("width", width);
			size.put("height", height);
			return success(size.toString());
		} catch(JSONException e) {
			return failed("UnknownError");
		}
	}
}
