package com.android.uiautomator.client.cmd;

import com.android.uiautomator.client.CommandBase;
import com.android.uiautomator.core.UiDevice;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zxa
 *
 */
public class Swipe extends CommandBase {
    @Override
    public String execute(JSONObject args) throws JSONException {
        try {
            Integer startX = (Integer) args.get("startX");
            Integer startY = (Integer) args.get("startY");
            Integer endX = (Integer) args.get("endX");
            Integer endY = (Integer) args.get("endY");
            Integer duration = (Integer) args.get("duration");
            boolean result = UiDevice.getInstance().swipe(startX, startY, endX, endY, duration);

            return success(result);
        } catch (final Exception e) {
            return failed("UnknownError");
        }
    }
}