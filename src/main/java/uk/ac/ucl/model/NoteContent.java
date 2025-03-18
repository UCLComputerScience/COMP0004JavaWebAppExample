package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

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

    public void deleteContent() {
        if (contentType.equals("image")) {
            File file = new File(content);
            if (file.exists()) {
                file.delete();
            }
        }
        this.content = null;
    }
}
