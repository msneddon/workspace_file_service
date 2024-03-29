package us.kbase.workspacefilehandler.exceptions;

public class FileUploadException extends Exception {
	private static final long serialVersionUID = 3169019078907064824L;

	public FileUploadException(String message) {
		super(message);
	}

	public FileUploadException(String message, Throwable e) {
		super(message,e);
	}

	public FileUploadException(Throwable e) {
		super(e.getMessage() == null ? "Unknown error" : e.getMessage(), e);
	}
}
