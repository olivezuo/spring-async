package com.jin.async.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

	private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);
	@Async
	public void asyncMethodOne() throws Exception {
		
		logger.info("Async method one:   " + Thread.currentThread().getName());
		throw new Exception("Async Exception");
	}
	
	@Async
	public Future<String> asyncMethodTwo() {
		
		logger.info("Async method two:   " + Thread.currentThread().getName());
	    try {
	        Thread.sleep(5000);
	        logger.info("Async method two wakeup!");
	        return new AsyncResult<String>("We need 5 seconds to run");
	    } catch (InterruptedException e) {
	        logger.error("Something wrong in async method two." + e.getLocalizedMessage());
	    }
	    	 
	    return null; 
	}
	
	@Async
    public CompletableFuture<String> asyncMethodThree(String flag) throws InterruptedException {
		logger.info("Async method three:   " + Thread.currentThread().getName());
        Thread.sleep(4000L);
        return CompletableFuture.completedFuture("Method three done with flag " + flag);
    }

}
