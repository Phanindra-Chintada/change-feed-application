package com.xyz.changefeed.application.config;

import com.azure.cosmos.ChangeFeedProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ChangefeedInitialiazer {

    @Autowired
    ChangefeedBuilder changefeedBuilder;

    private ChangeFeedProcessor changeFeedProcessor;

    @PostConstruct
    public void setupAndStartChangeFeed(){
        try{
            System.out.println("Starting ChangeFeed");
            changeFeedProcessor = changefeedBuilder.changeFeedProcessor();
            startChangeFeed();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void startChangeFeed() {
        if (changeFeedProcessor!=null)
            changeFeedProcessor.start().block();
    }

    @PreDestroy
    public void stopChangeFeed(){
        if(changeFeedProcessor!=null)
            changeFeedProcessor.stop().block();
    }
}
