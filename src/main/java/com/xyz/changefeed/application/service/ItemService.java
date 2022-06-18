package com.xyz.changefeed.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.xyz.changefeed.application.models.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private StreamBridge streamBridge;

    public void processDocument(JsonNode document){
        if(document == null)
            return;
        ItemModel itemModel = null;
        try{
            itemModel = ItemModel.builder().id(document.get("id").asText())
                    .itemType(document.get("ItemType").asText())
                    .docType(document.get("docType").asText())
                    .quantity(document.get("quantity").asInt())
                    .location(document.get("location").asText())
                    .build();
            assert itemModel != null;
            this.streamBridge.send("itemstopic", MessageBuilder.withPayload(itemModel).build());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
