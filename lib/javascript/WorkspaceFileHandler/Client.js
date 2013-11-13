

function WorkspaceFileHandler(url, auth, auth_cb) {

    var _url = url;
    var deprecationWarningSent = false;
    
    function deprecationWarning() {
        if (!deprecationWarningSent) {
            deprecationWarningSent = true;
            if (!window.console) return;
            console.log(
                "DEPRECATION WARNING: '*_async' method names will be removed",
                "in a future version. Please use the identical methods without",
                "the'_async' suffix.");
        }
    }

    if (typeof(_url) != "string" || _url.length == 0) {
        _url = "http://kbase.us/services/workspace_file_service/";
    }
    var _auth = auth ? auth : { 'token' : '', 'user_id' : ''};
    var _auth_cb = auth_cb;


    this.getAllFileTypes = function (_callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getAllFileTypes",
        [], 1, _callback, _errorCallback);
};

    this.getAllFileTypes_async = function (_callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getAllFileTypes", [], 1, _callback, _error_callback);
    };

    this.getUploadableFileTypes = function (_callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getUploadableFileTypes",
        [], 1, _callback, _errorCallback);
};

    this.getUploadableFileTypes_async = function (_callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getUploadableFileTypes", [], 1, _callback, _error_callback);
    };

    this.getDownloadableFileTypes = function (_callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getDownloadableFileTypes",
        [], 1, _callback, _errorCallback);
};

    this.getDownloadableFileTypes_async = function (_callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getDownloadableFileTypes", [], 1, _callback, _error_callback);
    };

    this.getFileType = function (ids, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getFileType",
        [ids], 1, _callback, _errorCallback);
};

    this.getFileType_async = function (ids, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getFileType", [ids], 1, _callback, _error_callback);
    };

    this.upload = function (parameters, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.upload",
        [parameters], 1, _callback, _errorCallback);
};

    this.upload_async = function (parameters, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.upload", [parameters], 1, _callback, _error_callback);
    };

    this.upload_batch = function (paramameters_list, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.upload_batch",
        [paramameters_list], 1, _callback, _errorCallback);
};

    this.upload_batch_async = function (paramameters_list, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.upload_batch", [paramameters_list], 1, _callback, _error_callback);
    };

    this.download = function (parameters, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.download",
        [parameters], 1, _callback, _errorCallback);
};

    this.download_async = function (parameters, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.download", [parameters], 1, _callback, _error_callback);
    };

    this.download_batch = function (parameters, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.download_batch",
        [parameters], 1, _callback, _errorCallback);
};

    this.download_batch_async = function (parameters, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.download_batch", [parameters], 1, _callback, _error_callback);
    };

    this.getDownloader = function (ids, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getDownloader",
        [ids], 1, _callback, _errorCallback);
};

    this.getDownloader_async = function (ids, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getDownloader", [ids], 1, _callback, _error_callback);
    };

    this.getUploader = function (ids, _callback, _errorCallback) {
    return json_call_ajax("WorkspaceFileHandler.getUploader",
        [ids], 1, _callback, _errorCallback);
};

    this.getUploader_async = function (ids, _callback, _error_callback) {
        deprecationWarning();
        return json_call_ajax("WorkspaceFileHandler.getUploader", [ids], 1, _callback, _error_callback);
    };
 

    /*
     * JSON call using jQuery method.
     */
    function json_call_ajax(method, params, numRets, callback, errorCallback) {
        var deferred = $.Deferred();

        if (typeof callback === 'function') {
           deferred.done(callback);
        }

        if (typeof errorCallback === 'function') {
           deferred.fail(errorCallback);
        }

        var rpc = {
            params : params,
            method : method,
            version: "1.1",
            id: String(Math.random()).slice(2),
        };
        
        var beforeSend = null;
        var token = (_auth_cb && typeof _auth_cb === 'function') ? _auth_cb()
            : (_auth.token ? _auth.token : null);
        if (token != null) {
            beforeSend = function (xhr) {
                xhr.setRequestHeader("Authorization", token);
            }
        }

        jQuery.ajax({
            url: _url,
            dataType: "text",
            type: 'POST',
            processData: false,
            data: JSON.stringify(rpc),
            beforeSend: beforeSend,
            success: function (data, status, xhr) {
                var result;
                try {
                    var resp = JSON.parse(data);
                    result = (numRets === 1 ? resp.result[0] : resp.result);
                } catch (err) {
                    deferred.reject({
                        status: 503,
                        error: err,
                        url: _url,
                        resp: data
                    });
                    return;
                }
                deferred.resolve(result);
            },
            error: function (xhr, textStatus, errorThrown) {
                var error;
                if (xhr.responseText) {
                    try {
                        var resp = JSON.parse(xhr.responseText);
                        error = resp.error;
                    } catch (err) { // Not JSON
                        error = "Unknown error - " + xhr.responseText;
                    }
                } else {
                    error = "Unknown Error";
                }
                deferred.reject({
                    status: 500,
                    error: error
                });
            }
        });
        return deferred.promise();
    }
}


