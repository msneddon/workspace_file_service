package us.kbase.workspacefilehandler.core;

import java.util.Map;

import us.kbase.common.service.Tuple10;

public class WsObjectInfoFull {

	
	private Long objid;
	private String name;
	private String type;
	private String save_date;
	private Long version;
	private String created_by;
	private Long wsid;
	private String chsum;
	private Long size;
	private Map<String, String> meta;
	
	
	public WsObjectInfoFull(Tuple10<Long, String, String, String, Long, String, Long, String, Long, Map<String, String>> object_info) {
		objid      = object_info.getE1();
		name       = object_info.getE2();
		type       = object_info.getE3();
		save_date  = object_info.getE4();
		version    = object_info.getE5();
		created_by = object_info.getE6();
		wsid       = object_info.getE7();
		chsum      = object_info.getE8();
		size       = object_info.getE9();
		meta       = object_info.getE10();
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
