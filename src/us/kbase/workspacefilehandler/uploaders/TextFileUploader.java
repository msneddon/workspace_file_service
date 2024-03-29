package us.kbase.workspacefilehandler.uploaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import us.kbase.common.service.Tuple10;
import us.kbase.common.service.Tuple11;
import us.kbase.common.service.Tuple9;
import us.kbase.common.service.UObject;
import us.kbase.file.TextFile;
import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.InternalUploadParameters;
import us.kbase.workspacefilehandler.core.WsFileUploader;
import us.kbase.workspacefilehandler.core.WsUploadResult;
import us.kbase.workspacefilehandler.exceptions.FileUploadException;
import us.kbase.workspace.*;


public class TextFileUploader  implements WsFileUploader{

	@Override
	public WsUploadResult upload(FileData file, InternalUploadParameters parameters, WorkspaceClient ws) throws FileUploadException {
		
		// Step 1: from the parameters, create the TextFile object.  This should be defined in 'File.spec', and is
		// automatically generated by the java type compiler.  Available types are in the package "us.kbase.file"
		//   note: this step may (and probably should) include basic validation or type checking of the content
		//         and could for instance call an external tool for parsing
		TextFile tf = new TextFile();
		tf.setFilename(file.getFilename());
		tf.setContent(file.getContent());
		
		// Step 2: create the ObjectSaveData object, which wraps the TextFile object as an UnspecifiedObject (a UObject)
		ObjectSaveData osd = new ObjectSaveData();
		osd.setData(new UObject(tf));                   // set the actual typed object data
		osd.setName(parameters.getDesiredObjectName()); // set the name for the workspace object
		osd.setType("File.TextFile");                   // set the name of the typed object that is used by the
		                                              // workspace for validation
		
		// Step 3: add any meta data to this object.  The metadata in parameters is used to pass you
		// additional parameters that may have been used to define how the file is parsed.  Here we just pass this
		// information directly to the workspace object
		osd.setMeta(parameters.getMetaData());
		
		
		// Step 4: you should ALWAYS set provenance information!
		ProvenanceAction pa = new ProvenanceAction();
		pa.setService("WorkspaceFileHandler");
		pa.setServiceVer("v0.1");
		pa.setMethod("upload");
		pa.setDescription("text file upload");
		List <ProvenanceAction> pa_list = new ArrayList<ProvenanceAction> (1);
		pa_list.add(pa);
		osd.setProvenance(pa_list);
		
		//Step 5: construct the list of objects to save and the parameter to the workspace save method
		List <ObjectSaveData> data = new ArrayList<ObjectSaveData>();
		data.add(osd);
		SaveObjectsParams p = new SaveObjectsParams();
		p.setObjects(data);
		p.setWorkspace(parameters.getWsName());
		
		
		//Step 6: actually save the object
		WsUploadResult uploadResult = null;
		try {
			List<Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>>> result_list = ws.saveObjects(p);
			uploadResult = new WsUploadResult(result_list.get(0));
		} catch (Exception e) {
			// If we encountered an error during saving, we should catch it and throw it back up as a FileUploadException
			throw new FileUploadException("Error encountered when attempting to save a text file to the Workspace: "+ e.getMessage(),e);
		}
		
		return uploadResult;
	}
	
	
	
	
}
