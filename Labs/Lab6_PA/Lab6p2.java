import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

enum Color {
  WHITE, GRAY, BLACK
}

class Node implements Comparable<Node> {
  public Color color;
  public int finishTime;
  public String subjectName;
  public int inDegree;
  public Vector<Node> neigh = new Vector<Node>();
  public Vector<Node> inNeigh = new Vector<Node>();

  public Node(String subjectName) {
    this.subjectName = subjectName;
  }

  @Override
      public int compareTo(Node o) {
        return o.finishTime - this.finishTime;
      }
}

@SuppressWarnings("serial")
class Graph extends Vector<Node> {
}

public class Lab6p2 {

  static HashMap<String, Integer> subjectToInt = new HashMap<String, Integer>();

  static int time;
  static Stack<Node> s = new Stack<Node>();

  static void dfs(Node node) {
    /* TODO: Puteti folosi aceasta functie pentru a implementa o parcurgere in
     * adancime (in cadrul careia sa completati si timpii de finish). */
	  node.color = Color.GRAY;
	  time++;
	  for (int  i=0; i< node.neigh.size();i++){
		  Node v = node.neigh.get(i);
		  if (v.color==Color.WHITE)
			  dfs(v);
	  }
	  node.color=Color.BLACK;
	  node.finishTime=time;	
	  s.add(node);
  }

  static void topologicalSorting(Graph graph) {
    /* TODO: Sortati vectorul primit ca parametru astfel incat sa contina
     * nodurile in ordine topologica. */
	  
	  for (int i = 0; i< graph.size(); i++)
	  {
		  Node u = graph.get(i);
		  if (u.color == Color.WHITE)
			  dfs(u);
	  }
	  
		  
  }

  static void planYears(Graph graph, Vector<Vector<String>> yearlyPlanning) {
    /* TODO: Folosind algoritmul lui Kahn, impartiti materiile la care fac
     * referire nodurile din vectorul graph pe ani, astfel incat:
     *
     * (a) Daca o materie A depinde de o materie B, atunci anul in care se face
     * materia A trebuie sa fie _strict_ mai mare decat anul in care se face
     * materia B.
     *
     * (b) Intr-un an nu se pot face mai mult de 5 materii.
     *
     * (c) Fiecare matrie trebuie planificata.
     *
     * In varaibila yearly planning veti pune in ordine, vectori de stringuri care
     * contin materiile ce vor fi parcurse in anii consecutivi de studiu: 1, 2, 3,
     * etc.
     */
	  
	  List<Node> lst = new ArrayList<Node>();
	  Map<String, Integer> grad = new HashMap<String,Integer>();
	  int year=0;
	  Vector<String> vct = new Vector<String>();
	  for (int i=0; i<5; i++)
		  yearlyPlanning.add(year,new Vector<String>());
	  
	  
	  boolean ok=true;
	  
	  for (int i=0; i< graph.size();i++)
		  grad.put(graph.get(i).subjectName, 0);
	  
	  while (ok){
		  ok=false;
		  
		  year++;
		  
		  
	  for (int i=0; i< graph.size();i++)
	  {
		  Node ni = graph.get(i);
		  if (ni.inDegree == 0 && ni.color== Color.WHITE )
		  {
		  	ni.color=Color.BLACK;
		  	lst.add(ni);
		  	
		  	vct = yearlyPlanning.get(year);
		  	if (vct.size()<5)
		  		vct.add(graph.get(i).subjectName);
		  	yearlyPlanning.add(vct);
		  }
		  
		else ok= false;
	  
	  
	  while (!lst.isEmpty()){
		  Node n = lst.get(0);
		  Vector<Node> nd = n.neigh;
			  for (int j=0; j<nd.size(); j++)
				  if (nd.get(j).subjectName.compareTo(n.subjectName)==0)
					  nd.remove(j);
		  lst.remove(0);
		  n.neigh=nd;
	  	}
	  }
	  }
	  
		  
  }

  public static void main(String[] args) throws FileNotFoundException {
    /* Declaram un graf. */
    Graph graph = new Graph();

    /* Citim un graf din fisierul de intrare. */
    Scanner scanner = new Scanner(new File("Materii.txt"));
    int edgeCount = scanner.nextInt();

    String subjectA, subjectB;
    for (int i = 0; i < edgeCount; ++i) {

      subjectA = scanner.next();
      scanner.next();
      subjectB = scanner.next();

      /*
       * Daca n-am mai vazut niciodata subjectA, atunci creaza-i un cod
       * unic.
       */
      if (subjectToInt.containsKey(subjectA) == false) {
        int code = subjectToInt.size();
        subjectToInt.put(subjectA, code);
        graph.add(new Node(subjectA));
      }

      /* La fel facem si cu subjectB. */
      if (subjectToInt.containsKey(subjectB) == false) {
        int code = subjectToInt.size();
        subjectToInt.put(subjectB, code);
        graph.add(new Node(subjectB));
      }

      /* Adaugam subjectB ca vecin al lui subjectA. */
      graph.get(subjectToInt.get(subjectA)).neigh.add(graph
                                                      .get(subjectToInt.get(subjectB)));
      graph.get(subjectToInt.get(subjectB)).inNeigh.add(graph
              .get(subjectToInt.get(subjectA)));
      graph.get(subjectToInt.get(subjectB)).inDegree++;
    }

    topologicalSorting(graph);
    
    while (!s.isEmpty())
    	System.out.println(s.pop().toString());
    
    for (int i=0; i< graph.size();i++)
    	graph.get(i).color=Color.WHITE;

    System.out.println("O posibila sortare topologica este: ");
    for (int i = 0; i < graph.size(); ++i) {
      System.out.println("\t" + graph.get(i).subjectName);
    }

    /* Facem impartirea pe ani. */
    Vector<Vector<String>> yearlyPlanning = new Vector<Vector<String>>();
    planYears(graph, yearlyPlanning);

    System.out.println();
    System.out.println("Iar o impartire pe ani ar putea fi urmatoarea: ");
    for (int i = 0; i < yearlyPlanning.size(); ++i) {
      System.out.println("Anul " + (i + 1));
      Vector<String> subjects = yearlyPlanning.get(i);
      for (int j = 0; j < subjects.size(); ++j) {
        System.out.println("\t" + subjects.get(j));
      }
    }
  }
}
