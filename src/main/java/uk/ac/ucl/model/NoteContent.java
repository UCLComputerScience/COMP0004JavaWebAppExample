package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NoteContent {
    private String content;
    private String contentType;

    @JsonCreator
    public NoteContent(@JsonProperty("content") String content, @JsonProperty("contentType") String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
