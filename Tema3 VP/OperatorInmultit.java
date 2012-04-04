/**
 * Clasa care extinde clasa node si este folosita pentru operatorul inmultit * 
 * @author Merca
 *
 */
public class OperatorInmultit extends Node implements Visitable{
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica "*"
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	OperatorInmultit(String s,int poz,int col) {
		super(s,poz,col);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
