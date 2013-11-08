package us.kbase.workspacefilehandler.core;

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
	protected String chsum;
	protected Long size;
	
	
	public WsUploadResult(Tuple9<Long, String, String, String, Long, String, Long, String, Long> result) {
		/* 
		tuple<
			1: obj_id objid,
			2: obj_name name,
			3: type_string type,
			4: timestamp save_date,
			5: int version,
			6: username created_by,
			7: ws_id wsid,
			8: string chsum,
			9: int size>
				object_info;
			*/
		obj_id        = result.getE1();
		obj_name      = result.getE2();
		typedef_name  = result.getE3();
		save_date     = result.getE4();
		version       = result.getE5();
		created_by    = result.getE6();
		ws_id         = result.getE7();
		chsum         = result.getE8();
		size          = result.getE9();
		
		absWsObjReference = "kb|ws."+result.getE7()+".obj."+result.getE1()+".ver."+result.getE5();
	}
	
	public String getAbsWsObjReference() {
		return absWsObjReference;
	}
	
	public String getTypeDefName() {
		return typedef_name;
	}
	
}
