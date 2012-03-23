
import java.util.Scanner;


public class Main {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
	if(args[0].charAt(0) == 'c') {
			Scanner s = new Scanner(System.in).useDelimiter("\\n\\.");
			// citim textul care va fi codificat
			String sc = new String(s.next());
			
			
			sc= sc.substring(0,sc.length()-1);
			Statistics stat = new Statistics(sc);
			// generam frunze adica calculam un vector de frecvente pentru fiecare caracter 
			stat.FrunzaGen();
			
			Codificare codific = new Codificare();
			String[] codificat = new String[127];
			// codificat contine caracterul si codul corespunzator
			codificat = codific.Code(stat);
			// afiseaza codul si caracterul ascii
			codific.PrintCod(codificat, stat);
			
			String textcod = new String();
			// formam o matrice ce va contine pe prima coloana caracterul si pe a doua coloana codul acelui caracter
			String[][] cv = new String[stat.lungVF][2];
			for(int i=0;i<stat.lungVF;i++){
				cv[i][0]= new String();
				cv[i][1]= new String();
				
				cv[i][0]= stat.frz[i].chr;
				cv[i][1]= codificat[i];
			}
			System.out.println(".");
			// codificam textul 
		textcod = codific.Codificat(sc,cv,stat.lungVF);
		System.out.println(textcod);
		
		}
			if(args[0].charAt(0)=='d'){
				// Decodificarea 
				Scanner s = new Scanner(System.in);
				String str = new String();
				//	Scanner s1= new Scanner(System.in).useDelimiter("\\n");
				String decod = new String();
				//System.out.println("s2   \n" + decod + "\nceva");
				String rez1= new String();
				String rez2 = new String();
				String[][] dedecod = new String[127][2];
				int i=0;
				str= s.nextLine();
				int index =0;
				//cat timp nu citim . citim o linie si o prelucram adica luam codul ascii pana la spatiu si dupa aceea pana la final codul caracterului
				while(str.equals(".")!= true){
					index = str.indexOf(" ");
					rez1 = str.substring(0,index);
					rez2 = str.substring(index+1);
					dedecod[i][0]= new String();
					dedecod[i][1]= new String();
					dedecod[i][0] = Character.toString((char)Integer.parseInt(rez1));
					dedecod[i][1]= rez2;
					i++;
					str = s.nextLine();
			
				}
		
				decod = s.nextLine(); 
				Decodificare d = new Decodificare();
				//dede este stirul decodificat 
				String dede = d.Decode(dedecod, decod,i);
				System.out.println(dede);
				System.out.print(".");
			} 
		}
}