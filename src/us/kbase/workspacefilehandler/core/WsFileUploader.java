package us.kbase.workspacefilehandler.core;

import java.util.List;

import us.kbase.workspace.*;
import us.kbase.workspacefilehandler.exceptions.FileUploadException;

public interface WsFileUploader {
	
	public WsUploadResult upload(FileData file, InternalUploadParameters parameters, WorkspaceClient ws) throws FileUploadException;
	
	//public List<ObjectIdentity> upload_batch(List<FileData>content, List<UploadParameters> parameters);
	
}
