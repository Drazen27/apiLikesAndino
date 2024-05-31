package com.apilikes.ApiRestLikes.Services;
import com.apilikes.ApiRestLikes.models.Like;
import com.apilikes.ApiRestLikes.repository.FirestoreRepository;
import com.apilikes.ApiRestLikes.repository.IFirestoreRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class LikeService implements IFirestoreRepository<Like> {

   
    private  FirestoreRepository firestoreRepository;
    
    public LikeService() throws IOException {
        this.firestoreRepository = new FirestoreRepository("likes");
    }
    
   @Override
    public List<Like> getAll(Class<Like> clazz) throws ExecutionException, InterruptedException {
        return firestoreRepository.getAll(clazz);
    }

    @Override
    public Like getById(Class<Like> clazz, String id) throws ExecutionException, InterruptedException {
        return firestoreRepository.getById(clazz, id);
    }

    @Override
    public Like add(Like model) throws ExecutionException, InterruptedException {
        try {
            Like aux = new Like();
            aux.setCancion(model.getCancion());
            aux.setId_usuario(model.getId_usuario());

            List<Like> likes = searchExact(aux);
            
            if (!likes.isEmpty()) {
                throw new IllegalStateException("Like already exists");
            }
            return firestoreRepository.add(model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing fields for search", e);
        }
    }

    @Override
    public Like update(String id, Like model) throws ExecutionException, InterruptedException {
        return firestoreRepository.update(id, model);
    }
    @Override
    public boolean delete(String id) throws ExecutionException, InterruptedException {
        return firestoreRepository.delete(id);
    }
    @Override
    public List<Like> search(Like model) throws ExecutionException, InterruptedException, IllegalAccessException{
        return firestoreRepository.search(Like.class, model);
    }
    @Override
    public List<Like> searchExact(Like model) throws ExecutionException, InterruptedException, IllegalAccessException {
        return firestoreRepository.searchExact(Like.class, model);
    }
}