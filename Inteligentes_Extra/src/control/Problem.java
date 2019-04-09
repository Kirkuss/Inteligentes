package control;

public class Problem {
	
	 private String graphlmfile;
	 private State IntSt;
	 //private StateSpace stateSpace;
	 
	 public Problem(String json, State IntSt) {
		 this.graphlmfile = json;
		 this.IntSt = IntSt;
	 }
	 
	 public Problem() {
		// TODO Auto-generated constructor stub
	}
	 
	public boolean isGoal(State st) {
		return false;
	}

	public String getGraphlfile() {
		 return graphlmfile;
	 }
	 
	 public void setGraphlmfile(String json) {
		 this.graphlmfile = json;
	 }
	 
	 public State getInSt() {
		 return IntSt;
	 }
	 
	 public void setInSt(State IntSt){
		 this.IntSt = IntSt;
	 }

	 public String toString() {
		 //String str = "Prueba " + graphlmfile ;
		 String str = "PROBLEM XML FILE : " + graphlmfile + "\nINITIAL STATE : \n" + IntSt.toString() + "    - \n";
		 return str;
	 }
}
