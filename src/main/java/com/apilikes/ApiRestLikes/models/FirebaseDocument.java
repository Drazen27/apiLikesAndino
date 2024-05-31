package com.apilikes.ApiRestLikes.models;

import com.google.cloud.firestore.annotation.DocumentId;

public class FirebaseDocument {
    @DocumentId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
