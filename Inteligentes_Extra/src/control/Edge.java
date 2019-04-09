package control;

public class Edge {
	private String src, tgt, name, EDGE_ID;
	private float length;
	
	public Edge(String src, String tgt, String name, float length, String EDGE_ID) {
		this.src = src;
		this.tgt = tgt;
		this.name = name;
		this.length = length;
		this.EDGE_ID = EDGE_ID;
	}
	
	public String getSrc() { return src;}
	public String getName() { return name;}
	public String getTgt() { return tgt;}
	public String getID() { return EDGE_ID;}
	public float getLength() { return length;}
	
	@Override
	public String toString() { return "(" + src + ", " + tgt + ", " + name + ", " + length + ")";}
}
