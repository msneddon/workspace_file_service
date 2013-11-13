package us.kbase.workspacefilehandler.uploaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import us.kbase.common.service.Tuple9;
import us.kbase.common.service.UObject;
import us.kbase.file.FastaFile;
import us.kbase.file.FastaRow;
import us.kbase.file.TextFile;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.ProvenanceAction;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.InternalUploadParameters;
import us.kbase.workspacefilehandler.core.WsFileUploader;
import us.kbase.workspacefilehandler.core.WsUploadResult;
import us.kbase.workspacefilehandler.exceptions.FileUploadException;

public class SimpleFastaFileUploader implements WsFileUploader {


	@Override
	public WsUploadResult upload(FileData file, InternalUploadParameters parameters, WorkspaceClient ws) throws FileUploadException {

		// Step 1: from the parameters, create the FastaFile object.  This should be defined in 'File.spec', and is
		// automatically generated by the java type compiler.  Available types are in the package "us.kbase.file"
		FastaFile ff = new FastaFile();
		ff.setFilename(file.getFilename());
		
		List<FastaRow> rows = new ArrayList<FastaRow>();
		BufferedReader bufReader = new BufferedReader(new StringReader(file.getContent()));

		String line=null;
		FastaRow currentRow = null; StringBuilder currentSequence = new StringBuilder();
		try {
			while( (line=bufReader.readLine()) != null ) {
				line = line.trim();
				if(line.startsWith(">")) {
					if(currentRow!=null) {
						currentRow.setSequence(currentSequence.toString());
						currentSequence = new StringBuilder();
					}
					currentRow = new FastaRow();
					currentRow.setHeader(line.substring(1).trim());
					rows.add(currentRow);
				} else {
					currentSequence.append(line);
				}
			}
			currentRow.setSequence(currentSequence.toString());
		} catch (IOException e) {
			throw new FileUploadException("Error encountered when parsing FastaFile: "+ e.getMessage(), e);
		}
		
		ff.setContent(rows);
		
		for(FastaRow r:rows) {
			System.out.println(">"+r.getHeader());
			System.out.println(r.getSequence());
		}
		
		// Step 2: create the ObjectSaveData object, which wraps the TextFile object as an UnspecifiedObject (a UObject)
		ObjectSaveData osd = new ObjectSaveData();
		osd.setData(new UObject(ff));                   // set the actual typed object data
		osd.setName(parameters.getDesiredObjectName()); // set the name for the workspace object
		osd.setType("File.FastaFile");                   // set the name of the typed object that is used by the
		                                              // workspace for validation
		
		
		System.out.println(new UObject(ff).asJsonNode());
		
		// Step 3: add any meta data to this object.  The metadata in parameters is used to pass you
		// additional parameters that may have been used to define how the file is parsed.  Here we just pass this
		// information directly to the workspace object
		osd.setMeta(parameters.getMetaData());
		
		
		// Step 4: you should ALWAYS set provenance information!
		ProvenanceAction pa = new ProvenanceAction();
		pa.setService("WorkspaceFileHandler");
		pa.setServiceVer("v0.1");
		pa.setMethod("upload");
		pa.setDescription("simple fasta file upload");
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
			List<Tuple9<Long,String,String,String,Long,String,Long,String,Long>> result_list = ws.saveObjects(p);
			uploadResult = new WsUploadResult(result_list.get(0));
		} catch (Exception e) {
			// If we encountered an error during saving, we should catch it and throw it back up as a FileUploadException
			throw new FileUploadException("Error encountered when attempting to save a text file to the Workspace: "+ e.getMessage(),e);
		}
		
		return uploadResult;
	}
	
	
}
