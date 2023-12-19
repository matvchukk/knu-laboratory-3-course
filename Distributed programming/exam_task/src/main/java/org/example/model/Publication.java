package org.example.model;

import java.io.Serializable;
import java.util.Arrays;

public class Publication implements Serializable {

    private int id;
    private String title;
    private String[] authors;
    private int yearOfPublication;

    private String[] keywords;

    public Publication() {
    }

    public Publication(int id, String title, String[] authors, int yearOfPublication, String[] keywords) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", yearOfPublication='" + yearOfPublication + '\'' +
                ", keywords=" + Arrays.toString(keywords) +
                '}';
    }
}
