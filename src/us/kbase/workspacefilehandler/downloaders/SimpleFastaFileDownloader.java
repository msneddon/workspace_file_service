package us.kbase.workspacefilehandler.downloaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import us.kbase.common.service.Tuple10;
import us.kbase.common.service.Tuple11;
import us.kbase.file.FastaFile;
import us.kbase.file.FastaRow;
import us.kbase.file.TextFile;
import us.kbase.workspace.ObjectData;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.InternalDownloadParameters;
import us.kbase.workspacefilehandler.core.WsFileDownloader;
import us.kbase.workspacefilehandler.core.WsObjectInfoFull;
import us.kbase.workspacefilehandler.exceptions.FileDownloadException;

public class SimpleFastaFileDownloader implements WsFileDownloader{

	
	@Override
	public FileData download(String objectReference,
			InternalDownloadParameters parameters, WorkspaceClient ws)
			throws FileDownloadException {
		
		// STEP 1: construct the call object needed to fetch the data
		List <ObjectIdentity> objectIds = new ArrayList<ObjectIdentity>(1);
		objectIds.add(new ObjectIdentity().withRef(objectReference));
				
		// STEP 2: get info about the objects and make sure they are the expected type.  We also need to remember the absolute object reference
		String absObjReference = "";
		try {
			List<Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>>> objectInfoList = ws.getObjectInfo(objectIds, 1L);
			// should only ever be one result if the object exists because this is not a batch download
			WsObjectInfoFull wsoif = new WsObjectInfoFull(objectInfoList.get(0));
			// make sure the type is valid
			if(!wsoif.getTypeName().equals("File.FastaFile")) {
				throw new FileDownloadException("Object "+objectReference+" cannot be downloaded as a Fasta file; it is of type "+wsoif.getTypeName()+", but must be of type File.FastaFile");
			}
			absObjReference = wsoif.getAbsObjRef();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileDownloadException("Error encountered when downloading '"+objectReference+"' as a Fasta file:"+e.getMessage(),e);
		}
			
		// STEP 3: actually download the objects and package the output file.  This is where any exporting or other operations would be handled.
		FileData downloadedFileData = null;
		try {
			List<ObjectData> objDataList = ws.getObjects(objectIds);
			ObjectData objData = objDataList.get(0);
				
			FastaFile fastaFile = objData.getData().asClassInstance(FastaFile.class);
			
			StringBuilder fastaBuilder = new StringBuilder();
			List<FastaRow> rows = fastaFile.getContent();
			for(FastaRow r: rows) {
				fastaBuilder.append(">"+r.getHeader()+"\n");
				fastaBuilder.append(r.getSequence()+"\n");
			}
				
			downloadedFileData  = new FileData(fastaFile.getFilename(),fastaBuilder.toString());
			downloadedFileData.setSrcReference(absObjReference);
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileDownloadException("Error encountered when downloading '"+objectReference+"' as a Fasta file:"+e.getMessage(),e);
		}
		return downloadedFileData;
	}
	
	
}
