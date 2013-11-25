package us.kbase.workspacefilehandler.core;

import java.util.Map;

import us.kbase.common.service.Tuple10;
import us.kbase.common.service.Tuple11;

public class WsObjectInfoFull {

	
	private Long objid;
	private String name;
	private String type;
	private String save_date;
	private Long version;
	private String created_by;
	private Long wsid;
	private String workspace_name;
	private String chsum;
	private Long size;
	private Map<String, String> meta;
	
	
	public WsObjectInfoFull(Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>> tuple11) {
		objid      = tuple11.getE1();
		name       = tuple11.getE2();
		type       = tuple11.getE3();
		save_date  = tuple11.getE4();
		version    = tuple11.getE5();
		created_by = tuple11.getE6();
		wsid       = tuple11.getE7();
		workspace_name = tuple11.getE8();
		chsum      = tuple11.getE9();
		size       = tuple11.getE10();
		meta       = tuple11.getE11();
	}
	
	public String getTypeName() {
		return type.split("-")[0];
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("       objid:  "+objid+"\n");
		result.append("        name:  "+name+"\n");
		result.append("        type:  "+type+"\n");
		result.append("   save_date:  "+save_date+"\n");
		result.append("     version:  "+version+"\n");
		result.append("  created_by:  "+created_by+"\n");
		result.append("        wsid:  "+wsid+"\n");
		result.append("       chsum:  "+chsum+"\n");
		result.append("        size:  "+size+"\n");
		result.append("        meta:  "+meta+"\n");
		return result.toString();
	}

	public String getAbsObjRef() {
		return "kb|ws."+wsid+".obj."+objid+".ver."+version;
	}
	
	
}
