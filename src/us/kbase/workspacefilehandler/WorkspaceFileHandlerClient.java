package us.kbase.workspacefilehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;

/**
 * <p>Original spec-file module name: WorkspaceFileHandler</p>
 * <pre>
 * Service for uploading files (of supported types) to the Workspace as typed objects, and downloading
 * those typed objects as a file.
 * </pre>
 */
public class WorkspaceFileHandlerClient {
    private JsonClientCaller caller;
    private static URL DEFAULT_URL = null;
    static {
        try {
            DEFAULT_URL = new URL("http://kbase.us/services/workspace_file_service/");
        } catch (MalformedURLException mue) {
            throw new RuntimeException("Compile error in client - bad url compiled");
        }
    }

    public WorkspaceFileHandlerClient() {
       caller = new JsonClientCaller(DEFAULT_URL);
    }

    public WorkspaceFileHandlerClient(URL url) {
        caller = new JsonClientCaller(url);
    }

    public WorkspaceFileHandlerClient(URL url, AuthToken token) {
        caller = new JsonClientCaller(url, token);
    }

    public WorkspaceFileHandlerClient(URL url, String user, String password) {
        caller = new JsonClientCaller(url, user, password);
    }

    public WorkspaceFileHandlerClient(AuthToken token) {
        caller = new JsonClientCaller(DEFAULT_URL, token);
    }

    public WorkspaceFileHandlerClient(String user, String password) {
        caller = new JsonClientCaller(DEFAULT_URL, user, password);
    }

    public boolean isAuthAllowedForHttp() {
        return caller.isAuthAllowedForHttp();
    }

    public void setAuthAllowedForHttp(boolean isAuthAllowedForHttp) {
        caller.setAuthAllowedForHttp(isAuthAllowedForHttp);
    }

    /**
     * <p>Original spec-file function name: getAllFileTypes</p>
     * <pre>
     * Fetch a list of all file types that are known, note that not all will support both upload and
     * download.
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FileType> getAllFileTypes() throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<List<FileType>>> retType = new TypeReference<List<List<FileType>>>() {};
        List<List<FileType>> res = caller.jsonrpcCall("WorkspaceFileHandler.getAllFileTypes", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getUploadableFileTypes</p>
     * <pre>
     * Fetch a list of supported file types that can be uploaded as a Workspace object.
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FileType> getUploadableFileTypes() throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<List<FileType>>> retType = new TypeReference<List<List<FileType>>>() {};
        List<List<FileType>> res = caller.jsonrpcCall("WorkspaceFileHandler.getUploadableFileTypes", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getDownloadableFileTypes</p>
     * <pre>
     * Fetch a list of supported file types that can be downloaded as a file.
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FileType> getDownloadableFileTypes() throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<List<FileType>>> retType = new TypeReference<List<List<FileType>>>() {};
        List<List<FileType>> res = caller.jsonrpcCall("WorkspaceFileHandler.getDownloadableFileTypes", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getFileType</p>
     * <pre>
     * Given a list of filetype_ids, return a list of the FileType information if the type exists.
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<FileType> getFileType(List<String> ids) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ids);
        TypeReference<List<List<FileType>>> retType = new TypeReference<List<List<FileType>>>() {};
        List<List<FileType>> res = caller.jsonrpcCall("WorkspaceFileHandler.getFileType", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: upload</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "UploadParams" (see {@link us.kbase.workspacefilehandler.UploadParams UploadParams} for details)
     * @return   Original type "ws_obj_reference" (A Ws ID (TODO: import this typedef from module Workspace) @id ws)
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String upload(UploadParams parameters) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(parameters);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("WorkspaceFileHandler.upload", args, retType, true, true);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: upload_batch</p>
     * <pre>
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<String> uploadBatch(List<UploadParams> paramametersList) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(paramametersList);
        TypeReference<List<List<String>>> retType = new TypeReference<List<List<String>>>() {};
        List<List<String>> res = caller.jsonrpcCall("WorkspaceFileHandler.upload_batch", args, retType, true, true);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: download</p>
     * <pre>
     * </pre>
     * @param   parameters   Original type "DownloadParams" (see {@link us.kbase.workspacefilehandler.DownloadParams DownloadParams} for details)
     * @return   Original type "DownloadedFile" (see {@link us.kbase.workspacefilehandler.DownloadedFile DownloadedFile} for details)
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public DownloadedFile download(DownloadParams parameters) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(parameters);
        TypeReference<List<DownloadedFile>> retType = new TypeReference<List<DownloadedFile>>() {};
        List<DownloadedFile> res = caller.jsonrpcCall("WorkspaceFileHandler.download", args, retType, true, true);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: download_batch</p>
     * <pre>
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public Map<String,DownloadedFile> downloadBatch(List<DownloadParams> parameters) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(parameters);
        TypeReference<List<Map<String,DownloadedFile>>> retType = new TypeReference<List<Map<String,DownloadedFile>>>() {};
        List<Map<String,DownloadedFile>> res = caller.jsonrpcCall("WorkspaceFileHandler.download_batch", args, retType, true, true);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getDownloader</p>
     * <pre>
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<Downloader> getDownloader(List<String> ids) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ids);
        TypeReference<List<List<Downloader>>> retType = new TypeReference<List<List<Downloader>>>() {};
        List<List<Downloader>> res = caller.jsonrpcCall("WorkspaceFileHandler.getDownloader", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: getUploader</p>
     * <pre>
     * </pre>
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<Uploader> getUploader(List<String> ids) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(ids);
        TypeReference<List<List<Uploader>>> retType = new TypeReference<List<List<Uploader>>>() {};
        List<List<Uploader>> res = caller.jsonrpcCall("WorkspaceFileHandler.getUploader", args, retType, true, false);
        return res.get(0);
    }
}
