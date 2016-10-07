package com.android.uiautomator.client.cmd;

import android.graphics.Rect;

import com.android.uiautomator.client.CommandBase;
import com.android.uiautomator.client.Element;
import com.android.uiautomator.client.Elements;
import com.android.uiautomator.core.UiObjectNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author xdf
 */
public class GetProperties extends CommandBase {
    @Override
    public String execute(JSONObject args) throws JSONException {
        try {
            String elementId = (String) args.get("elementId");
            Element el = Elements.getGlobal().getElement(elementId);
            final Rect rect = el.element.getBounds();
            JSONObject size = new JSONObject();
            size.put("width", rect.width());
            size.put("height", rect.height());
            size.put("centerX", rect.centerX());
            size.put("centerY", rect.centerY());
            JSONObject origin = new JSONObject();
            origin.put("x", rect.left);
            origin.put("y", rect.top);
            JSONObject props = new JSONObject();
            props.put("origin", origin);
            props.put("size", size);
            return success(props.toString());
        } catch (final UiObjectNotFoundException e) {
            return failed("NoSuchElement");
        } catch (final Exception e) {
            return failed("UnknownError");
        }
    }
}
