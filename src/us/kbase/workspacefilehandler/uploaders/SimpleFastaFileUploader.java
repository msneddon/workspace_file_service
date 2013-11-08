package us.kbase.workspacefilehandler.uploaders;

import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.InternalUploadParameters;
import us.kbase.workspacefilehandler.core.WsFileUploader;
import us.kbase.workspacefilehandler.core.WsUploadResult;

public class SimpleFastaFileUploader implements WsFileUploader {

	
	public SimpleFastaFileUploader() {}

	@Override
	public WsUploadResult upload(FileData file, InternalUploadParameters parameters, WorkspaceClient ws) {
		System.out.println("attempting to load simple fasta file "+file.getFilename());
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
