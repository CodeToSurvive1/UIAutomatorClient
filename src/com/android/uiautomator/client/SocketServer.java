package com.android.uiautomator.client;

import org.json.JSONException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xdf
 *
 */
public class SocketServer {
	/**
	 * 
	 */
	private ServerSocket server;

	/**
	 * @author xdf
	 * @param port
	 * @throws IOException
	 *
	 */

	public SocketServer(int port) throws IOException {
		setServer(new ServerSocket(port));
	}

	/**
	 * @param readyFlag
	 * @throws JSONException
	 * @throws ParserConfigurationException
	 */
	public void listen(String readyFlag) throws JSONException,
			ParserConfigurationException {
		Utils.output(readyFlag);
		try {
			Socket client = server.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					client.getInputStream(), "UTF-8"));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream(), "UTF-8"));
			StringBuilder swap = new StringBuilder();

			while (true) {
				swap.setLength(0);
				int chunk;
				while ((chunk = input.read()) != -1 && input.ready()) {
					swap.append((char) chunk);
				}
				String str = swap.toString();
				Utils.output("recive: " + str);
				String res = Command.handleInput(str);
				Utils.output("return: " + res);
				output.write(res);
				output.flush();
			}
		} catch (final IOException e) {

		}
	}

	/**
	 * @return res
	 */

	public ServerSocket getServer() {
		return server;
	}

	/**
	 * @param server
	 */
	public void setServer(ServerSocket server) {
		this.server = server;
	}
}
