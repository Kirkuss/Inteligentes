package control;

import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class SearchAlg {

	private int nodeCount;
	private TreeNode endNode;
	private Frontier fringe;
	private Problem prob;
	private boolean prune;
	private int heuristic;
	private Hashtable<String, TreeNode> visited;
	
	public SearchAlg(boolean prune) {
		this.prune = prune;
		this.visited = new Hashtable<String, TreeNode>();
		this.nodeCount = 0;
	}
	
	public void setHeu(int heu) {
		this.heuristic = heu;
	}
	
	public String Busqueda_Acotada(Problem prob, String estrategia, int Prof_Max) throws NoSuchAlgorithmException {
		//Proceso de inicializacion
		this.prob = prob;
		fringe = new Frontier();
		TreeNode n_inicial = new TreeNode(null, prob.getInSt(), 0, 0, 0);
		TreeNode n_actual = new TreeNode();
		fringe.Insert(n_inicial);
		nodeCount++;
		boolean solucion = false;
		//Bucle de busqueda
		while (!solucion && !fringe.isEmpty()) {
			n_actual = fringe.Remove();
			if (prob.isGoal(n_actual.getCurrentState())) {
				solucion = true;
			}else {
				ArrayList<control.Node> LS = prob.Sucesores(n_actual.getCurrentState());
				PriorityQueue<TreeNode> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia);
				visited.put(n_actual.getCurrentState().getId(), n_actual);
				nodeCount += LN.size();
				fringe.insertaLista(LN);
			}
		}
		if (solucion) {
			DecimalFormat df = new DecimalFormat("###.##");
			endNode = n_actual;
			return "\nSOLUTION DEPTH: " + (n_actual.getDepth() + 1) + "\nGENERATED NODES: " + nodeCount + "\nESTRATEGY: " + estrategia + "\nTOTAL COST: " + df.format(n_actual.getCost()/1000) + " km\n" + CreaSolucion(n_actual);
		}else {
			visited.clear();
			return "";
		}
	}
	
	public TreeNode getEndNode() {
		return endNode;
	}
	
	public String Busqueda(Problem prob, String estrategia, int Prof_Max, int Inc_Prof) throws NoSuchAlgorithmException {
		int Prof_Actual = Inc_Prof;
		String solucion = "";
		while (solucion.equals("") && Prof_Actual <= Prof_Max) {
			solucion = Busqueda_Acotada(prob, estrategia, Prof_Actual);
			Prof_Actual += Inc_Prof;
		}
		return solucion;
	}
	
	public PriorityQueue<TreeNode> CreaListaNodosArbol(ArrayList<control.Node> LS, TreeNode n_actual, int Prof_Max, String estrategia) throws NoSuchAlgorithmException{
		PriorityQueue<TreeNode> LN = new PriorityQueue<TreeNode>();
		double f = 0;
		int prof_Act = n_actual.getDepth();
		int prof_Sig = prof_Act + 1;
		for(int i = 0; i<LS.size(); i++) {
			control.Node listNode = LS.get(i);
			State st = new State(listNode.getID());
			st.setListNodes(n_actual.getCurrentState().getListNodes());
			switch(estrategia) {
			case "UCS":
				f = listNode.getF() + n_actual.getF();
				break;
			case "DFS":
				f = - prof_Sig;
				break;
			case "BFS":
				f = prof_Sig;
				break;
			case "GRE":
				f = setHeu(st, heuristic);
				break;
			case "A*":
				f = setHeu(st, heuristic) + (listNode.getF() + n_actual.getCost());
				break;
			}
			st.setListNodes(n_actual.getCurrentState().getListNodes());
			if(!(prof_Sig > Prof_Max)) {
				TreeNode listTree = new TreeNode(n_actual, st, prof_Sig, listNode.getF() + n_actual.getCost(), f);
				if(prune) {
					if(!visited.containsKey(listTree.getCurrentState().getId())) {
						LN.add(listTree);
					}else if(isBetter(listTree)){
						visited.put(listTree.getCurrentState().getId(), listTree);
						LN.add(listTree);
					}
				}else {
					LN.add(listTree);
				}
			}
		}
		return LN;
	}
	
	private boolean isBetter(TreeNode tn) {
		TreeNode tn1 = visited.get(tn.getCurrentState().getId());
		if(Math.abs(tn.getF()) < Math.abs(tn1.getF())) {
			return true;
		}
		return false;
	}
	
	private double setHeu(State tn1, int tipo) {
		double min = 999999;
		double heu = 0;
		double heu0 = 0;
		double actual_Dist = 0;
		control.Node nActual =  prob.getStateSpace().getG().getNode(tn1.getNode());
		ArrayList<String> subgoals = tn1.getListNodes();
		for(int i = 0; i<subgoals.size(); i++) {
			control.Node tn2 = prob.getStateSpace().getG().getNode(subgoals.get(i));
			actual_Dist = calculateDistance(nActual,tn2);
			if(actual_Dist < min) {
				min = actual_Dist;
				heu = actual_Dist;
			}
		}

		min = 999999;

		if(tipo == 1) {
			for(int i = 0; i<subgoals.size() - 1; i++) {
				for(int j = i+1; j<subgoals.size(); j++) {
					control.Node tn3 = prob.getStateSpace().getG().getNode(subgoals.get(i));
					control.Node tn4 = prob.getStateSpace().getG().getNode(subgoals.get(j));
					actual_Dist = calculateDistance(tn3,tn4);
					if(actual_Dist < min) {
						min = actual_Dist;
						heu0 = actual_Dist;
					}
				}
			}
			heu += heu0;
		}
		
		return heu;
	}
	
	private double calculateDistance(control.Node tn1, control.Node tn2) {
		double[] lnglat1 = {Double.valueOf(tn1.getX()), Double.valueOf(tn1.getY())};
		double[] lnglat2 = {Double.valueOf(tn2.getX()), Double.valueOf(tn2.getY())};
		double earthR = 6371009;
			
			
		double phi1 = Math.toRadians(lnglat1[1]);
		double phi2 = Math.toRadians(lnglat2[1]);
		double diffPhi = phi2 - phi1;
			
		double theta1 = Math.toRadians(lnglat1[0]);
		double theta2 = Math.toRadians(lnglat2[0]);
		double diffTheta = theta2 - theta1;
			
		double h = Math.pow(Math.sin(diffPhi/2), 2) + Math.pow(Math.sin(Math.cos(phi1) * Math.cos(phi2) * diffTheta/2), 2);
		double arc = 2 * Math.asin(Math.sqrt(h));
		
		return arc * earthR;
	}
	
	public String CreaSolucion(TreeNode tn) {
		DecimalFormat df = new DecimalFormat("###.##");
		String solucion = "";
		if(tn.getParent() == null) {
			solucion += "\nStarting from: " + tn.getCurrentState().getNode() + "\n";
			return solucion;
		}else {
			solucion += CreaSolucion(tn.getParent()) + tn.getParent().getCurrentState().getNode() +  " -> " + tn.getCurrentState().getNode() + " (F: " + df.format(tn.getF()) + ", D: " + tn.getDepth() + ", Cost: " + df.format(tn.getCost()) + ")\nStreet name: " + prob.getStateSpace().getG().getEdge(tn.getParent().getCurrentState().getNode() + tn.getCurrentState().getNode()).getName() + "\nIN: " + tn.getCurrentState().getNode() + "\nREMAINING: " + tn.getCurrentState().getListNodes() + "\n";
		}
		return solucion;
	}
}
