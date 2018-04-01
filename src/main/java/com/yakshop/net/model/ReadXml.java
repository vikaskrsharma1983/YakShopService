package com.yakshop.net.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXml {
	private File originalFilename;
	private List<YakShopModel> yakShopModelList;
	
	
	public ReadXml(File originalFilename) {
		this.originalFilename = originalFilename;

	}

	public List<YakShopModel> read() {
		yakShopModelList = new ArrayList<YakShopModel>();

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(originalFilename);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("labyak");

			for (int i = 0; i < nList.getLength(); i++) {
				YakShopModel yakShopmodel = new YakShopModel();
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String name = eElement.getAttribute("name");
					yakShopmodel.setName(name);
					float age = Float.parseFloat(eElement.getAttribute("age"));
					yakShopmodel.setAge(age);
					String sex = eElement.getAttribute("sex");
					Sex yakSex = (sex.equalsIgnoreCase("f")) ? Sex.FEMALE : Sex.MALE;
					yakShopmodel.setYakSex(yakSex);
					yakShopModelList.add(yakShopmodel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return yakShopModelList;
	}
}
