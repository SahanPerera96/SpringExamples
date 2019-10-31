package com.sahan.demo.modal;

import javax.persistence.*;

@Entity
public class DocumentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id = 0;


    private String filename;

    @Lob
    byte[] content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}