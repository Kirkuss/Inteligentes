package control;

import java.io.IOException;
import java.util.PriorityQueue;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Problem {
	
	 private String graphlmfile;
	 private State IntSt;
	 private StateSpace stateSpace;
	 
	 public Problem(String json, State IntSt) throws ParserConfigurationException, SAXException, IOException {
		 this.graphlmfile = json;
		 this.IntSt = IntSt;
	 }
	 
	 public void setStateSpace(StateSpace ss) {
		 this.stateSpace = ss;
	 }
	 
	 public Problem() {
		// TODO Auto-generated constructor stub
	}
	 
	public boolean isGoal(State st) {
		return st.getListNodes().isEmpty();
	}
	
	public PriorityQueue<control.Node> Sucesores(State st){
		return stateSpace.Succesors(st);
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
