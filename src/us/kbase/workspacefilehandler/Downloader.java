
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
 * <p>Original spec-file type: Downloader</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "input_wstype",
    "output_filetype"
})
public class Downloader {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("input_wstype")
    private String inputWstype;
    @JsonProperty("output_filetype")
    private String outputFiletype;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Downloader withId(String id) {
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

    public Downloader withName(String name) {
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

    public Downloader withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("input_wstype")
    public String getInputWstype() {
        return inputWstype;
    }

    @JsonProperty("input_wstype")
    public void setInputWstype(String inputWstype) {
        this.inputWstype = inputWstype;
    }

    public Downloader withInputWstype(String inputWstype) {
        this.inputWstype = inputWstype;
        return this;
    }

    @JsonProperty("output_filetype")
    public String getOutputFiletype() {
        return outputFiletype;
    }

    @JsonProperty("output_filetype")
    public void setOutputFiletype(String outputFiletype) {
        this.outputFiletype = outputFiletype;
    }

    public Downloader withOutputFiletype(String outputFiletype) {
        this.outputFiletype = outputFiletype;
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
