/**
 * Clasa care extinde clasa node si este folosita pentru o variabila int sau boolean
 * @author Merca
 *
 */
public class Variabila extends Node implements Visitable {
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica o litera 
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	Variabila(String s,int poz,int col) {
		super(s,poz,col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
