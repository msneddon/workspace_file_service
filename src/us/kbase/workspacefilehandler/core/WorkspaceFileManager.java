package us.kbase.workspacefilehandler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import us.kbase.workspacefilehandler.FileType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class WorkspaceFileManager {

	
	final static private String FILE_TYPE_LIST_LOCATION = "FileTypes.json";
	
	
	
	public WorkspaceFileManager() throws IOException {
		initialize();
	}
	
	

	
	
	
	private Map<String,FileType> fileTypeIndex;
	private Map<String,WorkspaceFileUploader> uploaders;
	private Map<String,WorkspaceFileDownloader> downloaders;
	
	
	protected void initialize() throws IOException {
		// read file type specification file
		String fileTypeListString = loadResourceFile(FILE_TYPE_LIST_LOCATION);
		
		// parse the instance document into a JsonNode
		ObjectMapper mapper = new ObjectMapper();
		final JsonNode fileTypeList = mapper.readTree(fileTypeListString);
		
		//TODO validate the FileTypes.json against the schema to make sure everything is valid
		
		// build our index of FileTypes
		fileTypeIndex = new HashMap<String,FileType>(fileTypeList.size());
		for(int k=0; k<fileTypeList.size(); k++) {
			System.out.println(fileTypeList.get(k));
			final JsonNode fileTypeJson = fileTypeList.get(k);
			FileType f = new FileType();
			
			f.setId(fileTypeJson.get("id").asText());
			f.setName(fileTypeJson.get("name").asText());
			f.setDescription(fileTypeJson.get("description").asText());
			f.setUrl(fileTypeJson.get("url").asText());
			
			//f.setDownloaders();
			
			System.out.println(f);
		}
		
		//populate the loaders
		
		//validate the fileTypeIndex and the Loaders
		
		//} catch (Exception e) {
		//			throw new InstanceValidationException("instance was not a valid or readable JSON document",e);
		//		}
		
		
	}
	
	
	protected void populateLoaders() {
		// create new maps
		
		//iterate over every FileType
		
		// check if it is in the Map- if it is then skip, if it is not, try to create one
		
	}
	
	protected void validateFileTypeIndex() {
		
	}
	
	protected void validateLoaders() {
		
	}
	
	
	/*protected WorkspaceFileUploader instantiateUploader(final String className){
		try{
			return type.cast(Class.forName(className).newInstance());
	    } catch(final InstantiationException e){
	        throw new IllegalStateException(e);
	    } catch(final IllegalAccessException e){
	        throw new IllegalStateException(e);
	    } catch(final ClassNotFoundException e){
	        throw new IllegalStateException(e);
	    }
	}*/
	
	
	
	
	
	private static String loadResourceFile(String resourceName) throws IOException  {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		InputStream is = WorkspaceFileManager.class.getClassLoader().getResourceAsStream(resourceName);
		if (is == null)
			throw new IllegalStateException("Resource not found: " + resourceName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			pw.println(line);
		}
		br.close();
		pw.close();
		return sw.toString();
	}
	
}
