package Presentacion;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Test.StressTest;
import control.Edge;
import control.Graph;
import control.OutPutControl;
import control.Problem;
import control.ReadJSON;
import control.SearchAlg;
import control.State;
import control.StateSpace;

public class Main {
	
	static Graph g;
	static Problem pr;
	static StateSpace ss;
	static boolean prune;
	static OutPutControl oc;

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OutOfMemoryError, NoSuchAlgorithmException {
		
		pr = ReadJSON.Read("problema.json");
		ss = new StateSpace(pr.getGraphlfile());
		pr.setStateSpace(ss);
		g = ss.getG();
		System.out.println(pr.toString());
		System.out.println(g.toString());
		
		StressTest st = new StressTest();
		
		Scanner sc = new Scanner(System.in);
		oc = new OutPutControl();
		
		while(true) {
			
			System.out.println("\nCHOOSE AN OPTION FROM THE MENU : ");
			System.out.println("1 - Check if a node exists in the graph");
			System.out.println("2 - Get the coordinates of an existing node");
			System.out.println("3 - Get the list of adjacents nodes of an existing node");
			System.out.println("4 - Get the list of succesors of a given state");
			System.out.println("5 - Search algortihms");
			System.out.println("6 - Data Structure stress test");
			int opt = sc.nextInt();
			
			switch(opt) {
			case 1:
				BelongNode(sc);
				break;
			case 2:
				positionNode(sc);
				break;
			case 3:
				adjacentNode(sc);
				break;
			case 4:
				printSuccessors(sc);
				break;
			case 5:
				basicSearch(sc);
				break;
			case 6:
				showTests(st, sc);
				break;
			}
		}
	}
	
	private static void basicSearch(Scanner sc) throws NoSuchAlgorithmException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String result = "";
		double t_init = 0;
		double t_fin = 0;
		DecimalFormat df = new DecimalFormat("###.##");
		prune = PruneAsk(sc);
		SearchAlg sa = new SearchAlg(prune);
		System.out.println("..");
		System.out.println("1 - UCS");
		System.out.println("2 - BFS");
		System.out.println("3 - DFS");
		System.out.println("4 - Greedy Search*");
		System.out.println("5 - A*");
		
		int opt = sc.nextInt();
		
