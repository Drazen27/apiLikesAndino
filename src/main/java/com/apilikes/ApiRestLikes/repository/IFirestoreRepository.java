package com.apilikes.ApiRestLikes.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.apilikes.ApiRestLikes.Entities.FirebaseDocument;


public interface IFirestoreRepository<T extends FirebaseDocument> {
    T getById(Class<T> clazz, String id) throws ExecutionException, InterruptedException;
    T add(T model)  throws ExecutionException, InterruptedException;
    List<T> getAll(Class<T> clazz) throws ExecutionException, InterruptedException;
    T update(String id,T model) throws ExecutionException, InterruptedException;
    boolean delete(String id)throws ExecutionException, InterruptedException;
    List<T> search(T model) throws ExecutionException, InterruptedException, IllegalAccessException;
    List<T> searchExact(T model) throws ExecutionException, InterruptedException, IllegalAccessException;
}

