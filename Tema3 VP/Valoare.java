/**
 * Clasa care extinde clasa node si este folosita a implementa o valoare int sau boolean
 * @author Merca
 *
 */
public class Valoare extends Node implements Visitable{
	/**
	 * Constructor 
	 * @param s operatorul efectiv adica un numar sau true/false
	 * @param poz este linia in fisier
	 * @param col coloana in fisier
	 */
	Valoare(String s,int poz,int col) {
		super(s,poz,col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
