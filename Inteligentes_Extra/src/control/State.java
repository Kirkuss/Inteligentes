package control;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class State {
	private String node;
	private ArrayList<String> listNodes;
	private String id;
	
	public State() {
		//Constructor vacio para generacion con gson
	}
	
	public State(String node) {
		this.node = node;
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public ArrayList<String> getListNodes() {
		return listNodes;
	}

	public void setListNodes(ArrayList<String> listNodes) {
		this.listNodes = listNodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String nodes = "    LIST OF NODES : ";
		nodes += listNodes;
		return "    CURRENT : " + node + "\n" + nodes + "\n    id : " + id + "\n";
	}

}
