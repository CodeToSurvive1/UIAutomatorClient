package com.android.uiautomator.client.cmd;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.uiautomator.client.Elements;
import com.android.uiautomator.client.CommandBase;
import com.android.uiautomator.client.Element;

/**
 * @author xdf
 *
 */
public class GetText extends CommandBase {
	@Override
	public String execute(JSONObject args) throws JSONException {

		try {
			String elementId = (String) args.get("elementId");
			Element element = Elements.getGlobal().getElement(elementId);
			return success(element.getText());
		} catch (final Exception e) {
			return failed("UnknownError");
		}
	}
}