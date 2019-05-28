package control;

public class Edge {
	private String src, tgt, name, EDGE_ID, OSMID;
	private float length;
	
	public Edge(String src, String tgt, String name, float length, String EDGE_ID, String OSMID) {
		this.src = src;
		this.tgt = tgt;
		if (!name.equals("")) {
			this.name = name;
		}else {
			this.name = "[UNKNOWN]";
		}
		this.length = length;
		this.EDGE_ID = EDGE_ID;
		this.OSMID = OSMID;
	}
	
	public String getSrc() { return src;}
	public String getName() { return name;}
	public String getTgt() { return tgt;}
	public String getID() { return EDGE_ID;}
	public String getOSMID() { return OSMID;}
	public float getLength() { return length;}
	
	@Override
	public String toString() { return "(" + src + ", " + tgt + ", " + name + ", " + length + ")";}
}
