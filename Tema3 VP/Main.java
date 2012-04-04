import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * clasa Main
 * @author Merca
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String in = args[0];
		Collection<String> text = new ArrayList<String>();
		// se citeste din fisier linie cu linie
		String aux;
        try {
			BufferedReader br = new BufferedReader(new FileReader(in));
			while((aux=br.readLine())!=null){
				text.add(aux);
			}
			
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        // se face parse tree ul si se afiseaza in fisierul corespunzator 
        Node n = new Node("",0,0); 
        n.makeTree(text);
        PrintWriter out = null;
        try {
            out = new PrintWriter(in+ "_pt");
            n.display(n,"",out);
        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if (out != null) {
                out.close();
            }
        }
        
        
        // se apeleaza visitorul semantic si se afiseaza in fisier numai erorile 
        PrintWriter out1 = null;
        try {

            out1 = new PrintWriter(in+ "_sv");
            SemanticVisitor v = new SemanticVisitor(out1);

            
            Iterator<Node> it = n.children.iterator();
            while(it.hasNext()){
            	
            	it.next().accept(v);
            }
           
            // daca fisierul nu contine erori se apeleaza si result visitor 
            if(v.error== false){
                
            	PrintWriter out2 = null;
                
                try {
                	
                    out2 = new PrintWriter(in + "_rv");
                    
                    ResultVisitor v1 = new ResultVisitor();
                    Iterator<Node> it1 = n.children.iterator();
                    while(it1.hasNext()){
                    	
                    	it1.next().accept(v1);
                    }
                    for(Map.Entry<String,String> entry : v1.value.entrySet()) {
                    	  String key = entry.getKey();
                    	  String value = entry.getValue();

                    	 out2.println(key + " = " + value);
                    	}

                } catch(IOException e) {
                    e.printStackTrace();

                } finally {
                    if (out2 != null) {
                        out2.close();
                    }
                }
                }
            
        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if (out1 != null) {
                out1.close();
            }
        }
        


	}

}
