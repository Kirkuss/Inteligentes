package control;

public class Node implements Comparable<Node>{
	private String ID;
	private float X;
	private float Y;
	private double f;
	
	public Node() {
		this.f = Math.random() * ((10000 - 0) + 1) + 0;
	}
	
	public Node(String ID, float X, float Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		this.f = Math.random() * ((10000 - 0) + 1) + 0;
	}
	
	public String getID() { return ID;}
	public float getX() { return X;}
	public float getY() { return Y;}
	public double getF() { return f;} // TASK 2 F VALUE
	
	public String ToString() { return ID + " - " + X + "(X) " + Y + "(Y)";}

	@Override
	public int compareTo(Node o) {
		if (f < o.getF()) {
			return -1;
		} else if ( f > o.getF()) {
			return 1;
		} else {
			return 0;
		}
	}
}
