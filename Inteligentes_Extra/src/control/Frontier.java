package control;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Frontier {
	private PriorityQueue<TreeNode> frontier;
	private ArrayList<String> addedNodes;
	
	public Frontier() {
		frontier = new PriorityQueue<TreeNode>();
		addedNodes = new ArrayList<String>();
	}
	
	public void Insert(TreeNode tn) {
		frontier.add(tn);
	}
	
	private void reInsert(ArrayList<TreeNode> reInsert) {
		for(int i = 0; i<reInsert.size(); i++) {
			frontier.add(reInsert.get(i));
		}
	}
	
	public boolean alreadyInside(TreeNode tn) {
		return addedNodes.contains(tn.getCurrentState().getNode());
	}
	
	public void insertaLista(PriorityQueue<TreeNode> LN) {
		while(!LN.isEmpty()) { Insert(LN.poll());}
	}
	
	public void Clear() {
		frontier.clear();
		addedNodes.clear();
	}

	public TreeNode Remove() {
		TreeNode tn = frontier.poll();
		addedNodes.remove(tn.getCurrentState().getNode());
		return tn;
	}
	
	public boolean isEmpty() {
		return frontier.isEmpty();
	}
	
	public void printFringe() {      
		ArrayList<TreeNode> reInsert = new ArrayList<TreeNode>();
        DecimalFormat df = new DecimalFormat("###.##");
        while(!frontier.isEmpty()) {
        		TreeNode next = frontier.poll();
        		reInsert.add(next);
                System.out.print(next.getCurrentState().getNode() + " COST: " + df.format(next.getF()) + " ") ;
        }
        System.out.println() ;
        reInsert(reInsert);
	}
}
//