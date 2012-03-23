
public class Decodificare {
	Decodificare(){
		
	}
	/**
	 * 
	 * @param cod reprezinta o matrice care pe prima coloana o sa contina codul ascii al caracterelor de decodat si pe a doua coloana 
	 * codul caracterului corespunzator
	 * @param textcod reprezinta textul codificat ce va fi decodificat
	 * @return textcod decodificat
	 */
	public String Decode(String[][] cod,String textcod,int len) {
			// arb reprezinta un arbore ce se va forma pe baza matricei cod adaugand daca este nevoie parintelui arbore stanga / drepta
			Arbore arb = new Arbore();
			// aux arbore auxiliar folosit pentru a reveni la radacina 
			Arbore aux = new Arbore();
			
			for(int i =0 ; i< len; i++){
				aux = arb;
				String parc = cod[i][1];
				// parcurgem fiecare cod si odata cu el parcurgem si arborele si 
				for(int k=0; k < parc.length(); k++) {
					// daca codul contine 1 ne ducem pe ramura dreapta si adaugam daca nu exista deja un nod  
					if(parc.charAt(k) == '1'){
						if(aux.arbdr==null){
							aux.arbdr = new Arbore();
							aux.arbdr.leaf.chr = cod[i][0];
						}
						
					
						aux = aux.arbdr;
					}
					else if(parc.charAt(k) == '0') {
						if(aux.arbst==null){
							aux.arbst = new Arbore();
							aux.arbst.leaf.chr = cod[i][0];
						}
						
						aux = aux.arbst;
						
					}
				}
				
			}
			// decodificam secventa de 0/1 parcurgand arborele pentru fiecare cod pana cand intalnim o frunza deci codul de mai inainte decodifica chr ul din aceea frunza
			// stringul rezultat
			String rez =  new String();
			aux = arb;
			int n= textcod.length();
			for(int i=0 ; i<n; i++){
				
				// daca contine 1 sau 0 si nu este frunza avansam 
				if(textcod.charAt(i) == '1' && aux.arbdr.isFrunza()!=true){
					aux = aux.arbdr;
				}
				else if(textcod.charAt(i)== '0' && aux.arbst.isFrunza()!= true){
					aux = aux.arbst;
				}
				else // daca este frunza avansam si memoram rezultatul adaugand la rezultat aux.leaf.chr si revenim la inceput 
					if(textcod.charAt(i)=='1' && aux.arbdr.isFrunza() == true){
						
						aux = aux.arbdr;
						rez += aux.leaf.chr; 
						aux = arb;
					}
					else
						if(textcod.charAt(i)=='0' && aux.arbst.isFrunza() == true){
							aux = aux.arbst;
							rez += aux.leaf.chr; 
							aux = arb;
								}
			
			}
			return rez;
	
		}
}