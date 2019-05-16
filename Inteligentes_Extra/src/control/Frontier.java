package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Frontier {
	private PriorityQueue<TreeNode> frontier;
	private ArrayList<String> addedNodes;
	
	public Frontier() {
		frontier = new PriorityQueue<TreeNode>();
		addedNodes = new ArrayList<String>();
	}
	
	public void Insert(TreeNode tn) {
		if(frontier.isEmpty()) {
			frontier.add(tn);
			addedNodes.add(tn.getCurrentState().getNode());
		}else if(!alreadyInside(tn)){
			frontier.add(tn);
			addedNodes.add(tn.getCurrentState().getNode());
		}else {
			if(isBetter(tn)) {
				System.out.println("TreeNode updated in the fringe");
			}
		}
	}
	
	private boolean isBetter(TreeNode tn) {
		TreeNode to = new TreeNode();
		ArrayList<TreeNode> reInsert = new ArrayList<TreeNode>();
		while(!frontier.isEmpty()) {
			to = frontier.poll();
			if(to.getCurrentState().getNode().equals(tn.getCurrentState().getNode())) {
				if(tn.getF() < to.getF()) {
					reInsert.add(tn);
					reInsert(reInsert);
					return true;
				}else {
					reInsert.add(to);
					reInsert(reInsert);
					return false;
				}
			}else {
				reInsert.add(to);
			}
		}
		reInsert(reInsert);
		return false;
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
		TreeNode tn = new TreeNode();
		while(!LN.isEmpty()) {
			System.out.println("Adding " + LN.peek().getCurrentState().getNode() + " to fringe with cost " + LN.peek().getF());
			Insert(LN.poll());
		}
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
        Iterator<TreeNode> through = frontier.iterator() ;
        while(through.hasNext() ) {
                System.out.print(through.next().getCurrentState().getNode() + " ") ;
        }
        System.out.println() ;
	}
}
//