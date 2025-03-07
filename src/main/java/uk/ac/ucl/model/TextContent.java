package uk.ac.ucl.model;

public class TextContent extends NoteContent {
    private String content;

    public TextContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
