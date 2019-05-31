package control;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Frontier {
	private PriorityQueue<TreeNode> frontier;
	
	public Frontier() {
		frontier = new PriorityQueue<TreeNode>();
	}
	
	public void Insert(TreeNode tn) {
		frontier.add(tn);	
	}
	
	private void reInsert(ArrayList<TreeNode> reInsert) {
		for(int i = 0; i<reInsert.size(); i++) {
			frontier.add(reInsert.get(i));
		}
	}
	
	public TreeNode Remove() {
		return frontier.poll();
	}
	
	public TreeNode RetrieveTreeNode(String key) {
		ArrayList<TreeNode> reInsert = new ArrayList<TreeNode>();
		while(!frontier.isEmpty()) {
			TreeNode next = frontier.poll();
			if(next.getCurrentState().getId().equals(key)) {
				reInsert(reInsert);
				return next;
			}else {
				reInsert.add(next);
			}
		}
		reInsert(reInsert);
		return null;
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