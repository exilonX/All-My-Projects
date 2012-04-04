/**
 * clasa ce ajuta la implementarea design patternului visitor iar orice vizitator implementeaza aceasta 
 * interfata 
 * @author Merca
 *
 */
public interface Visitor {
	/**
	 * metoda in care se vor efectua prelucrari asupra elementului vizitat din arbore
	 * @param o operatorul / variabila/ valoarea 
	 */
	public void visit(OperatorPlus o);
	/**
	 * metoda in care se vor efectua prelucrari asupra elementului vizitat din arbore
	 * @param o operatorul / variabila/ valoarea 
	 */
	public void visit(OperatorEgal o);
	/**
	 * metoda in care se vor efectua prelucrari asupra elementului vizitat din arbore
	 * @param o operatorul / variabila/ valoarea 
	 */
	public void visit(OperatorInmultit o);
	/**
	 * metoda in care se vor efectua prelucrari asupra elementului vizitat din arbore
	 * @param o operatorul / variabila/ valoarea 
	 */
	public void visit(Valoare o);
	/**
	 * metoda in care se vor efectua prelucrari asupra elementului vizitat din arbore
	 * @param o operatorul / variabila/ valoarea 
	 */
	public void visit(Variabila o);
	
}
