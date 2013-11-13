package us.kbase.workspacefilehandler.core;

import us.kbase.auth.AuthToken;
import us.kbase.workspacefilehandler.FileType;

public class InternalDownloadParameters {
	
	private AuthToken authToken;
	private FileType fileType;
	
	public InternalDownloadParameters(AuthToken authToken, FileType fileType) {
		this.authToken = authToken;
		this.fileType = fileType;
	}
	
	public AuthToken getAuthToken() {
		return authToken;
	}
	
	public FileType getFileType() {
		return fileType;
	}
	
}
