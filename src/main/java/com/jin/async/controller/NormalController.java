package com.jin.async.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jin.async.service.AsyncService;

@RestController
public class NormalController {
	private static final Logger logger = LoggerFactory.getLogger(NormalController.class);
			
	@Autowired
	AsyncService asyncService;
	
	@RequestMapping(method=RequestMethod.GET, value="/asyncmethodone")
	public String testAsynOne() throws Exception {
		asyncService.asyncMethodOne();
		return "Done";
	}

	@RequestMapping(method=RequestMethod.GET, value="/asyncmethodtwo")
	public String testAsynTwo() throws Exception {
		Future<String> future  = asyncService.asyncMethodTwo();
		while (true) {
	        if (future.isDone()) {
	            return future.get();	            
	        }
	        logger.info("Wait or doing something else");
	        Thread.sleep(1000);
	    }	
	}

	@RequestMapping(method=RequestMethod.GET, value="/asyncmethodthree")
	public String testAsynThree() throws Exception {
		CompletableFuture<String> futureFirst  = asyncService.asyncMethodThree("First");
		CompletableFuture<String> futureSecond  = asyncService.asyncMethodThree("Second");
		CompletableFuture.allOf(futureFirst, futureSecond).join();
		return futureFirst.get() + futureSecond.get();
	}
	
}
