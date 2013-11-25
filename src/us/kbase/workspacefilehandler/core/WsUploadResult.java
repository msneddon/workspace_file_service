package us.kbase.workspacefilehandler.core;

import us.kbase.common.service.Tuple10;
import us.kbase.common.service.Tuple9;

public class WsUploadResult {

	protected String absWsObjReference;
	
	protected Long obj_id;
	protected String obj_name;
	protected String typedef_name;
	protected String save_date;
	protected Long version;
	protected String created_by;
	protected Long ws_id;
	protected String workspace_name;
	protected String chsum;
	protected Long size;
	
	
	public WsUploadResult(Tuple10<Long, String, String, String, Long, String, Long, String, String, Long> tuple10) {
		/* 
		tuple<
			1: obj_id objid,
			2: obj_name name,
			3: type_string type,
			4: timestamp save_date,
			5: int version,
			6: username created_by,
			7: ws_id wsid,
			8: ws_name workspace
			8: string chsum,
			9: int size>
				object_info;
			*/
		obj_id         = tuple10.getE1();
		obj_name       = tuple10.getE2();
		typedef_name   = tuple10.getE3();
		save_date      = tuple10.getE4();
		version        = tuple10.getE5();
		created_by     = tuple10.getE6();
		ws_id          = tuple10.getE7();
		workspace_name = tuple10.getE8();
		chsum          = tuple10.getE9();
		size           = tuple10.getE10();
		
		absWsObjReference = "kb|ws."+tuple10.getE7()+".obj."+tuple10.getE1()+".ver."+tuple10.getE5();
	}
	
	public String getAbsWsObjReference() {
		return absWsObjReference;
	}
	
	public String getTypeDefName() {
		return typedef_name;
	}
	
}
