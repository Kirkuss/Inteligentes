package control;

import java.util.Arrays;
import java.util.PriorityQueue;

public class State {
	private String node;
	//private control.Node currentPos;
	private PriorityQueue<String> listNodes;
	private String id;
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	/*public control.Node getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(control.Node currentPos) {
		this.currentPos = currentPos;
	}*/

	public PriorityQueue<String> getListNodes() {
		return listNodes;
	}

	public void setListNodes(PriorityQueue<String> listNodes) {
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
		/*String[] ids = (String[]) listNodes.toArray();
		Arrays.sort(ids);
		for (int i = 0; i<ids.length; i++) {
			
			nodes += "\nNODE : " + ids[i];
		}*/
		nodes += listNodes;
		return "    CURRENT : " + node + "\n" + nodes + "\n    id : " + id + "\n";
	}

}
