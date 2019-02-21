package control;

public class Edge {
	private String src, tgt, name;
	private float length;
	
	public Edge(String src, String tgt, String name, float length) {
		this.src = src;
		this.tgt = tgt;
		this.name = name;
		this.length = length;
	}
	
	public String getSrc() { return src;}
	public String getName() { return name;}
	public String getTgt() { return tgt;}
	public float getLength() { return length;}
	
	public String ToString() { return "(" + src + ", " + tgt + ", " + name + ", " + length + ")";}
}
