package us.kbase.workspacefilehandler;

import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;

//BEGIN_HEADER
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
    //END_CLASS_HEADER

    public WorkspaceFileHandlerServer() throws Exception {
        super("WorkspaceFileHandler");
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
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
        //END getFileType
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: upload</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "UploadParams" (see {@link us.kbase.workspacefilehandler.UploadParams UploadParams} for details)
     * @return   Original type "ws_id" (A Ws ID (TODO: import this typedef from module Workspace) @id ws)
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.upload")
    public String upload(UploadParams parameters, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN upload
        //END upload
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: upload_batch</p>
     * <pre>
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.upload_batch")
    public List<String> uploadBatch(List<UploadParams> paramametersList, AuthToken authPart) throws Exception {
        List<String> returnVal = null;
        //BEGIN upload_batch
        //END upload_batch
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: download</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "DownloadParams" (see {@link us.kbase.workspacefilehandler.DownloadParams DownloadParams} for details)
     * @return   Original type "filecontent" (The content of a file, serialized as a string. (TODO: define encoding or encoding options))
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.download")
    public String download(DownloadParams parameters, AuthToken authPart) throws Exception {
        String returnVal = null;
        //BEGIN download
        //END download
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: download_batch</p>
     * <pre>
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.download_batch")
    public Map<String,String> downloadBatch(List<DownloadParams> parameters, AuthToken authPart) throws Exception {
        Map<String,String> returnVal = null;
        //BEGIN download_batch
        //END download_batch
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getDownloader</p>
     * <pre>
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getDownloader")
    public List<Downloader> getDownloader(List<String> ids) throws Exception {
        List<Downloader> returnVal = null;
        //BEGIN getDownloader
        //END getDownloader
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: getUploader</p>
     * <pre>
     * </pre>
     */
    @JsonServerMethod(rpc = "WorkspaceFileHandler.getUploader")
    public List<Uploader> getUploader(List<String> ids) throws Exception {
        List<Uploader> returnVal = null;
        //BEGIN getUploader
        //END getUploader
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
