package com.android.uiautomator.client;

import com.android.uiautomator.client.cmd.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;

/**
 * @author xdf
 *
 */
public class Command {
	/**
	 *
	 */
	private static HashMap<String, CommandBase> map = new HashMap<String, CommandBase>();

	static {
		getMap().put("ping", new Ping());
		getMap().put("wake", new Wake());
		getMap().put("find", new Find());
		getMap().put("setText", new SetText());
		getMap().put("getText", new GetText());
		getMap().put("click", new Click());
		getMap().put("clearText", new ClearText());
		getMap().put("swipe", new Swipe());
		getMap().put("getWindowSize", new GetWindowSize());
		getMap().put("getProperties", new GetProperties());
		getMap().put("getSource", new GetSource());
	}

	/**
	 * @param input
	 * @return res
	 * @throws JSONException
	 * @throws ParserConfigurationException
	 */
	static String handleInput(String input) throws JSONException,
			ParserConfigurationException {
		JSONObject json = Utils.parseJSON(input);
		String cmd = (String) json.get("cmd");
		JSONObject args = (JSONObject) json.get("args");
		return getMap().get(cmd).execute(args);
	}

	/**
	 * @return res
	 */
	public static HashMap<String, CommandBase> getMap() {
		return map;
	}

	/**
	 * @param map
	 */
	public static void setMap(HashMap<String, CommandBase> map) {
		Command.map = map;
	}
}
