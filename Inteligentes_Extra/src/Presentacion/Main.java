package Presentacion;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import control.Graph;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
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
	}

}
