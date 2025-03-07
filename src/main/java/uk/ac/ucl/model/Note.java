package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private final String id;
    private String title;
    private List<NoteContent> contents = new ArrayList<>();

    public Note(String id) {
        this.id = id;
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

}
