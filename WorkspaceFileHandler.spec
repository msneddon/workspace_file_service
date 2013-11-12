/*
Service for uploading files (of supported types) to the Workspace as typed objects, and downloading
those typed objects as a file.
*/
module WorkspaceFileHandler {

	/*
		A Ws ID (TODO: import this typedef from module Workspace)
		@id ws
	*/
	typedef string ws_id;
	
	/* the name of a WS (TODO: import this typedef from module Workspace) */
	typedef string ws_name;



	/* The unique id that can identify a specific file type (e.g. TXT, SBML3.v1.core) */
	typedef string filetype_id;

	/* The human readable name of a file type (e.g. 'Text File', 'SBML Level 3 Version 1 core') */
	typedef string filetype_name;
	
	/* The unique id of an uploader/downloader that is available */
	typedef string loader_id;
	
	/* The human readable name of an uploader/downloader that is available */
	typedef string loader_name;
	
	/* The name of a type definition available in the Workspace, i.e. "ModuleName.TypeName" */
	typedef string ws_typedefname;
	
	/*
		Information about a particular file type supported for upload or download from the workspace.
		  id                  - the unique ID of the FileType
		  name                - humand readable name of the file type (likely for display)
		  description         - brief (usually one-line) description of the filetype
		  url                 - webpage that provides more details on the file type
		  valid_extensions    - list of filename extensions which we support for this type
		  default_uploader    - id of the default uploader for this file type, empty if none defined
		  uploaders           - list of all uploaders registered for this filetype
		  default_downloader  - id of the default downloader for this filetype, empty if none defined
		  downloaders         - list of all downloaders registered for this filetype
	*/
	typedef structure {
		filetype_id id;
		filetype_name name;
		string description;
		string url;
		list <string> valid_extensions;
		loader_id default_uploader;
		list <loader_id> uploaders;
		string loader_id;
		list <loader_id> downloaders;
	} FileType;
	
	
	typedef structure {
		loader_id id;
		loader_name name;
		string description;
		ws_typedefname input_wstype;
		filetype_id output_filetype;
	} Downloader;
	
	typedef structure {
		loader_id id;
		loader_name name;
		string description;
		filetype_id input_filetype;
		ws_typedefname output_wstype;
	} Uploader;
	
	
	/* The name of a specific file (e.g. comments.txt, myModel.sbml) */
	typedef string filename;
	
	/* The content of a file, serialized as a string. (TODO: define encoding or encoding options)*/
	typedef string filecontent;
	
	
	/*
		Fetch a list of all file types that are known, note that not all will support both upload and
		download.
	*/
	funcdef getAllFileTypes() returns(list<FileType>);
	
	/*
		Fetch a list of supported file types that can be uploaded as a Workspace object.
	*/
	funcdef getUploadableFileTypes() returns(list<FileType>);
	
	/*
		Fetch a list of supported file types that can be downloaded as a file.
	*/
	funcdef getDownloadableFileTypes() returns(list<FileType>);
	
	/*
		Given a list of filetype_ids, return a list of the FileType information if the type exists.
	*/
	funcdef getFileType(list<filetype_id> ids) returns(list<FileType>);
	
	
	
	/*
		@optional uploader
		@optional metadata
	*/
	typedef structure {
		filetype_id type;
		filename name;
		filecontent content;
		ws_name ws_name;
		loader_id uploader;
		mapping <string,string> metadata;
	} UploadParams;
	
	funcdef upload(UploadParams parameters) returns(ws_id) authentication required;
	
	funcdef upload_batch(list<UploadParams> paramameters_list) returns(list<ws_id>) authentication required;
	
	
	/*
		@optional downloader
	*/
	typedef structure {
		filetype_id type;
		ws_id ws_id;
		loader_id downloader;
	} DownloadParams;
	
	funcdef download(DownloadParams parameters) returns(filecontent) authentication required;
	
	funcdef download_batch(list<DownloadParams> parameters) returns (mapping<ws_id,filecontent>) authentication required;
	
	
	
	
	
	
	funcdef getDownloader(list<loader_id> ids) returns (list<Downloader>);
	
	funcdef getUploader(list<loader_id> ids) returns (list<Uploader>);
};