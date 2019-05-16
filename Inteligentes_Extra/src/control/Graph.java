package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Graph {
	
	private String X_KEY, Y_KEY, OSMID_KEY, NAME_KEY, LEN_KEY, OSMID_EDGE;
	private Hashtable<String, control.Node> graphNodes = new Hashtable<String, control.Node>();
	private Hashtable<String, ArrayList<Edge>> graphEdges = new Hashtable<String, ArrayList<Edge>>();
	
	public Graph(String file) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("XML file was readed in: " + readXML(file) + " ms\n");
	}
	
	public long readXML(String file) throws ParserConfigurationException, SAXException, IOException {
		long t_init = System.currentTimeMillis();
		
		String ID = "", src = "", tgt = "", eName = "", EDGE_ID = "";
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
			else if(kElement.getAttribute("attr.name").equals("osmid") && kElement.getAttribute("for").equals("edge"))
				OSMID_EDGE = kElement.getAttribute("id");
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
				graphNodes.put(n.getID(), n);
			}
		}
		
		NodeList eList = doc.getElementsByTagName("edge");
		ArrayList<Edge> street = new ArrayList<Edge>();
		
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
				else if(dElement.getAttribute("key").equals(OSMID_EDGE)) EDGE_ID = dElement.getTextContent();
			}
			Edge e = new Edge(src, tgt, eName, length, EDGE_ID);
			if(graphEdges.containsKey(e.getID())) {
				graphEdges.get(e.getID()).add(e);
			}else {
				street = new ArrayList<Edge>();
				street.add(e);
				graphEdges.put(e.getID(), street);
			}
		}
		return System.currentTimeMillis() - t_init;
	}
	
	public boolean BelongNode(String ID) {
		return graphNodes.containsKey(ID);
	}
	
	public float [] positionNode(String ID) {
		float[] xy = new float[2];
		control.Node n = null;
		if(BelongNode(ID)) {
			n = graphNodes.get(ID);
			xy[0] = n.getX();
			xy[1] = n.getY();
			return xy;
		}
		return null;
	}
	
	public ArrayList<Edge> adjacentNode(String ID){
		ArrayList<Edge> adjacents = new ArrayList<Edge>();
		if(BelongNode(ID)) {
			//System.out.println("\nAdjacents of node " + ID + " : ");
			Set<String> keys = graphEdges.keySet();
			for(String key : keys) {
				ArrayList<Edge> street = graphEdges.get(key);
				for(int i = 0; i<street.size(); i++) {
					Edge e = street.get(i);
					if (e.getSrc().equals(ID)) {
						adjacents.add(e);
					}
				}
			}
			return adjacents;
		}
		return null;
	}
	
	public Hashtable<String, control.Node> getNodes() { return graphNodes;}
	public Hashtable<String, ArrayList<Edge>> getEdges() { return graphEdges;}
	
	public String toString() {
		String xml = "### XML KEYS ###\nX_KEY : " + X_KEY + "\nY_KEY : " + Y_KEY +"\nOSMID_KEY : " + OSMID_KEY + "\nNAME_KEY : " + NAME_KEY + "\nLEN_KEY : " + LEN_KEY + "\nOSMID_EDGE : " + OSMID_EDGE + "\n################\n";
		String nodes = "\nReaded nodes: \n";
		for(Map.Entry<String, control.Node> entry : graphNodes.entrySet()) {
			nodes += "    " + entry.getKey() + " : \n    X : " + entry.getValue().getX() + " Y : " + entry.getValue().getY() + "\n";
		}
		nodes += "    |-------------|\n";
		String edges = "\nReaded edges: \n";
		for(Map.Entry<String, ArrayList<Edge>> entry : graphEdges.entrySet()) {
			for(int i = 0; i<entry.getValue().size(); i++) {
				edges += "OSMID ID : " + entry.getKey() + " : \n    " + entry.getValue().get(i).getSrc() + " ----> " + entry.getValue().get(i).getTgt() + "\nNAME : " + entry.getValue().get(i).getName() + "\nLENGTH : " + entry.getValue().get(i).getLength() + " m\n-\n";
			}
		}
		edges += "|-------------|\n";
		
		return xml + nodes + edges;
	}
}
