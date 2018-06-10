package com.yhxx.wxapp.web.api;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @Author: Wanglf
 * @Date: Created in 16:39 2018/5/11
 * @modified By:
 */
@RestController
public class TestSomeThingController {



    public static void main(String[] args) {

        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maximumPoolSize = 1024;
        Long keepAliveTime = 60L;
        BlockingQueue<Runnable> myQue = new LinkedBlockingQueue<Runnable>(1000);
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,myQue);

        threadPool.execute();

    }

}
