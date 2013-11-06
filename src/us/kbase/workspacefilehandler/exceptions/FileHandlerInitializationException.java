package us.kbase.workspacefilehandler.exceptions;

public class FileHandlerInitializationException extends Exception {

	private static final long serialVersionUID = 3169019078907064824L;

	public FileHandlerInitializationException(String message) {
		super(message);
	}

	public FileHandlerInitializationException(String message, Throwable e) {
		super(message,e);
	}

	public FileHandlerInitializationException(Throwable e) {
		super(e.getMessage() == null ? "Unknown error" : e.getMessage(), e);
	}
}
