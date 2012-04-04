/**
 * Clasa care extinde clasa node si este folosita pentru operatorul plus
 * @author Merca
 *
 */
public class OperatorPlus extends Node implements Visitable {
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica "+"
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	OperatorPlus(String s,int poz,int col) {
		super(s,poz,col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
}
