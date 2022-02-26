package com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.Bean;

public class News {
    String title;
    String description;
    String publishedAt;
    String source;
    String image;

    public News(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public News(String title, String description, String publishedAt, String source, String image) {
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.source = source;
        this.image = image;
    }
}
