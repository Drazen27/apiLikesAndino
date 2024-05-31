package com.apilikes.ApiRestLikes.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.apilikes.ApiRestLikes.Entities.Like;
import com.apilikes.ApiRestLikes.Services.LikeService;
import com.apilikes.ApiRestLikes.dataContract.reponses.LikeResponse;
import com.apilikes.ApiRestLikes.dataContract.requests.LikeRequest;
import com.google.cloud.Timestamp;


@RestController
@RequestMapping("/api/likes")
public class LikeController {
    
    private final LikeService likeService;

    
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    

    @GetMapping("/getById/{id}")
    public ResponseEntity<LikeResponse> getById(@PathVariable String id) {
        try {
            Like like = likeService.getById(Like.class, id);
            LikeResponse response = new LikeResponse();

            if (like != null) {
                response.setSuccess(true);
                response.setMessage("Like found");
                response.setData(List.of(like));
                return ResponseEntity.ok(response);
            } else {
                response.setSuccess(false);
                response.setMessage("Like not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getLikes")
    public ResponseEntity<LikeResponse> getAll() {
        try {
            List<Like> likes = likeService.getAll(Like.class);
            LikeResponse response = new LikeResponse();
            response.setSuccess(true);
            response.setMessage("Likes retrieved successfully");
            response.setData(likes);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addLike")
    public ResponseEntity<LikeResponse> addLike(@RequestBody LikeRequest likeReq) {
        try {
            
            Like like = new Like(); 
            like.setCancion(likeReq.getCancion());
            like.setId_usuario(likeReq.getId_usuario());
            if(likeReq.getFecha()!=null)
                like.setFecha(Timestamp.parseTimestamp(likeReq.getFecha()));

            Like addedLike = likeService.add(like);

            LikeResponse response = new LikeResponse();
            response.setSuccess(true);
            response.setMessage("Like added successfully");

            response.setData(List.of(addedLike));
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<LikeResponse> update(@PathVariable String id, @RequestBody LikeRequest likeReq) {
        try {
            Like like = new Like(); 
            like.setCancion(likeReq.getCancion());
            like.setId_usuario(likeReq.getId_usuario());
            if(likeReq.getFecha()!=null)
                like.setFecha(Timestamp.parseTimestamp(likeReq.getFecha()));

            Like updatedLike = likeService.update(id, like);

            LikeResponse response = new LikeResponse();
            if (updatedLike != null) {
                response.setSuccess(true);
                response.setMessage("Like updated successfully");
                response.setData(List.of(updatedLike));
                return ResponseEntity.ok(response);
            } else {
                response.setSuccess(false);
                response.setMessage("Failed to update like");
                return ResponseEntity.status(500).body(response);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<LikeResponse> delete(@PathVariable String id) {
        try {
            boolean isDeleted = likeService.delete(id);
            LikeResponse response = new LikeResponse();
            if (isDeleted) {
                response.setSuccess(true);
                response.setMessage("Like deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.setSuccess(false);
                response.setMessage("Failed to delete like");
                return ResponseEntity.status(500).body(response);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<LikeResponse> search(@RequestBody LikeRequest likeReq) {
        try {
            Like model = new Like(); 
            model.setCancion(likeReq.getCancion());
            model.setId_usuario(likeReq.getId_usuario());
            if(likeReq.getFecha()!=null)
                model.setFecha(Timestamp.parseTimestamp(likeReq.getFecha()));

            List<Like> likes = likeService.search(model);
            LikeResponse response = new LikeResponse();
            response.setSuccess(true);
            response.setMessage("Likes retrieved successfully");
            response.setData(likes);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException | IllegalAccessException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/searchExact")
    public ResponseEntity<LikeResponse> searchExact(@RequestBody LikeRequest likeReq) {
        try {
            Like model = new Like(); 
            model.setCancion(likeReq.getCancion());
            model.setId_usuario(likeReq.getId_usuario());
            if(likeReq.getFecha()!=null)
                model.setFecha(Timestamp.parseTimestamp(likeReq.getFecha()));

            List<Like> likes = likeService.searchExact(model);
            LikeResponse response = new LikeResponse();
            response.setSuccess(true);
            response.setMessage("Likes retrieved successfully");
            response.setData(likes);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException | IllegalAccessException e) {
            e.printStackTrace();
            LikeResponse response = new LikeResponse();
            response.setSuccess(false);
            response.setMessage("Internal server error");
            return ResponseEntity.status(500).body(response);
        }
    }
}
