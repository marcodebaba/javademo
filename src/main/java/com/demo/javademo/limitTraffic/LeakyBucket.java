package com.demo.javademo.limitTraffic;

/**
 * @author：marco.pan
 * @ClassName：LeakyBucket
 * @Description：
 * @date: 2022年04月16日 20:24
 */
public class LeakyBucket {
    // 当前时间
    public long timeStamp = System.currentTimeMillis();
    //桶的容量
    public long capacity;
    //水漏出的速度(每秒系统能处理的请求数)
    public long rate;
    // 当前水量(当前累积请求数)
    public long water;

    public boolean limit() {
        long now = System.currentTimeMillis();
        water = Math.max(0, water - ((now - timeStamp) / 1000) * rate);

        //先执行漏水，计算剩余水量
        timeStamp = now;
        if ((water + 1) < capacity) {
            // 尝试加水,并且水还未满
            water += 1;
            return true;
        } else {
            // 水满，拒绝加水
            return false;
        }
    }
}
