package com.app.implementation.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "photos") // Ensures the table is named "photos"
public class Photos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_name", nullable = false)
    private String photoName;

    @Lob
    @Column(name = "photo_data", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] content;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
