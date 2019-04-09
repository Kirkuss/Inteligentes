package control;

public class TreeNode implements Comparable<TreeNode> {
	private control.Node parent;
	private State currentState;
	private double cost;
	private String action;
	private int depth;
	private double f;
	
	public TreeNode() {
		this.f = Math.random() * ((10000 - 0) + 1) + 0;
	}
	
	public control.Node getParent() {
		return parent;
	}
	public void setParent(control.Node parent) {
		this.parent = parent;
	}
	public State getCurrentState() {
		return currentState;
	}
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public double getF() {
		return f;
	}
	public void setF(double f) {
		this.f = f;
	}
	
	@Override
	public int compareTo(TreeNode o) {
		if (f < o.getF()) {
			return -1;
		} else if ( f > o.getF()) {
			return 1;
		} else {
			return 0;
		}
	}
}
