package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import design.Block;
import design.Property;
import design.State;

public class TestData {

	public static String MODEL_FILE_PATH = "model/System5.xmi";
	/**
	 * Sets up block object with data streams
	 * 
	 * @return Block
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static Block setUpDataStream(List<AxisStream> axisList) {
		List<State> states = new ArrayList<State>();

		
		try {
			//Get Document Builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			//Build Document
			Document document = builder.parse(new File(MODEL_FILE_PATH));
			 
			//Normalize the XML Structure; It's just too important !!
			document.getDocumentElement().normalize();
			//Here comes the root node
			Element root = document.getDocumentElement();
			 
			//Get all employees
			NodeList nList = document.getElementsByTagName("state");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 Node node = nList.item(temp);
				 if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					    //Print each employee's detail
					    Element sElement = (Element) node;
					    String stateName = sElement.getAttribute("name");
						List<Property> props = new ArrayList<Property>(Arrays.asList(null, null, null, null, null));
					    NodeList assignmentList = sElement.getElementsByTagName("assignment" );
					    for(int i = 0; i < assignmentList.getLength(); i++) {
							 Node assignmentNode = assignmentList.item(i);
							 if (node.getNodeType() == Node.ELEMENT_NODE) {
								 Element assignmentElement = (Element) assignmentNode;
							 	 String valString = assignmentElement.getAttribute("value");
							 	 double val = 0.0;
							 	 if(valString.length() > 0) {
							 		val = Double.parseDouble(valString);
							 	 }
//									switch(this) {
//									case BP:
//										return 2;
//									case MAP:
//										return 3;
//									case SAP:
//										return 4;
//									case WP:
//										return 5;
//									case GP:
//										return 6;
//									default:
//										return 0;
//									}
							 	 String propName = assignmentElement.getAttribute("property");
								 switch(propName) {
								 	case "//@block.0/@property.0":
								 		if(axisList.contains(AxisStream.BP)) {
								 			props.set(0, new Property("bp", val));
								 		}
								 		break;
									case "//@block.0/@property.1":
								 		if(axisList.contains(AxisStream.MAP)) {
								 			props.set(1, new Property("map", val));
								 		}
								 		break;
									case "//@block.0/@property.2":
								 		if(axisList.contains(AxisStream.GP)) {
								 			props.set(2, new Property("gp", val));
								 		}
								 		break;
									case "//@block.0/@property.3":
								 		if(axisList.contains(AxisStream.SAP)) {
								 			props.set(3, new Property("sap", val));
								 		}
								 		break;
									case "//@block.0/@property.4":
								 		if(axisList.contains(AxisStream.WP)) {
								 			props.set(4, new Property("wp", val));
								 		}
								 		break;
								 		
								 }
							 }
					    }
					    states.add(new State(stateName, props));
				 }
			}
		} catch (ParserConfigurationException|IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	
		
			
		// SetUp Block
		return new Block("roboticArm", states);
	}
	
}