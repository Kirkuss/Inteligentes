package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Graph {
	
	private String X_KEY, Y_KEY, OSMID_KEY, NAME_KEY, LEN_KEY;
	private ArrayList<control.Node> graphNodes = new ArrayList<control.Node>();
	private ArrayList<Edge> graphEdges = new ArrayList<Edge>();
	
	public Graph(String file) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("XML file was readed in: " + readXML(file) + " ms\n");
	}
	
	public long readXML(String file) throws ParserConfigurationException, SAXException, IOException {
		long t_init = System.currentTimeMillis();
		
		String ID = "", src = "", tgt = "", eName = "";
		float length = 0, X = 0, Y = 0;
		
		File f = new File(file);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(f);
		
		doc.getDocumentElement().normalize();
		
		/**
		 * Assign key values to ease XML reading.
		 * key tags are variable from document to document.
		 */
		
		NodeList kList = doc.getElementsByTagName("key");
		
		for (int i = 0; i<kList.getLength(); i++) {
			Node kNode = kList.item(i);
			Element kElement = (Element) kNode;
			if(kElement.getAttribute("attr.name").equals("name") && kElement.getAttribute("for").equals("edge"))
				NAME_KEY = kElement.getAttribute("id");
			else if(kElement.getAttribute("attr.name").equals("length") && kElement.getAttribute("for").equals("edge"))
				LEN_KEY = kElement.getAttribute("id");
			else if(kElement.getAttribute("attr.name").equals("x") && kElement.getAttribute("for").equals("node"))
				X_KEY = kElement.getAttribute("id");
			else if(kElement.getAttribute("attr.name").equals("y") && kElement.getAttribute("for").equals("node"))
				Y_KEY = kElement.getAttribute("id");
			else if(kElement.getAttribute("attr.name").equals("osmid") && kElement.getAttribute("for").equals("node"))
				OSMID_KEY = kElement.getAttribute("id");
		}
		
		NodeList data;
		NodeList nList = doc.getElementsByTagName("node");
		
		for(int i = 0; i<nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				data = eElement.getElementsByTagName("data");
				
				for(int j = 0; j<data.getLength(); j++) {
					Node dNode = data.item(j);
					Element dElement = (Element) dNode;
					if(dElement.getAttribute("key").equals(X_KEY)) X = Float.valueOf(dElement.getTextContent());
					else if(dElement.getAttribute("key").equals(Y_KEY)) Y = Float.valueOf(dElement.getTextContent());
					else if(dElement.getAttribute("key").equals(OSMID_KEY)) ID = dElement.getTextContent();
				}
				control.Node n = new control.Node(ID, X, Y);
				//System.out.println(n.ToString());
				graphNodes.add(n);
			}
		}
		
		NodeList eList = doc.getElementsByTagName("edge");
		
		for (int i = 0; i<eList.getLength(); i++) {
			Node eNode = eList.item(i);
			Element eElement = (Element) eNode;
			
			src = eElement.getAttribute("source");
			tgt = eElement.getAttribute("target");
			
			data = eElement.getElementsByTagName("data");
			
			for (int j = 0; j<data.getLength(); j++) {
				Node dNode = data.item(j);
				Element dElement = (Element) dNode;
				if(dElement.getAttribute("key").equals(NAME_KEY)) eName = dElement.getTextContent();
				else if(dElement.getAttribute("key").equals(LEN_KEY)) length = Float.valueOf(dElement.getTextContent());
			}
			Edge e = new Edge(src, tgt, eName, length);
			//System.out.println(e.ToString());
			graphEdges.add(e);
		}
		return System.currentTimeMillis() - t_init;
	}
	
	public boolean BelongNode(String ID) {
		for (int i = 0; i<graphNodes.size(); i++) {
			if(graphNodes.get(i).getID().equals(ID)) return true;
		}
		return false;
	}
	
	public float [] positionNode(String ID) {
		float[] xy = new float[2];
		control.Node n = null;
		if(BelongNode(ID)) {
			for (int i = 0; i<graphNodes.size(); i++) {
				if(graphNodes.get(i).getID().equals(ID)) {
					n = graphNodes.get(i);
					break;
				}
			}
			xy[0] = n.getX();
			xy[1] = n.getY();
			return xy;
		}
		return null;
	}
	
	public ArrayList<Edge> adjacentNode(String ID){
		ArrayList<Edge> adjacents = new ArrayList<Edge>();
		if(BelongNode(ID)) {
			System.out.println("\nAdjacents of " + ID + ": \n");
			for(int i = 0; i<graphEdges.size(); i++) {
				Edge e = graphEdges.get(i);
				if (e.getSrc().equals(ID)) {
					System.out.println(e.ToString());
					adjacents.add(e);
				}
			}
			System.out.println();
			return adjacents;
		}
		return null;
	}
}
