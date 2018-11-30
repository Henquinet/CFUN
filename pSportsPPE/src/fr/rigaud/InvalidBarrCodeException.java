package fr.rigaud;

public class InvalidBarrCodeException extends Exception{

	private String message;
	
	
	public InvalidBarrCodeException(String message) {
		super();
		this.message = message;
	}


	public String toString() {
		final String MSG = "Le code-barre suivant est incorrecte : ";
		return MSG + message;
	}
	
	
}
