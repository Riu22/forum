package com.example.forum.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "topics")
public class topics {
    @Id
    int id;
    Date created_at;
    Date updated_at;
    String title;
    String content;
    int views;
    int number_of_replies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private categori category;

    public int getId() {
        return id;
    }

    public categori getCategory() {
        return category;
    }

    public void setCategory(categori category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getNumber_of_replies() {
        return number_of_replies;
    }

    public void setNumber_of_replies(int number_of_replies) {
        this.number_of_replies = number_of_replies;
    }
}
