
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
 * <p>Original spec-file type: DownloadParams</p>
 * <pre>
 * @optional downloader
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "ref",
    "downloader"
})
public class DownloadParams {

    @JsonProperty("type")
    private String type;
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("downloader")
    private String downloader;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public DownloadParams withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("ref")
    public String getRef() {
        return ref;
    }

    @JsonProperty("ref")
    public void setRef(String ref) {
        this.ref = ref;
    }

    public DownloadParams withRef(String ref) {
        this.ref = ref;
        return this;
    }

    @JsonProperty("downloader")
    public String getDownloader() {
        return downloader;
    }

    @JsonProperty("downloader")
    public void setDownloader(String downloader) {
        this.downloader = downloader;
    }

    public DownloadParams withDownloader(String downloader) {
        this.downloader = downloader;
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
