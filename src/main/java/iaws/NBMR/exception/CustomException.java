package iaws.NBMR.exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = -285435751281747613L;

	private int code;
	private String message;
	
	public CustomException(int code, String message){
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
