import java.util.*;

/**
 * clasa arbore care contine o colectie de noduri de tip ArboreSGF si file care reprezinta continutul 
 * @author Merca
 *
 */
public class ArboreSGF {
	
	Collection<ArboreSGF> noduri = new ArrayList<ArboreSGF>();
	Orice file;
	// constructor folosit pentru a initializa root
	ArboreSGF(){
		User usr = new User("root", null);
		file = new Folder("root", usr, null, "director");
	}

}
