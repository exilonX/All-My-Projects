import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * implementare a patternului visitor ce realizeaza o verificare semantica a unor atribuiri 
 * dintr-un fisier sursa 
 * @author Merca
 *
 */
public class SemanticVisitor implements Visitor {
	// se retin tipurile variabilelor
	Map<String, String> dex = new TreeMap<String,String>();
	// string ce este folosit pentru a retine tipul curent
	String ok;
	// fisierul de iesire 
	PrintWriter in;
	// o variabila ce retine daca s-a descoperit o eroare
	Boolean error = false;
	/**
	 * Constructor
	 * @param p fisierul de iesire
	 */
	public SemanticVisitor(PrintWriter p) {
		in = p;
	}
	@Override
	public void visit(OperatorPlus o) {
		// se viziteaza copii operatorului plus si se retine tipul fiecaruia
		o.children.get(0).accept(this);
		String ok1 = ok;
		o.children.get(1).accept(this);
		// daca nu au acelasi tip eroare 
		if(!ok1.equals(ok)){
			
			if((o.children.get(1) instanceof Variabila || o.children.get(1) instanceof Valoare)&& !ok.equals("eroare")){
				in.println("+ intre tipuri incompatibile la linia "+ o.l + " coloana "+ o.col  );
				ok="eroare";
				error = true;
			}
		}
		
	}
	
	@Override
	public void visit(OperatorInmultit o) {
		// se viziteaza copii si se retine tipul fiecaruia 
		o.children.get(0).accept(this);
		String ok1 =ok;
		o.children.get(1).accept(this);
		// daca sunt de tipuri diferite eroare
		if(!ok1.equals(ok)){
			if(!ok.equals("eroare")&&(o.children.get(1) instanceof Variabila || o.children.get(1) instanceof Valoare)){
				in.println("* intre tipuri incompatibile la linia "+ o.l + " coloana "+ o.col  );
				ok="eroare";
				error = true;
			}
			
		}
		
		if(o.children.get(0) instanceof Variabila){
			
			o.children.get(0).accept(this);
		}	
	}
	@Override
	public void visit(OperatorEgal o) {
		// se initializeaza variabiala ce retine tipul cu sirul vid
		ok = "";
		// se parcurge copilul din dreapta si daca cel din stanga este valoare eroare
		o.children.get(1).accept(this);
		if(o.children.get(0) instanceof Valoare){
			in.println("membrul stang nu este o variabila la linia " + o.children.get(0).l + " coloana " + o.children.get(0).col  );
			error = true;
		} else {
			// se adauga in dictionar tipul variabilei din stanga
			if(ok.equals("int")){
				dex.put(o.children.get(0).ceva, "int");
		
			}
			else if(ok.equals("bool")){
				dex.put(o.children.get(0).ceva, "bool");
			}
			
		}
	}



	@Override
	public void visit(Valoare o) {
		// se verifica tipul valorii si se retine in ok
		if(checkVal(o.ceva)){
			ok = "int";
		}
		else{
			ok = "bool";
		}
		
		
	}

	@Override
	public void visit(Variabila o) {
		//daca nu exista variabila in dictionar eroare 
		if(!dex.containsKey(o.ceva)){
			in.println(o.ceva +" nedeclarata la linia " + o.l + " coloana "+ o.col );
				ok="eroare";
				error = true;
			}
		else{
			// altfel se modifica tipul lui ok
			ok = dex.get(o.ceva);
		}
	}
	/**
	 * verifica daca un string poate fi un int
	 * @param s string ul ce se verifica
	 * @return daca da sau nu 
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

}
