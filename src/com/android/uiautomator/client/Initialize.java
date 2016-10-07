package com.android.uiautomator.client;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import org.json.JSONException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author xdf
 *
 */
public class Initialize extends UiAutomatorTestCase {

	/**
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParserConfigurationException
	 */
	public void testStartServer() throws IOException, JSONException,
			ParserConfigurationException {
		Utils.output("uiautomator start socket server.");
		int port = Integer.parseInt(getParams().getString("port"));
		String readyFlag = getParams().getString("flag");
		SocketServer server = new SocketServer(port);
		server.listen(readyFlag);
	}
}