package com.example.note_app;

public class NoteModel {
    private String n_title;
    private String n_body;
    private String n_createdAt;

    public NoteModel(String n_title, String n_body, String n_createdAt) {
        this.n_title = n_title;
        this.n_body = n_body;
        this.n_createdAt = n_createdAt;
    }

    public String getN_createdAt() {
        return n_createdAt;
    }

    public void setN_createdAt(String n_createdAt) {
        this.n_createdAt = n_createdAt;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_body() {
        return n_body;
    }

    public void setN_body(String n_body) {
        this.n_body = n_body;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "n_title='" + n_title + '\'' +
                ", n_body='" + n_body + '\'' +
                ", n_createdAt='" + n_createdAt + '\'' +
                '}';
    }
}
