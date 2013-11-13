package us.kbase.workspacefilehandler.core;

import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.exceptions.FileDownloadException;

public interface WsFileDownloader {

	
	FileData download(String wsid, InternalDownloadParameters parameters, WorkspaceClient ws) throws FileDownloadException;
	
	
	// List<FileData> downloadBatch(...)
}
