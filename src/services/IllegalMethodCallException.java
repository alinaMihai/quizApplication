/**
 * 
 */
package services;

/**
 * @author user
 *
 */
public class IllegalMethodCallException extends Exception{

	private static final long serialVersionUID = 1L;

	private String message="The method cannot be called for this service";
	
	public IllegalMethodCallException(){
		super();
	}
	
	public String toString(){
		return message;
	}
	
	public String getMessage(){
		return message;
	}
}
