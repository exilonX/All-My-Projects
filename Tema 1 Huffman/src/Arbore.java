/**
 * @author merca
 * clasa arbore / nod contine o frunza adica informatia din acel nod .value si .chr inglobate intr-o clasa.
 */

public class Arbore{
	
	Frunza leaf;
	Arbore arbst; // arborele din stanga parintelui this 
	Arbore arbdr;	// arborele din dreapta parintelui this 
	// contructor fara parametrii care doar instantiaza continutul si nodurile stanga dreapta parintelui
	Arbore(){
		leaf = new Frunza();
	}
	Arbore(Arbore st, Arbore dr, Frunza f){
		arbst =st;
		arbdr=dr;
		leaf = new Frunza(f.value, f.chr);
	}
	public boolean isFrunza() {
		if(this.arbdr == null && this.arbst == null){
			return true;
		}
		else return false;
	}
	
}
