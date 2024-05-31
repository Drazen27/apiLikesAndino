package com.apilikes.ApiRestLikes.dataContract.reponses;

import com.apilikes.ApiRestLikes.dataContract.Response;
import com.apilikes.ApiRestLikes.models.Like;

import java.util.List;

public class LikeResponse extends Response {
    private List<Like> data;

    // Getters y setters
    public List<Like> getData() {
        return data;
    }

    public void setData(List<Like> data) {
        this.data = data;
    }
}