		switch(opt) {
		case 1:
			result = "";
			t_init = System.currentTimeMillis();
			result = sa.Busqueda_Acotada(pr, "UCS", 9999);
			t_fin = System.currentTimeMillis();
			result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
			System.out.println(result);
			date = new Date();
			oc.generateTxtFile(result, "UCS_RESULT_" + dateFormat.format(date));
			oc.generateGpxFile(result, "UCS_RESULT_" + dateFormat.format(date), sa.getEndNode(), pr);
			break;
		case 2:
			result = "";
			t_init = System.currentTimeMillis();
			result = sa.Busqueda_Acotada(pr, "BFS", 9999);
			t_fin = System.currentTimeMillis();
			result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
			System.out.println(result);
			date = new Date();
			oc.generateTxtFile(result, "BFS_RESULT_" + dateFormat.format(date));
			oc.generateGpxFile(result, "BFS_RESULT_" + dateFormat.format(date), sa.getEndNode(), pr);
			break;
		case 3:
			DFSAsk(sc);
			break;
		case 4:
			sa.setHeu(HeuAsk(sc));
			result = "";
			t_init = System.currentTimeMillis();
			result = sa.Busqueda_Acotada(pr, "GRE", 9999);
			t_fin = System.currentTimeMillis();
			result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
			System.out.println(result);
			date = new Date();
			oc.generateTxtFile(result, "GRE_RESULT_" + dateFormat.format(date));
			oc.generateGpxFile(result, "GRE_RESULT_" + dateFormat.format(date), sa.getEndNode(), pr);
			break;
		case 5:
			sa.setHeu(HeuAsk(sc));
			result = "";
			t_init = System.currentTimeMillis();
			result = sa.Busqueda_Acotada(pr, "A*", 9999);
			t_fin = System.currentTimeMillis();
			result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
			System.out.println(result);
			date = new Date();
			oc.generateTxtFile(result, "A_RESULT_" + dateFormat.format(date));
			oc.generateGpxFile(result, "A_RESULT_" + dateFormat.format(date), sa.getEndNode(), pr);
			break;
		}
	}
	
	private static int HeuAsk(Scanner sc) {
		System.out.println("Type of heuristic?");
		System.out.println("1 - H0 (Minimum distance between node and subgoals)");
		System.out.println("2 - H1 (H0 + Minimum distance between subgoals)");
		
		int opt = sc.nextInt();
		
		int def = 0;
		
		switch(opt) {
		case 1:
			def = 0;
			break;
		case 2:
			def = 1;
			break;
		default:
			def = 0;
			break;
		}
		return def;
	}
	
	
	private static boolean PruneAsk(Scanner sc) {
		System.out.println("Apply pruning?");
		System.out.println("1 - YES");
		System.out.println("2 - NO");
		
		int opt = sc.nextInt();
		
		boolean def = false;
		
		switch(opt) {
		case 1:
			def = true;
			break;
		case 2:
			def = false;
			break;
		default:
			def = false;
			break;
		}
		return def;
	}
	
	private static void DFSAsk(Scanner sc) throws NoSuchAlgorithmException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		SearchAlg sa = new SearchAlg(prune);
		double t_init = 0;
		double t_fin = 0;
		String result = "";
		DecimalFormat df = new DecimalFormat("###.##");
		System.out.println("..");
		System.out.println("1 - DFS");
		System.out.println("2 - DLS");
		System.out.println("3 - IDS");
		
		int opt = sc.nextInt();
		
		switch(opt) {
		case 1:
			result = "";
			t_init = System.currentTimeMillis();
			result = sa.Busqueda_Acotada(pr, "DFS", 9999);
			t_fin = System.currentTimeMillis();
			result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
			System.out.println(result);
			date = new Date();
			oc.generateTxtFile(result, "DFS_RESULT_" + dateFormat.format(date));
			oc.generateGpxFile(result, "DFS_RESULT_" + dateFormat.format(date), sa.getEndNode(), pr);
			break;
		case 2:
			DLSAsk(sc);
			break;
		case 3:
			IDSAsk(sc);
			break;
		}
	}
	
	private static void DLSAsk(Scanner sc) throws NoSuchAlgorithmException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		SearchAlg sa = new SearchAlg(prune);
		System.out.println("Provide a depth limit: ");
		String result = "";
		
		double t_init = 0;
		double t_fin = 0;
		DecimalFormat df = new DecimalFormat("###.##");
		
		int Inc = sc.nextInt();
		
		result = "";
		t_init = System.currentTimeMillis();
		result = sa.Busqueda_Acotada(pr, "DFS", Inc);
		t_fin = System.currentTimeMillis();
		result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
		System.out.println(result);
		date = new Date();
		oc.generateTxtFile(result, "DLS_RESULT_" + Inc + "_" + dateFormat.format(date));
		oc.generateGpxFile(result, "DLS_RESULT_" + Inc + "_" + dateFormat.format(date), sa.getEndNode(), pr);
	}
	
	private static void IDSAsk(Scanner sc) throws NoSuchAlgorithmException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String result = "";
		SearchAlg sa = new SearchAlg(prune);
		System.out.println("Provide an increment: ");
		
		double t_init = 0;
		double t_fin = 0;
		DecimalFormat df = new DecimalFormat("###.##");
		
		int Inc = sc.nextInt();
		
		result = "";
		t_init = System.currentTimeMillis();
		result = sa.Busqueda(pr, "DFS", 9999, Inc);
		t_fin = System.currentTimeMillis();
		result ="\nOUTPUT - TIME SPENT: " + df.format(t_fin - t_init) + " ms - PRUNE: " + prune + " - \n" + result  + "---";
		System.out.println(result);
		date = new Date();
		oc.generateTxtFile(result, "IDS_RESULT_" + Inc + "_" + dateFormat.format(date));
		oc.generateGpxFile(result, "IDS_RESULT_" + Inc + "_" + dateFormat.format(date), sa.getEndNode(), pr);
	}
	
	private static void printSuccessors(Scanner aux) {
		System.out.println("PROVIDE A NODE ID : ");
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			State st = new State(Long.toString(id));
			System.out.println("Printing successors of state AT " + id + " : ");
			ss.Succesors(st);
		}
	}
	
	private static void adjacentNode(Scanner aux) {
		System.out.println("PROVIDE A NODE ID : ");
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
	
	private static void positionNode(Scanner aux) {
		System.out.println("PROVIDE A NODE ID : ");
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
	
	private static void BelongNode(Scanner aux) {
		System.out.println("PROVIDE A NODE ID : ");
		long id = aux.nextLong();
		System.out.println("Checking if the node exists...");
		if (g.BelongNode(Long.toString(id))) {
			System.out.println("The node " + Long.toString(id) + " belongs to the graph " + pr.getGraphlfile());
		} else {
			System.out.println("The node " + Long.toString(id) + " doesn't belong to the graph " + pr.getGraphlfile());
		}
	}
	
	private static void showTests(StressTest st, Scanner sc) {
		System.out.println("..");
		System.out.println("1 - SortedSet");
		System.out.println("2 - ArrayList");
		System.out.println("3 - PriorityQueue");
		System.out.println("4 - Exit");
		System.out.println("Execution of the program will stop after any test");
		
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
