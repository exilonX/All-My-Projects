/**
 * interfata ce ajuta la implementare design patternului visitor 
 * fiecare clasa ce mosteneste Node implementeaza visitable
 * @author Merca
 *
 */
public interface Visitable {
	/**
	 * functia care accepta vizitatorul
	 * @param v vizitator
	 */
	public void accept(Visitor v);
}