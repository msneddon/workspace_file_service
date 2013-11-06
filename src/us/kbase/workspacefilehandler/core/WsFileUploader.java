package us.kbase.workspacefilehandler.core;

import us.kbase.workspace.*;

public interface WsFileUploader {
	
	
	public ObjectIdentity upload(String content, String filename);
	//public List<ObjectIdentity> upload_batch(List<String>content,List<String>)
	
	
}
