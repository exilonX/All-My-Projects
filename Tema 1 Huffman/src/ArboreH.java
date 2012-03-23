/**
 * Clasa in care se va forma arborele huffman 
 * @author merca
 *
 */
public class ArboreH  {
	/**
	 * 
	 * @param st de tip Statistics tip ce contine textul si informatii despre text, frecventa de aparitie a fiecarei litere 
	 * @return arborele huffman de tip arbore 
	 */
	public Arbore arbhmaker(Statistics st){
		//arb[] este un vector in care se retin initial toate frunzele lungimea maxima este 127 cate caractere sunt in codul ASCII
		Arbore arb[] = new Arbore[127];
		// initializez vectorul de arbori arb cu cate o frunza
		for(int i=0;i<st.lungVF;i++){
			arb[i]= new Arbore(null,null,st.frz[i]);
		}
		// n = lungimea vectorului de frunze 
		int n = st.lungVF;
		// cat timp luam n-1 noduri adica n>1 
		while(n > 1){
			int i0 = 0;    
            int i1 = 1;
            if(arb[i0].leaf.value > arb[i1].leaf.value) {
                i0 = 1;
                i1 = 0;
            }

            // cautam primele 2 frunze cu frecventa cea mai mica pentru a le adauga in arbore
            for(int i=2; i<n; i++)
                if(arb[i].leaf.value < arb[i0].leaf.value) {
                    i1 = i0;
                    i0 = i;
                } else if(arb[i].leaf.value < arb[i1].leaf.value)
                    i1 = i;
            
            // calculam frecventa cumulata a primelor 2 frunze
            int value = arb[i0].leaf.value + arb[i1].leaf.value;
            String chr = arb[i0].leaf.chr + arb[i1].leaf.chr;
            Frunza f = new Frunza(value, chr);
            // pe pozitia i0 punem arborele parinte format din copii arb[i0] si arb[i1] si f frunza cu valorile adunate/concatenate
            arb[i0] = new Arbore(arb[i0], arb[i1],f );
            // copiem peste valorile de la arb[i1] pana la final pentru a se pierde un nod ce a fost concatenat anterior 
            for(int i=i1+1; i<n; i++)
                arb[i-1] = arb[i];
            // scade lungimea vectorului de frunze pentru ca de fiecare data inlocuim  2 noduri cu 1 nod rezultat din concatenare 
            n--;
		}
		// arb[i0] este de fapt varful arborelui huffman 
		return arb[0];
	}
	/**
	 * Functie care afiseaza arborele recursiv
	 * @param arb arbore ce va fi afisat 
	 */
	public void ArbPrint(Arbore arb) {
		if(arb != null)
		System.out.println("(" + arb.leaf.chr + "," + arb.leaf.value + ")\n" );
		else
			return ;
		ArbPrint(arb.arbdr);
		ArbPrint(arb.arbst);
	}
	
}