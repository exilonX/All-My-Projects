/**
 * Clasa care extinde clasa node si este folosita pentru operatorul egal 
 * @author Merca
 *
 */
public class OperatorEgal extends Node implements Visitable {
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica "="
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	OperatorEgal(String s,int poz,int col) {
		super(s, poz, col);
		
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
