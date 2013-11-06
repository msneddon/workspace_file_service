package us.kbase.workspacefilehandler.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import us.kbase.auth.*;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.Tuple6;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.file.TextFile;
import us.kbase.workspace.*;
import us.kbase.workspacefilehandler.core.WsFileManager;


public class TestFileManager {

	private static String wsURL = "http://140.221.84.170:7058";
	private static String fileSpec = "File.spec";
	
	private static WorkspaceClient ws;
	
	@BeforeClass
	public static void initWsClient() throws Exception {
		System.out.println("instantiating ws client.");
		String user     = "----";
		String password = "----";
		ws = new WorkspaceClient(new URL(wsURL), user, password);
		ws.setAuthAllowedForHttp(true);
		System.out.println("ws client ready.");
		initWs();
		//registerSpecFile();
	}
	
	private static void initWs() throws Exception {
		String wsname = "TestWS1";
		WorkspaceIdentity wsi = new WorkspaceIdentity();
		wsi.setWorkspace(wsname);
		
		try {
			Tuple6<Long,String,String,String,String,String> info = ws.getWorkspaceInfo(wsi);
			System.out.println("WS is ready, details:");
			printWsInfo(info);
		} catch (Exception e) {
			System.out.println("WS is not ready, attempting to create.");
			try {
				CreateWorkspaceParams params = new CreateWorkspaceParams();
				params.setWorkspace(wsi.getWorkspace());
				params.setDescription("test ws for wstester1");
				params.setGlobalread("n");
				Tuple6<Long,String,String,String,String,String> info = ws.createWorkspace(params);
				System.out.println("Created WS: "+params.getWorkspace());
				printWsInfo(info);
			} catch (Exception e1) {
				System.out.println("WS could not be created, attempting to undelete.");
				ws.undeleteWorkspace(wsi);
				System.out.println("Undeleted WS: "+wsi.getWorkspace());
				Tuple6<Long,String,String,String,String,String> info = ws.getWorkspaceInfo(wsi);
				printWsInfo(info);
			}
		}
	}
	
	
	
	private static void registerSpecFile() throws Exception {
		
		GetModuleInfoParams gmip = new GetModuleInfoParams();
		gmip.setMod("File");
		try {
			ModuleInfo info = ws.getModuleInfo(gmip);
			System.out.println(info.getDescription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(true) return;
		
		BufferedReader br = new BufferedReader(new FileReader("File.spec"));
		String content = "";
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        content = sb.toString();
	    } finally {
	        br.close();
	    }
	    CompileTypespecParams ctp = new CompileTypespecParams();
	    ctp.setDryrun(new Long(0));
	    ctp.setSpec(content);
	    ctp.setDependencies(new HashMap<String,Long>());
	    List<String> newTypes = new ArrayList<String>();
	    newTypes.add("TextFile");
	    newTypes.add("FastaFile");
	    ctp.setNewTypes(newTypes);
	    //System.out.println(ctp.getMod());
	    Map<String,String> result = ws.compileTypespec(ctp);
	    System.out.println("compilation result:");
	    for(Map.Entry<String, String> e : result.entrySet()) {
	    	System.out.println(e.getKey()+" -> "+e.getValue());
	    }
	    ws.releaseModule("File");
	}
	
	
	
	
	@Test
	public void test() throws Exception {
		
		//ws.requestModuleOwnership("File");
		/*SaveObjectsParams p = new SaveObjectsParams();
		ObjectSaveData o = new ObjectSaveData();
		TextFile tf = new TextFile();
		tf.setFilename("myFile.txt");
		tf.setContent("hello world.");
		o.setData(new UObject(tf));
		o.setName(tf.getFilename());
		o.setType("File.TextFile");
		
		List <ObjectSaveData> data = new ArrayList<ObjectSaveData>();
		data.add(o);
		p.setObjects(data);
		p.setWorkspace("TestWS1");
		ws.saveObjects(p);
		*/
		List <ObjectIdentity> oil = new ArrayList<ObjectIdentity>();
		ObjectIdentity oi = new ObjectIdentity();
		oi.setName("myFile.txt");
		oi.setWorkspace("TestWS1");
		oil.add(oi);
		List <ObjectData> objs = ws.getObjects(oil);
		System.out.println("retrieved:");
		for(ObjectData obj : objs) {
			System.out.println(obj.getData().asJsonNode());
			TextFile t = obj.getData().asClassInstance(TextFile.class);
			System.out.println("filename : "+t.getFilename());
			System.out.println("content : "+t.getContent());
		}
		
		/*CreateWorkspaceParams params = new CreateWorkspaceParams();
		params.setWorkspace(wsi.getWorkspace());
		params.setDescription("test ws for wstester1");
		params.setGlobalread("n");
		Tuple6<Long,String,String,String,String,String> info = ws.createWorkspace(params);
		
		System.out.println("Created WS: "+params.getWorkspace());
		printWsInfo(info);
		
		*/
		
		//ws.deleteWorkspace(wsi);
		//System.out.println("Deleted Ws.");
		
		
		//try {
		//	WsFileManager wfm = new WsFileManager();
		//} catch (Exception e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	}

	
	private static void printWsInfo(Tuple6<Long,String,String,String,String,String> info) {
		System.out.println("  [1]:"+info.getE1());
		System.out.println("  [2]:"+info.getE2());
		System.out.println("  [3]:"+info.getE3());
		System.out.println("  [4]:"+info.getE4());
		System.out.println("  [5]:"+info.getE5());
		System.out.println("  [6]:"+info.getE6());
	}
}
