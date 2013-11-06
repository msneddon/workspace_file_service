package us.kbase.workspacefilehandler;

public class FileTypeUtil {

	
	public static String toString(FileType ft) {
		StringBuilder sb = new StringBuilder();
		sb.append("filetype: "+ft.getId()+" - "+ft.getName());
		
		return sb.toString();
	}
	
}
