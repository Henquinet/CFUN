package fr.rigaud;

public class InvalidBarrCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3531372674322516252L;
	private String message;
	
	
	public InvalidBarrCodeException(String message) {
		super();
		this.message = message;
	}


	public String toString() {
		final String MSG = "Le code-barre suivant est incorrecte : ";
		return MSG + message;
	}
	public String getMessage() {
		final String MSG = "Le code-barre suivant est incorrecte : ";
		return MSG + message;
	}
	
}