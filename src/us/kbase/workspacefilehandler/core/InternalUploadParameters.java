package us.kbase.workspacefilehandler.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import us.kbase.auth.AuthToken;

public class InternalUploadParameters {

	
	private AuthToken authToken;
	private String desiredObjectName;
	private String wsName;
	private Map <String,String> metadata;
	
	public InternalUploadParameters(AuthToken authToken, String wsName) {
		this(authToken,wsName,null);
	}
	
	public InternalUploadParameters(AuthToken authToken, String wsName, String desiredObjectName) {
		this(authToken,wsName,desiredObjectName,null);
	}
	public InternalUploadParameters(AuthToken authToken, String wsName, String desiredObjectName, Map<String,String> metadata) {
		this.authToken = authToken;
		this.wsName = wsName;
		this.desiredObjectName = desiredObjectName;
		if(metadata==null) {
			this.metadata = new HashMap<String,String>();
		} else {
			this.metadata = metadata;
		}
	}
	
	public AuthToken getAuthToken() {
		return authToken;
	}
	
	public boolean isDesiredObjectNameSpecified() {
		if(desiredObjectName != null) { return true; }
		return false;
	}
	
	public String getDesiredObjectName() {
		if(desiredObjectName == null) { return ""; }
		return desiredObjectName;
	}
	
	public String getWsName() {
		return wsName;
	}
	
	public Map <String,String> getMetaData() {
		return Collections.unmodifiableMap(metadata);
	}
	
	public String getMetaDataByKey(String key) {
		return metadata.get(key);
	}
	
}
