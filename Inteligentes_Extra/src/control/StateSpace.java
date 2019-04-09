package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StateSpace {
	
	private Graph g;
	private Hashtable<String, control.Node> listNodes;
	
	public Graph getG() {
		return g;
	}


	public void setG(Graph g) {
		this.g = g;
	}


	public Hashtable<String, control.Node> getListNodes() {
		return listNodes;
	}


	public void setListNodes(Hashtable<String, control.Node> listNodes) {
		this.listNodes = listNodes;
	}


	public StateSpace(String file) throws ParserConfigurationException, SAXException, IOException {
		g = new Graph(file);
		listNodes = g.getNodes();
	}
	
	
	public PriorityQueue<control.Node> Succesors(State st){
		PriorityQueue<control.Node> succesors = new PriorityQueue<control.Node>();
		ArrayList <Edge> adjacents;
		control.Node n = null;
		
		if (BelongNode(st)) {
			adjacents = g.adjacentNode(st.getNode());
		}else {
			return null;
		}
		
		if (!adjacents.equals(null)) {
			for(int i = 0; i<adjacents.size(); i++) {
				n = listNodes.get(adjacents.get(i).getTgt());
				//Operaciones con el coste real mas adelante
				if (n.getID().equals(st.getNode())) {
					n = listNodes.get(adjacents.get(i).getSrc());
					System.out.println("I'm in " + st.getNode() + " and i go to " + n.getID() + " with a cost of " + n.getF());
				} else {
					System.out.println("I'm in " + st.getNode() + " and i go to " + n.getID() + " with a cost of " + n.getF());
				}
			}
		}
		
		return succesors;
	}
	
	public boolean BelongNode(State st) {
		return listNodes.containsKey(st.getNode());
	}
	
	public String toString() {
		return "STATE SPACE : \n" + g.toString();
	}
}
