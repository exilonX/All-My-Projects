import java.io.PrintWriter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Clasa Node este parintele tuturor claselor ce sunt folosite intr-un arbore de parsare 
 * @author Merca
 *
 */
public class Node  {
	String ceva;
	int col;
	int l;
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica orice = * + true false int sau variabila
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	Node(String s,int n,int li ){
		ceva = s;
		col = n;
		l= li;
	}
	/**
	 * este facuta doar pentru compile time dar de fapt nu o sa fie folosita niciodata 
	 * pentru ca in main apelez accept pentru fiecare copil al radacinii generice nu pentru radacina 
	 * generica insasi
	 * @param v reprezinta vizitatorul implementat intr o alta clasa
	 */
	public void accept(Visitor v){

	}
	
	public LinkedList<Node> children = new LinkedList<Node>();
	/**
	 * metoda ce afiseaza in fisier text arborele parcurs in adancime cu tab uri
	 * @param s nodul radacina 
	 * @param p un string folosit pentru a tipari bine tab urile initial gol apoi adaugand un tab 
	 * pentru fiecare apel recursiv
	 * @param in
	 */
	public void display(Node s,String p,PrintWriter in){
		
		in.println(p + s.ceva  );
		
		
		Iterator<? extends Node> it = s.children.iterator();
		while(it.hasNext()){
			display(it.next(),p + "\t",in);
		}	
	}

	
	/**
	 * functie care verifica daca un string este int 
	 * @param s Stringul de verificat
	 * @return true/false daca s poate reprezenta un int 
	 */
	private boolean checkVal(String s){
		 try  
		   {  
		      Integer.parseInt( s );  
		      return true;  
		   }  
		   catch( Exception e )  
		   {  
		      return false;  
		   }
	}
	/**
	 * functie care construieste arborele de parsare 
	 * @param text este un ArrayList ce contine toate liniile din fisierul de intrare 
	 */
	public void makeTree(Collection<String> text){
		//cele 2 stive de operatori si rezultat
		Stack<Node> operatori = new Stack<Node>();
		Stack<Node> rezultat = new Stack<Node>();
		String aux;
		String[] aux1;
			// initializam radacina generica
			this.ceva = "r";
			
			int j =0;
			// iteram prin liniile din fisierul de intrare 
			Iterator<String> it = text.iterator();
			while(it.hasNext()){
				j++;
				aux = it.next();
				// despartim operatorii din linia corespunzatoare dupa separatorul spatiu 
				aux1 = aux.split(" ");
				// este un int care este folosit pentru a preciza coloana pe care se afla un operator 
				// se mareste doar cand un operator/valoare/variabila  are o lungime mai mare de 1 
				int dep = 0;
				for(int i=0;i<aux1.length;i++){
					// daca este un operator 
					if(aux1[i].equals("+")||aux1[i].equals("*")||aux1[i].equals("=")){
						// daca stiva de operatori este goala adaugam in stiva de tipul respectiv 
						if(operatori.isEmpty()){
							if(aux1[i].equals("=")){
								
								operatori.push(new OperatorEgal(aux1[i],2*i+1+dep,j));
							
							}else
									if(aux1[i].equals("+")){
										operatori.push(new OperatorPlus(aux1[i],2*i+1+dep,j));
							}
							else 
									if(aux1[i].equals("*")){
										operatori.push(new OperatorInmultit(aux1[i],2*i+1+dep,j));
							}
							
						}	// daca nu este goala stiva de operatori 
							else{
								@SuppressWarnings("unused")
								int ok=1;
								//cat timp operatorul din varful stivei are prioritate mai mare 
								while(operatori.peek().ceva.equals("*")&&aux1[i].equals("+")){
									ok=0;
									
									Node r1, r2, r3,x;
									// formam un rezultat intermediar sub forma unui subarbore format 
									// din primele 2 rezultate si primul operator adica * 
									r2 = rezultat.pop();
									r1 = rezultat.pop();
									r3 = operatori.pop();
									x = r3;
									x.children.add(r1);
									x.children.add(r2);
									// introducem rezultatul intermediar in stiva de rezultate
									rezultat.push(x);
									/*
									if(aux1[i].equals("=")){
										operatori.push(new OperatorEgal(aux1[i],2*i+1+dep,j));
										}else if(aux1[i].equals("+")){
											operatori.push(new OperatorPlus(aux1[i],2*i+1+dep,j));
										}
										else if(aux1[i].equals("*")){
											operatori.push(new OperatorInmultit(aux1[i],2*i+1+dep,j));
										}*/
								}
						//	if(ok==1){
								// dupa ce se iese din while adaugam si operatorul care nu a fost adaugat 
								// adica cel curent 
								if(aux1[i].equals("=")){
									operatori.push(new OperatorEgal(aux1[i],2*i+1+dep,j));
									}else if(aux1[i].equals("+")){
										operatori.push(new OperatorPlus(aux1[i],2*i+1+dep,j));
									}
									else if(aux1[i].equals("*")){
										operatori.push(new OperatorInmultit(aux1[i],2*i+1+dep,j));
									}
							//}
						}
						
					}
					else{
						// testam daca este valoare si ce lungime are marind dep adica deplasarea pe coloane
						
						if(aux1[i].equals("true")||aux1[i].equals("false")||checkVal(aux1[i])==true){
							rezultat.push(new Valoare(aux1[i],2*i+1+dep,j));
					
 									if(aux1[i].length()!=1){
									dep = aux1[i].length() -1;
								}
							
								 
							
						}
						// daca este variabila o adaugam in rezultat ca variabila 
						else{
							rezultat.push(new Variabila(aux1[i],2*i+1+dep,j));
						}
					}
				
				} 
				// scoatem toti operatorii si toate rezultatele ramanand cu un subarbore in stiva de rezultate
				while(operatori.size()>0){
					Node r1 = rezultat.pop();
					Node r2 = rezultat.pop();
					Node r3 = operatori.pop();
					Node x ;
					x = r3;
					x.children.add(r2);
					x.children.add(r1);
					rezultat.push(x);
				}
				Node a = rezultat.pop();
				// in cazul in care este doar o linie in fisierul de intrare atunci nu mai facem radacina generica
				if(text.size() ==1){
					
					this.ceva = a.ceva;
					this.children = a.children;
					this.col = a.col;
					this.l = a.l;
				}
				else
				{
					// adaugam la copii radacinii generice subarborele pentru linia curenta
					this.children.add(a);
				}
				rezultat = new Stack<Node>();
				operatori = new Stack<Node>();
			}// final while 
			// aici 
		
		
	}
	@Override
	public String toString() {
		String s ;
		s = this.ceva + "(";
		Iterator<Node> it = this.children.iterator();
	
		while(it.hasNext()){
			s += it.next() + ",";
			
		}
		s = s +  ")";
		return s ;
	}
}
