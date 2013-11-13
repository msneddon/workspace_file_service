
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
 * <p>Original spec-file type: DownloadedFile</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "src_obj",
    "filename",
    "content"
})
public class DownloadedFile {

    @JsonProperty("src_obj")
    private String srcObj;
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("content")
    private String content;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("src_obj")
    public String getSrcObj() {
        return srcObj;
    }

    @JsonProperty("src_obj")
    public void setSrcObj(String srcObj) {
        this.srcObj = srcObj;
    }

    public DownloadedFile withSrcObj(String srcObj) {
        this.srcObj = srcObj;
        return this;
    }

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public DownloadedFile withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    public DownloadedFile withContent(String content) {
        this.content = content;
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
