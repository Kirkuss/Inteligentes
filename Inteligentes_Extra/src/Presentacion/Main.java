package Presentacion;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Test.StressTest;
import control.Graph;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OutOfMemoryError {
		Graph g = new Graph("Anchuras.graphml");
		long t_init = System.currentTimeMillis();
		g.BelongNode("4331489709");
		System.out.println("BelongNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		t_init = System.currentTimeMillis();
		g.positionNode("4331489709");
		System.out.println("positionNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		t_init = System.currentTimeMillis();
		g.adjacentNode("4331489709");
		System.out.println("adjacentNode() executed in: " + (System.currentTimeMillis() - t_init) + " ms");
		
		StressTest st = new StressTest();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("##########");
		System.out.println("1 - SortedSet");
		System.out.println("2 - ArrayList");
		System.out.println("3 - PriorityQueue");
		System.out.println("##########");
		
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
