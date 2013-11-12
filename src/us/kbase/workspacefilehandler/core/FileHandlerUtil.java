package us.kbase.workspacefilehandler.core;

import us.kbase.workspacefilehandler.FileType;
import us.kbase.workspacefilehandler.UploadParams;

public class FileHandlerUtil {

	
	public static String toString(FileType ft) {
		StringBuilder sb = new StringBuilder();
		sb.append("filetype: "+ft.getId()+" - "+ft.getName());
		
		return sb.toString();
	}
	
	public static String toString(UploadParams rawParameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("UploadParams=> ");
		sb.append("  filename:\""+rawParameters.getName()+"\";");
		sb.append("  filetype:\""+rawParameters.getType()+"\";");
		sb.append("  uploader:\""+rawParameters.getUploader()+"\";");
		sb.append("  ws_name :\""+rawParameters.getWsName()+"\";");
		if(rawParameters.getContent()!=null)
			sb.append("  content : " +rawParameters.getContent().length()+" characters long;");
		else 
			sb.append("  content : null;");
		if(rawParameters.getMetadata()!=null)
			sb.append("  metadata: " +rawParameters.getMetadata().size() + " key/values defined;");
		else 
			sb.append("  metadata: null;");
			
		return sb.toString();
	}
	
}
