package us.kbase.workspacefilehandler;

import java.util.List;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;

//BEGIN_HEADER
import java.util.ArrayList;
import java.net.URL;

import us.kbase.workspacefilehandler.core.FileData;
import us.kbase.workspacefilehandler.core.WsFileDownloader;
import us.kbase.workspacefilehandler.core.WsFileManager;
import us.kbase.workspacefilehandler.core.WsLoaderDispatcher;
import us.kbase.workspacefilehandler.core.WsUploadResult;
//END_HEADER

/**
 * <p>Original spec-file module name: WorkspaceFileHandler</p>
 * <pre>
 * Service for uploading files (of supported types) to the Workspace as typed objects, and downloading
 * those typed objects as a file.
 * </pre>
 */
public class WorkspaceFileHandlerServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;

    //BEGIN_CLASS_HEADER
    private static String wsURL = "http://140.221.84.170:7058";
    private WsFileManager manager;;
    private WsLoaderDispatcher dispatcher;
    //END_CLASS_HEADER

    public WorkspaceFileHandlerServer() throws Exception {
        super("WorkspaceFileHandler");
        //BEGIN_CONSTRUCTOR
        this.manager = new WsFileManager();
        this.dispatcher = new WsLoaderDispatcher(manager,new URL(wsURL));
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: getAllFileTypes</p>
     * <pre>
     * Fetch a list of all file types that are known, note that not all will support both upload and
     * download.
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getAllFileTypes")
    public List<FileType> getAllFileTypes() throws Exception {
        List<FileType> returnVal = null;
        //BEGIN getAllFileTypes
        returnVal = manager.getAllFileTypes();
        //END getAllFileTypes
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getUploadableFileTypes</p>
     * <pre>
     * Fetch a list of supported file types that can be uploaded as a Workspace object.
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getUploadableFileTypes")
    public List<FileType> getUploadableFileTypes() throws Exception {
        List<FileType> returnVal = null;
        //BEGIN getUploadableFileTypes
        returnVal = manager.getAllUploadableFileTypes();
        //END getUploadableFileTypes
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getDownloadableFileTypes</p>
     * <pre>
     * Fetch a list of supported file types that can be downloaded as a file.
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getDownloadableFileTypes")
    public List<FileType> getDownloadableFileTypes() throws Exception {
        List<FileType> returnVal = null;
        //BEGIN getDownloadableFileTypes
        returnVal = manager.getAllUploadableFileTypes();
        //END getDownloadableFileTypes
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getFileType</p>
     * <pre>
     * Given a list of filetype_ids, return a list of the FileType information if the type exists.
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getFileType")
    public List<FileType> getFileType(List<String> ids) throws Exception {
        List<FileType> returnVal = null;
        //BEGIN getFileType
        returnVal = manager.getFileType(ids);
        //END getFileType
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: upload</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "UploadParams" (see {@link us.kbase.workspacefilehandler.UploadParams UploadParams} for details)
     * @return   Original type "ws_obj_reference" (A Ws ID (TODO: import this typedef from module Workspace) @id ws)
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.upload")
    public String upload(UploadParams parameters, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN upload
        WsUploadResult uploadResult = dispatcher.upload(parameters, authPart);
        returnVal = uploadResult.getAbsWsObjReference();
        //END upload
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: download</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "DownloadParams" (see {@link us.kbase.workspacefilehandler.DownloadParams DownloadParams} for details)
     * @return   Original type "DownloadedFile" (see {@link us.kbase.workspacefilehandler.DownloadedFile DownloadedFile} for details)
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.download")
    public DownloadedFile download(DownloadParams parameters, AuthToken authPart) throws Exception {
        DownloadedFile returnVal = null;
        //BEGIN download
        FileData fd = dispatcher.download(parameters, authPart);
        returnVal = new DownloadedFile();
        returnVal.setFilename(fd.getFilename());
        returnVal.setContent(fd.getContent());
        returnVal.setSrcObj(fd.getSrcReference());
        //END download
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: <program> <server_port>");
            return;
        }
        new WorkspaceFileHandlerServer().startupServer(Integer.parseInt(args[0]));
    }
}
