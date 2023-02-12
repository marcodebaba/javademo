package com.demo.javademo.concurrency.future.completableDemo;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    public List<String> getCommentsByGoodsId(Integer goodsId) {
        return Arrays.asList("好", "一般", "非常好");
    }
}
