package com.demo.javademo.concurrency.future.completableDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    CommentService commentService;

    @Autowired
    RepoService repoService;

    @GetMapping("/goods")
    public List<Goods> goods() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Goods>> goodsFuture = CompletableFuture
                .supplyAsync(() -> goodsService.queryGoods());
        CompletableFuture cf = goodsFuture.thenApplyAsync(goods -> {
            goods.stream().map(goods1 -> CompletableFuture.supplyAsync(() -> {
                goods1.setRepo(repoService.getRepoByGoodsId(goods1.getId()));
                return goods1;
            }).thenCompose(goods2 -> CompletableFuture.supplyAsync(() -> {
                goods2.setComment(commentService.getCommentsByGoodsId(goods2.getId()));
                return goods2;
            }))).toArray(size -> new CompletableFuture[size]);
            return goods;
        });

        return (List<Goods>) cf.handleAsync((goods, th) -> th != null ? "系统繁忙" : goods).get();
    }
}
