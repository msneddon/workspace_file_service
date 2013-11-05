package us.kbase.workspacefilehandler.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import us.kbase.workspacefilehandler.core.WorkspaceFileManager;

public class TestFileManager {

	@Test
	public void test() {
		
		try {
			WorkspaceFileManager wfm = new WorkspaceFileManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
