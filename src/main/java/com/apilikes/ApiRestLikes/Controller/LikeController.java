package com.apilikes.ApiRestLikes.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import com.apilikes.ApiRestLikes.Entities.Like;
import com.apilikes.ApiRestLikes.Services.LikeService;
import com.apilikes.ApiRestLikes.dataContract.reponses.LikeGetResponse;
import com.apilikes.ApiRestLikes.dataContract.reponses.LikePostResponse;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

 private final LikeService likeService;

    
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/getLikes")
    public LikeGetResponse getAllLikes() throws ExecutionException, InterruptedException {
        return likeService.getAllLikes();
    }

    @PostMapping("/addLike")
    public LikePostResponse addLike(@RequestBody Like like) throws ExecutionException, InterruptedException {
        return likeService.addLike(like);
    }
}
