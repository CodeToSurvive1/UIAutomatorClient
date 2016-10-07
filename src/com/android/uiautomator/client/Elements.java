package com.android.uiautomator.client;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * @author xdf
 *
 */
public class Elements {
	/**
	 * 
	 */
	private Integer counter;

	/**
	 * global singleton
	 */
	private static Elements global;
	/**
	 * 
	 */
	private Hashtable<String, Element> elems;

	/**
	 * Constructor
	 */
	public Elements() {
		setCounter(0);
		elems = new Hashtable<String, Element>();
	}

	/**
	 * @return res
	 */
	public static Elements getGlobal() {
		if (Elements.global == null) {
			Elements.global = new Elements();
		}
		return Elements.global;
	}

	/**
	 * @param element
	 * @return res
	 */
	public Element addElement(UiObject element) {
		setCounter(getCounter() + 1);
		String key = getCounter().toString();
		Element elem = new Element(key, element);
		getElems().put(key, elem);
		return elem;
	}

	/**
	 * @param key
	 * @return res
	 */
	public Element getElement(String key) {
		return getElems().get(key);
	}

	/**
	 * @param sel
	 * @return res
	 * @throws Exception
	 */
	public Element getElement(UiSelector sel) throws Exception {
		UiObject el = new UiObject(sel);

		if (el.exists()) {
			return addElement(el);
		} else {
			throw new Exception("not found");
		}
	}

	/**
	 * @param selector
	 * @return res
	 * @throws UiObjectNotFoundException
	 */
	public ArrayList<Element> getElements(UiSelector selector)
			throws UiObjectNotFoundException {
		String str = selector.toString();
		boolean index = str.contains("CLASS_REGEX=");
		boolean endsInstance = Pattern.compile(".*INSTANCE=\\d+]$")
				.matcher(str).matches();

		ArrayList<Element> elements = new ArrayList<Element>();

		if (endsInstance) {
			UiObject obj = new UiObject(selector);
			if (obj != null && obj.exists()) {
				elements.add(addElement(obj));
			}
			return elements;
		}

		UiObject obj;
		UiSelector tmp;
		int counter = 0;

		boolean keep = true;
		while (keep) {
			if (index) {
				tmp = selector.index(counter);
			} else {
				tmp = selector.instance(counter);
			}
			obj = new UiObject(tmp);
			counter++;

			if (obj != null && obj.exists()) {
				elements.add(addElement(obj));
			} else {
				keep = false;
			}
		}
		return elements;
	}

	/**
	 * @param instance
	 */
	public static void setInstance(Elements instance) {
		Elements.global = instance;
	}

	/**
	 * @return res
	 */
	public Integer getCounter() {
		return counter;
	}

	/**
	 * @param counter
	 */
	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	/**
	 * @return res
	 */
	public Hashtable<String, Element> getElems() {
		return elems;
	}
}