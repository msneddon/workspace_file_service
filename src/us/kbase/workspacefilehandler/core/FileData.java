package us.kbase.workspacefilehandler.core;

public class FileData {

	protected String filename;
	protected String content;
	
	public FileData(String filename, String content) {
		this.filename = filename;
		this.content = content;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getContent() {
		return content;
	}
}
