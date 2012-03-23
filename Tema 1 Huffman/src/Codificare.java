
public class Codificare {
	Codificare(){	
	}
	/**
	 * @param st Statistics din care luam codul care va fi folosit pentru apelarea arbhmaker care va genera arborele huffman 
	 * @return un vector de codificari pentru fiecare caracter 
	 */
	public String[] Code(Statistics st) {
		ArboreH arb = new ArboreH();
		Arbore arbh = new Arbore();
		arbh = arb.arbhmaker(st);
		Arbore aux = new Arbore();
		aux = arbh;
		String cod[] = new String[st.lungVF];
		//parcurgem vectorul de frunze 
		for (int i=0; i < st.lungVF; i++){
			// folosim un arbore auxiliar pentru a reveni mai tarziu la parinte 
			aux = arbh ; 
			cod[i] = new String();
			// cat timp nu se ajunge la null 
			while(aux != null) {
				// comparam daca chr ul parintelui este egal cu frunza pe care o cautam in arbore daca da revenim in varf
				if(aux.leaf.chr.equals(st.frz[i].chr) == true ){
					break;
				}
				// daca exista caracaterul pe stanga atunci la cod se adauga 0 
				if(aux.arbst.leaf.chr.contains(st.frz[i].chr) == true ){
					aux = aux.arbst;
					cod[i]+="0";
				}
				// altfel se adauga 1
				else{
					aux = aux.arbdr;
					cod[i] += "1";
				}
			}
		}
		return cod;
	}
	/**
	 * Metoda care codifica textul primit prin st.text dupa codul din cod[]
	 * @param st este de tip Statistics si este folosit pentru a lua textul si caracterele 
	 * @param cod este un vector de coduri pentru fiecare caracter 
	 * @return textul codificat
	 */
	public String Codificat(String text,String[][] cod,int len){
		
		for(int i=0;i<cod.length;i++){
			if(cod[i][0].charAt(0) == '.'){
				String cod1 = new String("\\.");
				text = text.replaceAll(cod1,cod[i][1]);
			} else
			text=text.replaceAll(cod[i][0], cod[i][1]);
		
		}
		return text;
	}
	/**
	 * PrintCod afiseaza si returneaza o matrice de String in care pe prima coloana sunt caracterele si pe a doua coloana codificarea lor
	 * @param cod reprezinta un vector cu caracterele codificate 
	 * @param st este de tip Statistics si este folosit pentru a avea toate caracterele din st.text;
	 * @return s este o matrice de Stringuri in care prima coloana sunt caracterele si pe a doua coloana codificarea lor
	 */
	public String[][] PrintCod(String[] cod , Statistics st) {
		for(int i=0; i<st.lungVF ; i++){
			System.out.println((int)(st.frz[i].chr.charAt(0)) + " " + cod[i]);
		}
		
		String[][] s = new String[127][2];
		for(int i=0; i<st.lungVF;i++){
			s[i][0] = st.frz[i].chr;
			s[i][1]= cod[i];
		}
		return s;
	}
	
}
