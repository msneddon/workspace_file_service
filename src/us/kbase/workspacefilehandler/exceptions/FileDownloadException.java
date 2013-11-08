package us.kbase.workspacefilehandler.exceptions;

public class FileDownloadException extends Exception {
	private static final long serialVersionUID = 3169019078907064824L;

	public FileDownloadException(String message) {
		super(message);
	}

	public FileDownloadException(String message, Throwable e) {
		super(message,e);
	}

	public FileDownloadException(Throwable e) {
		super(e.getMessage() == null ? "Unknown error" : e.getMessage(), e);
	}
}
