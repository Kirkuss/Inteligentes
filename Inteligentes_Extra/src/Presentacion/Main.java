package Presentacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Test.StressTest;
import control.Edge;
import control.Graph;
import control.Problem;
import control.ReadJSON;
import control.SearchAlg;
import control.State;
import control.StateSpace;

public class Main {
	
	static Graph g;
	static Problem pr;
	static StateSpace ss;

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OutOfMemoryError {
		/*Graph g = new Graph("Anchuras.graphml");
		long t_init = System.currentTimeMillis();
		g.BelongNode("4331489709");
		System.out.println("BelongNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		t_init = System.currentTimeMillis();
		g.positionNode("4331489709");
		System.out.println("positionNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		t_init = System.currentTimeMillis();
		g.adjacentNode("4331489709");
		System.out.println("adjacentNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		*/
		
		
		ReadJSON reader = new ReadJSON();
		pr = reader.Read("problema.json");
		ss = new StateSpace(pr.getGraphlfile());
		pr.setStateSpace(ss);
		g = ss.getG();
		System.out.println(pr.toString());
		System.out.println(g.toString());
		
		StressTest st = new StressTest();
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			System.out.println("\nCHOOSE AN OPTION FROM THE MENU : ");
			System.out.println("1 - Check if a node exists in the graph");
			System.out.println("2 - Get the coordinates of an existing node");
			System.out.println("3 - Get the list of adjacents nodes of an existing node");
			System.out.println("4 - Get the list of succesors of a given state");
			System.out.println("5 - Basic search algortihms");
			System.out.println("6 - Data Structure stress test");
			int opt = sc.nextInt();
			
			switch(opt) {
			case 1:
				BelongNode();
				break;
			case 2:
				positionNode();
				break;
			case 3:
				adjacentNode();
				break;
			case 4:
				printSuccessors();
				break;
			case 5:
				basicSearch();
				break;
			case 6:
				showTests(st);
				break;
			}
		}
	}
	
	private static void basicSearch() {
		SearchAlg sa = new SearchAlg();
		System.out.println("..");
		System.out.println("1 - UCS");
		System.out.println("2 - BFS");
		System.out.println("3 - DFS");
		
		Scanner sc = new Scanner(System.in);
		int opt = sc.nextInt();
		
		switch(opt) {
		case 1:
			System.out.println(sa.Busqueda_Acotada(pr, "UCS", 9999));
			break;
		case 2:
			System.out.println(sa.Busqueda_Acotada(pr, "BFS", 9999));
			break;
		case 3:
			DFSAsk();
			break;
		}		
	}
	
	private static void DFSAsk() {
		SearchAlg sa = new SearchAlg();
		System.out.println("..");
		System.out.println("1 - DFS");
		System.out.println("2 - DLS");
		System.out.println("3 - IDS");
		
		Scanner sc = new Scanner(System.in);
		int opt = sc.nextInt();
		
		switch(opt) {
		case 1:
			System.out.println(sa.Busqueda_Acotada(pr, "DFS", 9999));
			break;
		case 2:
			DLSAsk();
			break;
		case 3:
			IDSAsk();
			break;
		}		
	}
	
	private static void DLSAsk() {
		SearchAlg sa = new SearchAlg();
		System.out.println("Provide a depth limit: ");
		
		Scanner sc = new Scanner(System.in);
		int Inc = sc.nextInt();
		
		System.out.println(sa.Busqueda_Acotada(pr, "DFS", Inc));		
	}
	
	private static void IDSAsk() {
		SearchAlg sa = new SearchAlg();
		System.out.println("Provide an increment: ");
		
		Scanner sc = new Scanner(System.in);
		int Inc = sc.nextInt();
		
		System.out.println(sa.Busqueda(pr, "DFS", 9999, Inc));		
	}
	
	private static void printSuccessors() {
		System.out.println("PROVIDE A NODE ID : ");
		Scanner aux = new Scanner(System.in);
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			State st = new State(Long.toString(id));
			System.out.println("Printing successors of state AT " + id + " : ");
			ss.Succesors(st);
		}
	}
	
	private static void adjacentNode() {
		System.out.println("PROVIDE A NODE ID : ");
		Scanner aux = new Scanner(System.in);
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			ArrayList<Edge> edgeList = g.adjacentNode(Long.toString(id));
			for(int i=0; i<edgeList.size(); i++) {
				System.out.println("    " + edgeList.get(i).toString());
			}
			System.out.println();
		} else {
			System.out.println("[ERROR] : Node " + Long.toString(id) + " doesn't belong to the graph " + pr.getGraphlfile());
		}
	}
	
	private static void positionNode() {
		System.out.println("PROVIDE A NODE ID : ");
		Scanner aux = new Scanner(System.in);
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			float[] xy = g.positionNode(Long.toString(id));
			System.out.println("Node " + Long.toString(id) + " is at : ");
			System.out.println("  X : " + xy[0] + "\n  Y : " + xy[1]);
		} else {
			System.out.println("[ERROR] : Node " + Long.toString(id) + " doesn't belong to the graph " + pr.getGraphlfile());
		}
	}
	
	private static void BelongNode() {
		System.out.println("PROVIDE A NODE ID : ");
		Scanner aux = new Scanner(System.in);
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			System.out.println("The node " + Long.toString(id) + " belongs to the graph " + pr.getGraphlfile());
		} else {
			System.out.println("The node " + Long.toString(id) + " doesn't belong to the graph " + pr.getGraphlfile());
		}
	}
	
	private static void showTests(StressTest st) {
		System.out.println("..");
		System.out.println("1 - SortedSet");
		System.out.println("2 - ArrayList");
		System.out.println("3 - PriorityQueue");
		System.out.println("4 - Exit");
		System.out.println("Execution of the program will stop after any test");
		
		Scanner sc = new Scanner(System.in);
		int opt = sc.nextInt();
		
		switch(opt) {
		case 1:
			st.testSortSet();
			break;
		case 2:
			st.testList();
			break;
		case 3:
			st.testPQueue();
			break;
		}
	}

}
