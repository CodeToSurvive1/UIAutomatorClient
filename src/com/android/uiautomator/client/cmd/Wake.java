package com.android.uiautomator.client.cmd;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.RemoteException;

import com.android.uiautomator.client.CommandBase;
import com.android.uiautomator.client.Utils;
import com.android.uiautomator.core.UiDevice;

/**
 * @author xdf
 *
 */
public class Wake extends CommandBase {
	@Override
	public String execute(JSONObject args) throws JSONException {
		try {
			UiDevice.getInstance().wakeUp();
			return success((Object) true);
		} catch (final RemoteException e) {
			Utils.output(e.toString());
		}
		return failed("UnknownError");
	}
}