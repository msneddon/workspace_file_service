package us.kbase.workspacefilehandler.core;

public class FileData {

	protected String filename;
	protected String content;
	protected String srcRef;
	
	public FileData(String filename, String content) {
		this.filename = filename;
		this.content = content;
		this.srcRef = "";
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setSrcReference(String srcRef) {
		this.srcRef = srcRef;
	}
	public String getSrcReference() {
		return srcRef;
	}
}
