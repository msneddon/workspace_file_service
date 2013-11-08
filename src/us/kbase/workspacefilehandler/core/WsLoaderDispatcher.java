package us.kbase.workspacefilehandler.core;

import java.io.IOException;
import java.net.URL;

import us.kbase.auth.AuthToken;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.UploadParams;
import us.kbase.workspacefilehandler.exceptions.FileUploadException;

public class WsLoaderDispatcher {

	
	protected WsFileManager wfm;
	protected URL workspaceUrl;
	
	
	/**
	 * 
	 */
	public WsLoaderDispatcher(WsFileManager wfm, URL workspaceUrl) {
		this.wfm = wfm;
		this.workspaceUrl = workspaceUrl;
	}
	
	
	
	
	/**
	 * UploadParams rawParameters is the raw parameters we receive via JSON RPC.  We convert that
	 * to 
	 * @return
	 * @throws FileUploadException 
	 */
	public WsUploadResult upload(UploadParams rawParameters, AuthToken authToken) throws FileUploadException {
		
		//validate the input
		validateRawUploadParameters(rawParameters);
		
		// Instantiate the file data
		FileData file = new FileData(rawParameters.getName(), rawParameters.getContent());
		
		// fetch the proper uploader
		WsFileUploader uploader = wfm.getUploader(rawParameters.getUploader());
		
		// configure the upload parameters for the uploader
		InternalUploadParameters parameters = new InternalUploadParameters(authToken, rawParameters.getWsName(), rawParameters.getName(), rawParameters.getMetadata());
		
		// do the upload and return the WS handle
		WsUploadResult uploadResult = null;
		try {
			WorkspaceClient ws = new WorkspaceClient(workspaceUrl,authToken);
			ws.setAuthAllowedForHttp(true);
			uploadResult = uploader.upload(file, parameters,ws);
		} catch (RuntimeException e) {
			throw new FileUploadException("unexpected internal error encountered during upload of '"+rawParameters.getName()+"'",e);
		} catch (UnauthorizedException e) {
			throw new FileUploadException("user is not authorized to access the workspace, at '"+workspaceUrl.toExternalForm()+"'",e);
		} catch (IOException e) {
			throw new FileUploadException("unable to connect to the workspace, at '"+workspaceUrl.toExternalForm()+"'",e);
		}
		return uploadResult;
	}
	
	
	
	protected void validateRawUploadParameters(UploadParams rawParameters) throws FileUploadException {
		// validate filename exists and is not empty
		if(rawParameters.getName()==null) { throw new FileUploadException("cannot upload file, filename was not set."); }
		if(rawParameters.getName().equals("")) { throw new FileUploadException("cannot upload file, filename was empty string."); }
		
		// validate that something is in our file
		if(rawParameters.getContent()==null) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', file content was not set."); }
		
		// validate that the filetype is set and is valid
		if(rawParameters.getType()==null) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', file type was not set."); }
		if(rawParameters.getType().equals("")) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', file type was empty string."); }
		if(!wfm.isValidFiletype(rawParameters.getType())) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', file type '"+rawParameters.getType()+"' is not a supported file type."); }

		// validate that we have an uploader for this filetype
		if(rawParameters.getUploader()==null) {
			// if uploader is null, we set the default uploader for this filetype
			rawParameters.setUploader(wfm.getDefaultUploader(rawParameters.getType()));
		} else if(rawParameters.getUploader().equals("")) {
			throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', uploader name was empty string.");
		}
		if(!wfm.isValidUploader(rawParameters.getUploader())) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', file type '"+rawParameters.getUploader()+"' is not a supported uploader."); }

		// validate that the workspace name is set
		if(rawParameters.getWsName()==null) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', target workspace name was not set."); }
		if(rawParameters.getWsName().equals("")) { throw new FileUploadException("cannot upload file '"+rawParameters.getName()+"', target workspace name was empty string."); }
		
		
	}
	
	
	
}
