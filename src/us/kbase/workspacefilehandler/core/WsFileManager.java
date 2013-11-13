package us.kbase.workspacefilehandler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.kbase.workspacefilehandler.FileType;
import us.kbase.workspacefilehandler.exceptions.FileDownloadException;
import us.kbase.workspacefilehandler.exceptions.FileHandlerInitializationException;
import us.kbase.workspacefilehandler.exceptions.FileUploadException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class WsFileManager {

	
	final static private String FILE_TYPE_LIST_LOCATION = "FileTypes.json";
	final static private String DOWNLOADER_PACKAGE = "us.kbase.workspacefilehandler.downloaders.";
	final static private String UPLOADER_PACKAGE = "us.kbase.workspacefilehandler.uploaders.";
	
	final static private boolean VERBOSE = true;
	
	public WsFileManager() throws IOException, FileHandlerInitializationException {
		initialize();
	}
	
	
	private Map<String,FileType> fileTypeIndex;
	private List<FileType> uploadableFileTypes;
	private List<FileType> downloadableFileTypes;
	
	
	private Map<String,WsFileUploader> uploaders;
	private Map<String,WsFileDownloader> downloaders;
	
	
	protected void initialize() throws IOException, FileHandlerInitializationException {
		// read file type specification file
		String fileTypeListString = loadResourceFile(FILE_TYPE_LIST_LOCATION);
		if(VERBOSE) { System.out.println("Loading filetypes from: "+FILE_TYPE_LIST_LOCATION+"\n  ... "); }
		
		// parse the instance document into a JsonNode
		ObjectMapper mapper = new ObjectMapper();
		final JsonNode fileTypeList = mapper.readTree(fileTypeListString);
		
		//TODO validate the FileTypes.json against the schema to make sure everything is valid
		
		
		// build our index of FileTypes
		fileTypeIndex = new HashMap<String,FileType>(fileTypeList.size());
		uploadableFileTypes = new ArrayList<FileType>(fileTypeList.size());
		downloadableFileTypes = new ArrayList<FileType>(fileTypeList.size());
		for(int k=0; k<fileTypeList.size(); k++) {
			final JsonNode fileTypeJson = fileTypeList.get(k);
			FileType f = new FileType();
			
			// set basic info
			f.setId(fileTypeJson.get("id").asText());
			f.setName(fileTypeJson.get("name").asText());
			f.setDescription(fileTypeJson.get("description").asText());
			f.setUrl(fileTypeJson.get("url").asText());
			//set downloaders info
			f.setDefaultDownloader(fileTypeJson.get("default_downloader").asText());
			JsonNode downloaders = fileTypeJson.get("downloaders");
			List <String> downloadersList = new ArrayList<String>(downloaders.size());
			for(int d=0; d<downloaders.size(); d++) {
				downloadersList.add(downloaders.get(d).asText());
			}
			f.setDownloaders(downloadersList);
			//set uploaders info
			f.setDefaultUploader(fileTypeJson.get("default_uploader").asText());
			JsonNode uploaders = fileTypeJson.get("uploaders");
			List <String> uploadersList = new ArrayList<String>(uploaders.size());
			for(int u=0; u<uploaders.size(); u++) {
				uploadersList.add(uploaders.get(u).asText());
			}
			f.setUploaders(uploadersList);
			
			//make sure this filetype is valid
			if(fileTypeIndex.containsKey(f.getId())) {
				throw new FileHandlerInitializationException("two FileTypes have the same ID: "+f.getId()+", service not running.");
			}
			fileTypeIndex.put(f.getId(), f);
			
			if(!f.getUploaders().isEmpty()) {
				uploadableFileTypes.add(f);
			}
			if(!f.getDownloaders().isEmpty()) {
				downloadableFileTypes.add(f);
			}
			
			if(VERBOSE) {
				System.out.println(FileHandlerUtil.toString(f));
			}
		}
		
		//instantiate and populate the loaders
		populateLoaders();
		
		//validate the fileTypeIndex and the Loaders
		validateFileTypeIndex();
		
	}
	
	
	protected void populateLoaders() throws FileHandlerInitializationException {
		// create new maps
		if(VERBOSE) { System.out.println("\nLoading the loaders\n  ... "); }
		uploaders = new HashMap<String,WsFileUploader>();
		downloaders = new HashMap<String,WsFileDownloader>();
		
		//iterate over every FileType and instantiate the uploaders and downloaders
		for (Map.Entry<String, FileType> fileTypeEntry : fileTypeIndex.entrySet()) {
			String fileTypeId = fileTypeEntry.getKey();
			FileType fileType = fileTypeEntry.getValue();
			List <String>downloaders = fileType.getDownloaders();
			for(String name : downloaders) {
				instantiateDownloader(name);
			}
			List <String>uploaders = fileType.getUploaders();
			for(String name : uploaders) {
				instantiateUploader(name);
			}
		}
	}
	
	
	protected void instantiateDownloader(String downloaderName) throws FileHandlerInitializationException {
		if(downloaders.containsKey(downloaderName)) return;
		try {
			downloaders.put(
					downloaderName,
					(WsFileDownloader)getClass().getClassLoader().loadClass(DOWNLOADER_PACKAGE+downloaderName).newInstance());
			if(VERBOSE) { System.out.println("downloader: "+downloaderName); }
		} catch(final Exception e){
			throw new FileHandlerInitializationException("unable to instantiate Downloader '"+downloaderName+"'",e);
		}
	}
	protected void instantiateUploader(String uploaderName) throws FileHandlerInitializationException {
		if(downloaders.containsKey(uploaderName)) return;
		try {
			uploaders.put(
					uploaderName,
					(WsFileUploader)getClass().getClassLoader().loadClass(UPLOADER_PACKAGE+uploaderName).newInstance());
			if(VERBOSE) { System.out.println("uploader: "+uploaderName); }
		} catch(final Exception e){
			throw new FileHandlerInitializationException("unable to instantiate Uploader '"+uploaderName+"'",e);
		}
	}
	
	protected void validateFileTypeIndex() {
		
	}
	
	public FileType getFileType(String fileTypeId) {
		return fileTypeIndex.get(fileTypeId);
	}
	
	public List<FileType> getFileType(List<String> fileTypeId) {
		List<FileType> typeList = new ArrayList<FileType>(fileTypeId.size());
		for(String id : fileTypeId) {
			FileType f = fileTypeIndex.get(id);
			if(f!=null) { typeList.add(f); }
		}
		return typeList;
	}
	
	public List<FileType> getAllFileTypes() {
		return Collections.unmodifiableList(new ArrayList<FileType>(fileTypeIndex.values()));
	}
	
	
	public List<FileType> getAllUploadableFileTypes() {
		return Collections.unmodifiableList(uploadableFileTypes);
	}
	
	public List<FileType> getAllDownloadableFileTypes() {
		return Collections.unmodifiableList(downloadableFileTypes);
	}
	
	
	
	public WsFileUploader getUploader (String loaderId) throws FileUploadException {
		WsFileUploader uploader = uploaders.get(loaderId);
		if(uploader==null) {
			throw new FileUploadException("Uploader "+loaderId+" not found.");
		}
		return uploader;
	}
	
	public WsFileDownloader getDownloader (String loaderId) throws FileDownloadException {
		WsFileDownloader downloader = downloaders.get(loaderId);
		if(downloader==null) {
			throw new FileDownloadException("Downloader "+loaderId+" not found.");
		}
		return downloader;
	}
	
	
	
	
	/**
	 * load a file from file or internally from within a jar/war file\
	 */
	private static String loadResourceFile(String resourceName) throws IOException  {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		InputStream is = WsFileManager.class.getClassLoader().getResourceAsStream(resourceName);
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


	
	public boolean isValidFiletype(String fileTypeId) {
		return fileTypeIndex.containsKey(fileTypeId);
	}


	
	public String getDefaultUploader(String fileTypeId) {
		FileType f = fileTypeIndex.get(fileTypeId);
		if(f!=null) { return f.getDefaultUploader(); }
		return "";
	}

	public boolean isValidUploader(String uploader) {
		return uploaders.containsKey(uploader);
	}


	
	public String getDefaultDownloader(String fileTypeId) {
		FileType f = fileTypeIndex.get(fileTypeId);
		if(f!=null) { return f.getDefaultDownloader(); }
		return "";
	}
	
	public boolean isValidDownloader(String downloader) {
		return downloaders.containsKey(downloader);
	}
	
}
