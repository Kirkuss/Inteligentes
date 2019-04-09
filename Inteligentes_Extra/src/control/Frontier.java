package control;

import java.util.PriorityQueue;

public class Frontier {
	private PriorityQueue<TreeNode> frontier;
	
	public Frontier() {
		frontier = new PriorityQueue<TreeNode>();
	}
	
	public void Insert(TreeNode tn) {
		frontier.add(tn);
	}

	public void Remove() {
		frontier.poll();
	}
	
	public boolean isEmpty() {
		return frontier.isEmpty();
	}
}
