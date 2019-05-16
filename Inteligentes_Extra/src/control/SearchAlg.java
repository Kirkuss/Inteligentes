package control;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class SearchAlg {

	private ArrayList<String> subgoals = new ArrayList<String>();
	private Frontier fringe;
	
	public String Busqueda_Acotada(Problem prob, String estrategia, int Prof_Max) {
		//Proceso de inicializacion
		CopySubgoals(prob.getInSt().getListNodes());
		fringe = new Frontier();
		ArrayList<TreeNode> visited = new ArrayList<TreeNode>();
		TreeNode n_inicial = new TreeNode(null, prob.getInSt(), 0, 0, 0);
		TreeNode n_actual = new TreeNode();
		fringe.Insert(n_inicial);
		boolean solucion = false;
		//Bucle de busqueda
		while (!solucion && !fringe.isEmpty()) {
			n_actual = fringe.Remove();
			System.out.println(subgoals);
			if(subgoals.get(0).equals(n_actual.getCurrentState().getNode())) {
				System.out.println("Paren las rotativas se ha encontrado un subgoal");
				subgoals.remove(n_actual.getCurrentState().getNode());
				fringe.Clear();
				visited.clear();
				System.out.println(subgoals);
			}
			System.out.println("n_actual: " + n_actual.getCurrentState().getNode() + " coste actual del camino: " + n_actual.getF());
			if (prob.isGoal(n_actual.getCurrentState())) {
				solucion = true;
			}else {
				PriorityQueue<control.Node> LS = prob.Sucesores(n_actual.getCurrentState());
				PriorityQueue<TreeNode> LN = CreaListaNodosArbol(LS, n_actual, Prof_Max, estrategia, visited);
				if(LN == null) return null;
				visited.add(n_actual);
				fringe.insertaLista(LN);
				fringe.printFringe();
			}
		}
		if (solucion) {
			return CreaSolucion(n_actual);
		}else {
			return CreaSolucion(n_actual);
		}
	}
	
	public String Busqueda(Problem prob, String estrategia, int Prof_Max, int Inc_Prof) {
		int Prof_Actual = Inc_Prof;
		String solucion = null;
		while (solucion == null && Prof_Actual <= Prof_Max) {
			solucion = Busqueda_Acotada(prob, estrategia, Prof_Actual);
			Prof_Actual = Prof_Actual + Inc_Prof;
		}
		return solucion;
	}
	
	public PriorityQueue<TreeNode> CreaListaNodosArbol(PriorityQueue<control.Node> LS, TreeNode n_actual, int Prof_Max, String estrategia, ArrayList<TreeNode> visited){
		PriorityQueue<TreeNode> LN = new PriorityQueue<TreeNode>();
		double f = 0;
		int prof_Act = n_actual.getDepth();
		int prof_Sig = prof_Act + 1;
		while(!LS.isEmpty()) {
			control.Node listNode = LS.poll();
			State st = new State(listNode.getID());
			//Asignacion de coste conforme a la estrategia
			switch(estrategia) {
			case "UCS":
				f = listNode.getF() + n_actual.getF();
				break;
			case "DFS":
				f = -prof_Sig;
				break;
			case "BFS":
				f = prof_Sig;
				break;
			}
			st.setListNodes(n_actual.getCurrentState().getListNodes());
			//REVISAR AQUI COMO FUNCIONA LA LISTA DE NODOS POR LOS QUE PASAR.
			if(!(prof_Sig > Prof_Max)) {
				st.setListNodes(subgoals);
				TreeNode listTree = new TreeNode(n_actual, st, prof_Sig, n_actual.getCost(), f);
				if(prof_Act != 0) {
					//if(!listTree.getCurrentState().getNode().equals(n_actual.getParent().getCurrentState().getNode())) {
						if(!containsNode(visited, listTree)) {
							LN.add(listTree);
						//}
					}
				}else {
					LN.add(listTree);
				}
			}else {
				return null; //Parada en caso de exceder profundidad maxima
			}
		}
		return LN;
	}
	
	private boolean containsNode(ArrayList<TreeNode> visited, TreeNode tn) {
		for(int i = 0; i<visited.size(); i++) {
			if(visited.get(i).getCurrentState().getNode().equals(tn.getCurrentState().getNode())) {
				return true;
			}
		}
		return false;
	}
	
	public String CreaSolucion(TreeNode tn) {
		String solucion = "";
		if(tn.getParent() == null) {
			solucion += tn.getCurrentState().getNode();
			return solucion;
		}else {
			solucion += CreaSolucion(tn.getParent()) +  " -> " + tn.getCurrentState().getNode();
		}
		return solucion;
	}
	
	private void CopySubgoals(ArrayList<String> subgoals) {
		for(int i = 0; i<subgoals.size(); i++) {
			this.subgoals.add(subgoals.get(i));
		}
	}
}
