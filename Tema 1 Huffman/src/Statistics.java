public class Statistics  {
	public String text;
	public Frunza[] frz = new Frunza[127];
	//  text textul de codificat
	public int lungVF = 0;
	public Statistics(){
		text = new String();
	}
	public Statistics(String read){
		text = new String(read);  
	}
	
	// metoda ce gaseste nr de aparitii ale tuturor caracterelor in text si returneaza
	// un vector de frunze cu caracterul si 
	public void FrunzaGen() {
		int l = text.length();
		int nrap = 0;
		int nrf =0;
		int i;
		//parcurgem tot textul String
		while(text.length() > 0){
				i=0;
			for(int j=0;j<l;j++){
				// cautam 2 elemente egale si le numaram 
				if(text.charAt(0) == text.charAt(j)) {
					nrap++;// nr de aparitii retine frecventa pentru fiecare caracter 
				}
				
			}
			// formam un vector de tip Frunza in care pe pozitia nrf(nr de frunze) se copiaza frecventa si caracterul 
			frz[nrf] = new Frunza();
			frz[nrf].chr = Character.toString(text.charAt(0)) ;
			frz[nrf].value = nrap; 
			
			nrf++;// marim nr de frunze dupa ce am adaugat in vector 
			// stergem toate apatiile caracterului cautat text.charAt(i)
			if(text.charAt(i) == '.'){
				String cod = new String("\\.");
				text = text.replaceAll(cod,"");
			}
			else
			text = text.replaceAll(Character.toString( text.charAt(0)),"");
			// reinnoim lungimea totala a textului
			l = text.length();
			nrap=0;
		}
		lungVF = nrf;
	}
	public void afis(){
		for(int i=0;i<lungVF; i++){
			System.out.println(frz[i].chr + "-->" + frz[i].value);
		}
	}
	
}
