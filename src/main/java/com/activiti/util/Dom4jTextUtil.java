package com.activiti.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jTextUtil {

	public static List<List<String>> getElementBy(String xmlText, String tag) throws DocumentException{
		Document document = DocumentHelper.parseText(xmlText);
		Element element = document.getRootElement();
		List<List<String>> newList = new ArrayList<>();
		getAllChildrenElementBy(element, tag, newList);
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	public static void getAllChildrenElementBy(Element rootElement, String tagName, List<List<String>> doubleList) {
//		System.out.println("当前节点的名称：" + rootElement.getName());
		List<Attribute> attributes = rootElement.attributes();
		
		List<String> list = new ArrayList<>();
		
		if (tagName.equals(rootElement.getName())) {
			for (Attribute a : attributes) {
				if ("id".equals(a.getName()) || "name".equals(a.getName())) {
					list.add(a.getValue());
				}
				System.out.println("属性" + a.getName() + ": " + a.getValue());
			}
			doubleList.add(list);
		}
		
		Iterator<Element> iterator = rootElement.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			getAllChildrenElementBy(e, tagName,doubleList);
		}
		
	}
}
