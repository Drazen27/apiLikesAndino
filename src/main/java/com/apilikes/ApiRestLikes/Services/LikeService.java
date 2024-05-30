package com.apilikes.ApiRestLikes.Services;
import com.apilikes.ApiRestLikes.Entities.Like;
import com.apilikes.ApiRestLikes.dataContract.reponses.LikeGetResponse;
import com.apilikes.ApiRestLikes.dataContract.reponses.LikePostResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class LikeService {

    private static final String COLLECTION_NAME = "likes";
    
     
    public LikeGetResponse getAllLikes() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).get();
        QuerySnapshot querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Like> likes = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            Like like = document.toObject(Like.class);
            likes.add(like);
        }

        LikeGetResponse response = new LikeGetResponse();
        response.setSuccess(true);
        response.setMessage("Likes fetched successfully");
        response.setData(likes);

        return response;
    }


    public LikePostResponse addLike(Like like) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> result = db.collection("likes").add(like);

        LikePostResponse response = new LikePostResponse();
        response.setSuccess(true);
        response.setMessage("Like added successfully");
        try {
            response.setId(result.get().getId());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to add like");
        }

        return response;
    }
}