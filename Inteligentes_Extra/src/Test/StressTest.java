package Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

public class StressTest {
	private Queue<control.Node> queueNode = new PriorityQueue<control.Node>();
	private ArrayList<control.Node> listNode = new ArrayList<control.Node>();
	private SortedSet<control.Node> sortNode = new TreeSet<control.Node>();
	
	private int counter = 1;
	private long ini = 0;
	private long fin = 0;
	private long elapsed = 0;
	private long min = 9999, max = 0, val = 0;
	
	public void testSortSet() {
		while (true) {
			try {
				control.Node nodo = new control.Node();
				ini = System.currentTimeMillis();
				sortNode.add(nodo);
				fin = System.currentTimeMillis();
				elapsed = fin - ini;
				if (elapsed < min && elapsed > 0) {
					min = elapsed;
				} else if (elapsed > max) {
					max = elapsed;
				}
				val += elapsed;
				if (counter % 10000 == 0) {
					printResults(min, max, counter, val);
					/*Iterator it = sortNode.iterator();
					while(it.hasNext()) {
						nodo = (control.Node) it.next();
						System.out.println(nodo.getF());
					}*/
				}
				counter += 1;
			}catch(Exception | Error exception) {
				printResults(min, max, counter, val);
			}
		}
	}
	
	public void testPQueue() {	
		while (true) {
			try {
				control.Node nodo = new control.Node();
				ini = System.currentTimeMillis();
				queueNode.add(nodo);
				fin = System.currentTimeMillis();
				elapsed = fin - ini;
				if (elapsed < min && elapsed > 0) {
					min = elapsed;
				} else if (elapsed > max) {
					max = elapsed;
				}
				val += elapsed;
				if (counter % 10000 == 0) {
					printResults(min, max, counter, val);
					/*for(int i = 0; i<10; i++) {
						System.out.println(queueNode.poll().getF());
					}*/
				}
				counter += 1;
			}catch(Exception | Error exception) {
				printResults(min, max, counter, val);
			}
		}
	}
	
	public void testList() {
		while (true) {
			try {
				control.Node nodo = new control.Node();
				ini = System.currentTimeMillis();
				listNode.add(nodo);
				fin = System.currentTimeMillis();
				elapsed = fin - ini;
				if (elapsed < min && elapsed > 0) {
					min = elapsed;
				} else if (elapsed > max) {
					max = elapsed;
				}
				val += elapsed;
				if (counter % 10000 == 0) {
					ini = System.currentTimeMillis();
					Collections.sort(listNode);
					//Collectioms.reverse(listNode);
					fin = System.currentTimeMillis();
					printResults(min, max, counter, val);
					System.out.println("Sorting time for " + counter + " nodes : " + (fin-ini) + " ms");
					/*for (int i = 0; i<listNode.size(); i++) {
						System.out.println(listNode.get(i).getF());
					}*/
				}
				counter += 1;
			}catch(Exception | Error exception) {
				printResults(min, max, counter, val);
			}
		}
	}
	
	private void printResults(long min, long max, int counter, long val) {
		double mean = 0;
		mean = (double) val / counter;
		System.out.println("##########");
		System.out.println("MIN < " + min + " ms\nMAX : " + max + " ms\nTOTAL INSERTIONS : " + counter + "\nTOTAL TIME : " + val + " ms\nMEAN : " + mean + " ms");
		System.out.println("##########");
	}
}
