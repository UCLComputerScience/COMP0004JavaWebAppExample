package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private final String id;
    private String title;
    private List<NoteContent> contents = new ArrayList<>();

    public Note(String id, String title) {
        this.id = id;
        this.title = title;
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

    public List<NoteContent> getAllContent() {
        return contents;
    }

}
