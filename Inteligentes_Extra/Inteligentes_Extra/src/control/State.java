package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	public void setListNodes(ArrayList<String> listNodes) throws NoSuchAlgorithmException {
		this.listNodes = new ArrayList<String>(listNodes);
		if(this.listNodes.contains(this.node)) {
			this.listNodes.remove(this.node);
		}
		calculateMD5();
	}

	public void calculateMD5() throws NoSuchAlgorithmException {
		String rep = this.node + " " + this.listNodes;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(rep.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for(byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		this.id = sb.toString();
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
