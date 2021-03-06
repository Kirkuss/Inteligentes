package control;

public class Node{
	private String ID;
	private float X;
	private float Y;
	private double f;
	
	public Node(String ID, float X, float Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
	}

	public String getID() { return ID;}
	public float getX() { return X;}
	public float getY() { return Y;}
	public double getF() { return f;} 
	public void setF(double f) { this.f = f;}
	
	public String toString() { return ID + " - " + X + "(X) " + Y + "(Y)";}
}
