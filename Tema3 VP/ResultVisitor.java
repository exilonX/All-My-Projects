
import java.util.Map;
import java.util.TreeMap;

/**
 * implementare a patternului visitor care calculeaza rezultatele finale pentru variabile 
 * pe baza operatorilor / variabilelor / valorilor din arborele de parsare
 * @author Merca
 *
 */
public class ResultVisitor implements Visitor {
	// folosim un map pentru a retine pentru fiecare variabila tipul ei 
	Map<String, String> dex = new TreeMap<String,String>();
	// folosim un map pentru a retine pentru fiecare variabila valoarea ei 
	Map<String, String> value = new TreeMap<String,String>();
	// string ce este folosit pentru a retine tipul curent
	String ok;
	// retine valoarea curenta 
	String val;
	/**
	 * constructor 
	 */
	public ResultVisitor() {
		
	}
	@Override
	public void visit(OperatorPlus o) {
		// visitatorul pentru operatorul plus
		// vizitam copii operatorului si retine pentru fiecare valoarea curenta adica un fel de 
		// valoare returnata / calculata
		o.children.get(0).accept(this);
		String val1 = val;
		o.children.get(1).accept(this);
		// cum arborele este corect din start verificam doar tipul 
		// daca este int facem suma daca este bool facem sau logic 
			if(ok.equals("int")){
				int q= Integer.parseInt(val1) + Integer.parseInt(val);
				val = String.valueOf(q);
			}
			else if (ok.equals("bool")){
				boolean p = Boolean.parseBoolean(val1) || Boolean.parseBoolean(val);
				val = String.valueOf(p);
			}
			
		
		
	}
	
	@Override
	public void visit(OperatorInmultit o) {
		// visitatorul pentru operatorul inmultit
		// vizitam copii operatorului si retine pentru fiecare valoarea curenta adica un fel de 
		// valoare returnata / calculata
		o.children.get(0).accept(this);
		String val1 = val;
		o.children.get(1).accept(this);
			// facem inmultirea celor 2 valori sau si logic pentru bool
			if(ok.equals("int")){
				int q= Integer.parseInt(val1) * Integer.parseInt(val);
				val = String.valueOf(q);
				
			}
			else if (ok.equals("bool")){
				boolean p = Boolean.parseBoolean(val1) && Boolean.parseBoolean(val);
				val = String.valueOf(p);
			}
			
	}
	
	@Override
	public void visit(OperatorEgal o) {
		// se incepe o alta atribuire deci facem ok si val ce retin tipul si valoarea curenta 
		// sirul vid
		ok = "";
		val = "";
		// vizitam copilul din dreapta pentru ca cel din stanga este sigur o variabila si trebuie 
		// sa stim ce valoare sa ii atribuim 
		o.children.get(1).accept(this);
		// adaugam in dex tipul variabilei adica copilul din stanga
			if(ok.equals("int")){
			
				dex.put(o.children.get(0).ceva, "int");
				
			}
			else if(ok.equals("bool")){
				dex.put(o.children.get(0).ceva, "bool");
			}
			// adaugam valoarea copilului dn stanga
			value.put(o.children.get(0).ceva, val);
			
	}



	@Override
	public void visit(Valoare o) {
		// verificam tipul si atribuim acel tip lui ok care va retine tipul curent
		if(checkVal(o.ceva)){
			ok = "int";
		}
		else{
			ok = "bool";
		}
		// modificam si valoarea curenta
		val = o.ceva;	
		
	}

	@Override
	public void visit(Variabila o) {
			//daca se viziteaza o variabila se modifica tipul si valoarea curenta cu 
		// caracteristicile acelei variabile
			ok = dex.get(o.ceva);
			val = value.get(o.ceva);
	}
	/**
	 * verifica daca un string poate reprezenta un int
	 * @param s string ce va fi verificat
	 * @return boolean daca se verifica sau nu 
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
