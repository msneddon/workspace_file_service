
package us.kbase.file;

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
 * <p>Original spec-file type: FastaFile</p>
 * <pre>
 * @searchable ws_subset filename
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "filename",
    "content"
})
public class FastaFile {

    @JsonProperty("filename")
    private String filename;
    @JsonProperty("content")
    private List<FastaRow> content = new ArrayList<FastaRow>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FastaFile withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    @JsonProperty("content")
    public List<FastaRow> getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(List<FastaRow> content) {
        this.content = content;
    }

    public FastaFile withContent(List<FastaRow> content) {
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
