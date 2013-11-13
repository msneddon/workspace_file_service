package us.kbase.workspacefilehandler.downloaders;

import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.InternalDownloadParameters;
import us.kbase.workspacefilehandler.core.WsFileDownloader;
import us.kbase.workspacefilehandler.exceptions.FileDownloadException;

public class SimpleFastaFileDownloader implements WsFileDownloader{

	
	@Override
	public FileData download(String wsid,
			InternalDownloadParameters parameters, WorkspaceClient ws)
			throws FileDownloadException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
