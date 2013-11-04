
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
 * <p>Original spec-file type: Uploader</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "input_filetype",
    "output_wstype"
})
public class Uploader {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("input_filetype")
    private String inputFiletype;
    @JsonProperty("output_wstype")
    private String outputWstype;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Uploader withId(String id) {
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

    public Uploader withName(String name) {
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

    public Uploader withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("input_filetype")
    public String getInputFiletype() {
        return inputFiletype;
    }

    @JsonProperty("input_filetype")
    public void setInputFiletype(String inputFiletype) {
        this.inputFiletype = inputFiletype;
    }

    public Uploader withInputFiletype(String inputFiletype) {
        this.inputFiletype = inputFiletype;
        return this;
    }

    @JsonProperty("output_wstype")
    public String getOutputWstype() {
        return outputWstype;
    }

    @JsonProperty("output_wstype")
    public void setOutputWstype(String outputWstype) {
        this.outputWstype = outputWstype;
    }

    public Uploader withOutputWstype(String outputWstype) {
        this.outputWstype = outputWstype;
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
