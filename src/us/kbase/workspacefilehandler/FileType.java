
package us.kbase.workspacefilehandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: FileType</p>
 * <pre>
 * Information about a particular file type supported for upload or download from the workspace.
 *   id                  - the unique ID of the FileType
 *   name                - humand readable name of the file type (likely for display)
 *   description         - brief (usually one-line) description of the filetype
 *   url                 - webpage that provides more details on the file type
 *   valid_extensions    - list of filename extensions which we support for this type
 *   default_uploader    - id of the default uploader for this file type, empty if none defined
 *   uploaders           - list of all uploaders registered for this filetype
 *   default_downloader  - id of the default downloader for this filetype, empty if none defined
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "url",
    "valid_extensions",
    "default_uploader",
    "uploaders",
    "loader_id",
    "downloaders",
    "typedefnames"
})
public class FileType {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    @JsonProperty("valid_extensions")
    private List<String> validExtensions = new ArrayList<String>();
    @JsonProperty("default_uploader")
    private String defaultUploader;
    @JsonProperty("uploaders")
    private List<String> uploaders = new ArrayList<String>();
    @JsonProperty("loader_id")
    private String loaderId;
    @JsonProperty("downloaders")
    private List<String> downloaders = new ArrayList<String>();
    @JsonProperty("typedefnames")
    private List<String> typedefnames = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public FileType withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public FileType withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public FileType withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public FileType withUrl(String url) {
        this.url = url;
        return this;
    }

    @JsonProperty("valid_extensions")
    public List<String> getValidExtensions() {
        return validExtensions;
    }

    @JsonProperty("valid_extensions")
    public void setValidExtensions(List<String> validExtensions) {
        this.validExtensions = validExtensions;
    }

    public FileType withValidExtensions(List<String> validExtensions) {
        this.validExtensions = validExtensions;
        return this;
    }

    @JsonProperty("default_uploader")
    public String getDefaultUploader() {
        return defaultUploader;
    }

    @JsonProperty("default_uploader")
    public void setDefaultUploader(String defaultUploader) {
        this.defaultUploader = defaultUploader;
    }

    public FileType withDefaultUploader(String defaultUploader) {
        this.defaultUploader = defaultUploader;
        return this;
    }

    @JsonProperty("uploaders")
    public List<String> getUploaders() {
        return uploaders;
    }

    @JsonProperty("uploaders")
    public void setUploaders(List<String> uploaders) {
        this.uploaders = uploaders;
    }

    public FileType withUploaders(List<String> uploaders) {
        this.uploaders = uploaders;
        return this;
    }

    @JsonProperty("loader_id")
    public String getLoaderId() {
        return loaderId;
    }

    @JsonProperty("loader_id")
    public void setLoaderId(String loaderId) {
        this.loaderId = loaderId;
    }

    public FileType withLoaderId(String loaderId) {
        this.loaderId = loaderId;
        return this;
    }

    @JsonProperty("downloaders")
    public List<String> getDownloaders() {
        return downloaders;
    }

    @JsonProperty("downloaders")
    public void setDownloaders(List<String> downloaders) {
        this.downloaders = downloaders;
    }

    public FileType withDownloaders(List<String> downloaders) {
        this.downloaders = downloaders;
        return this;
    }

    @JsonProperty("typedefnames")
    public List<String> getTypedefnames() {
        return typedefnames;
    }

    @JsonProperty("typedefnames")
    public void setTypedefnames(List<String> typedefnames) {
        this.typedefnames = typedefnames;
    }

    public FileType withTypedefnames(List<String> typedefnames) {
        this.typedefnames = typedefnames;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
