package uk.ac.ucl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private final String id;
    private String title;
    private List<NoteContent> contents;

    @JsonCreator
    public Note(@JsonProperty("id") String id, @JsonProperty("title") String title, @JsonProperty("contents") List<NoteContent> contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Note(String id, String title) {
        this.id = id;
        this.title = title;
        this.contents = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addContent(NoteContent content) {
        contents.add(content);
    }

    public void setContents(List<NoteContent> contents) {
        this.contents = contents;
    }

    public List<NoteContent> getContents() {
        return contents;
    }

    public void appendImage(String imageSrc) {
        NoteContent imageContent = new NoteContent(imageSrc, "image");
        contents.add(imageContent);
    }

    public void deleteAllContents() {
        for (NoteContent content : contents) {
            content.deleteContent();
        }
        contents.clear();
    }

}
