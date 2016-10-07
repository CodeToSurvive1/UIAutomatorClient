/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.uiautomator.client.XmlUtils;

import android.view.accessibility.AccessibilityNodeInfo;
import com.android.uiautomator.core.UiSelector;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;


public class XmlUtils {

    public static ArrayList<UiSelector> getSelectors(String xpathString) throws Exception {
        XPathExpression xpathExpression = compileXpath(xpathString);
        Node root = formatXmlHierarchy(getUiXmlHierarchyFromAndroid());
        NodeList nodes;
        ArrayList<UiSelector> selectors = new ArrayList<UiSelector>();
        try {
            nodes = (NodeList) xpathExpression.evaluate(root, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            throw new Exception("XMLWindowHierarchy could not be parsed:" + e);
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                try {
                    selectors.add(getSelectorFromXmlNode(nodes.item(i)));
                } catch (Exception e) {
                }
            }
        }

        return selectors;
    }

    // get the UiSelector related to Xml Node

    public static UiSelector getSelectorFromXmlNode(Node node) {
        NamedNodeMap attrElements = node.getAttributes();
        String androidClass = attrElements.getNamedItem("class").getNodeValue();
        String instance = attrElements.getNamedItem("instance").getNodeValue();
        return new UiSelector().className(androidClass).instance(Integer.parseInt(instance));
    }

    public static XPathExpression compileXpath(String xpathExpression) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression exp = null;
        try {
            exp = xpath.compile(xpathExpression);
        } catch (XPathExpressionException e) {
            throw new Exception("Invalid XPath expression");
        }
        return exp;
    }

    // get the current ui xml from the running android device

    public static InputSource getUiXmlHierarchyFromAndroid() throws Exception {
        AccessibilityNodeInfo root;
        while (true) {
            root = UiAutomatorBridge.getInstance().getQueryController().getAccessibilityRootNode();
            if (root != null) {
                break;
            }
        }
        String xmlDump = AccessibilityNodeInfoDumper.getWindowXMLHierarchy(root);
        return new InputSource(new StringReader(xmlDump));
    }

    public static Node formatXmlHierarchy(InputSource input) {
        XPath xpath = XPathFactory.newInstance().newXPath();

        Node root = null;
        try {
            root = (Node) xpath.evaluate("/", input, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not read xml hierarchy: ", e);
        }

        HashMap<String, Integer> instances = new HashMap<String, Integer>();

        formatNodes(root, instances);

        return root;
    }

    public static void formatNodes(Node node, HashMap<String, Integer> instances) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node nodeTmp = children.item(i);
            if (nodeTmp.getNodeType() == Node.ELEMENT_NODE) {
                Document doc = nodeTmp.getOwnerDocument();
                NamedNodeMap attributes = nodeTmp.getAttributes();

                String androidClass;
                try {
                    androidClass = attributes.getNamedItem("class").getNodeValue();
                    androidClass = androidClass.replaceAll("[$@#&]", ".");
                    androidClass = androidClass.replaceAll("\\s", "");

                    if (!instances.containsKey(androidClass)) {
                        instances.put(androidClass, 0);
                    }
                    Integer instance = instances.get(androidClass);

                    Node attrNode = doc.createAttribute("instance");
                    attrNode.setNodeValue(instance.toString());
                    attributes.setNamedItem(attrNode);

                    doc.renameNode(nodeTmp, nodeTmp.getNamespaceURI(), androidClass);

                    instances.put(androidClass, instance + 1);
                } catch (Exception e) {

                }
                formatNodes(nodeTmp, instances);
            }
        }
    }

}
