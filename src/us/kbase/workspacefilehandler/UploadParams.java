
package us.kbase.workspacefilehandler;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: UploadParams</p>
 * <pre>
 * @optional uploader
 * @optional metadata
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "name",
    "content",
    "ws_name",
    "uploader",
    "metadata"
})
public class UploadParams {

    @JsonProperty("type")
    private java.lang.String type;
    @JsonProperty("name")
    private java.lang.String name;
    @JsonProperty("content")
    private java.lang.String content;
    @JsonProperty("ws_name")
    private java.lang.String wsName;
    @JsonProperty("uploader")
    private java.lang.String uploader;
    @JsonProperty("metadata")
    private Map<String, String> metadata;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("type")
    public java.lang.String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(java.lang.String type) {
        this.type = type;
    }

    public UploadParams withType(java.lang.String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public UploadParams withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("content")
    public java.lang.String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    public UploadParams withContent(java.lang.String content) {
        this.content = content;
        return this;
    }

    @JsonProperty("ws_name")
    public java.lang.String getWsName() {
        return wsName;
    }

    @JsonProperty("ws_name")
    public void setWsName(java.lang.String wsName) {
        this.wsName = wsName;
    }

    public UploadParams withWsName(java.lang.String wsName) {
        this.wsName = wsName;
        return this;
    }

    @JsonProperty("uploader")
    public java.lang.String getUploader() {
        return uploader;
    }

    @JsonProperty("uploader")
    public void setUploader(java.lang.String uploader) {
        this.uploader = uploader;
    }

    public UploadParams withUploader(java.lang.String uploader) {
        this.uploader = uploader;
        return this;
    }

    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public UploadParams withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
