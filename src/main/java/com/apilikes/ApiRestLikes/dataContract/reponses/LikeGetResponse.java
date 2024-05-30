package com.apilikes.ApiRestLikes.dataContract.reponses;

import com.apilikes.ApiRestLikes.Entities.Like;
import com.apilikes.ApiRestLikes.dataContract.Response;
import java.util.List;

public class LikeGetResponse extends Response {
    private List<Like> data;

    // Getters y setters
    public List<Like> getData() {
        return data;
    }

    public void setData(List<Like> data) {
        this.data = data;
    }
}
