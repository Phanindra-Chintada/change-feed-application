package com.xyz.changefeed.application.config;

import com.azure.cosmos.*;
import com.azure.cosmos.models.ChangeFeedProcessorOptions;
import com.fasterxml.jackson.databind.JsonNode;
import com.xyz.changefeed.application.properties.CosmosProperties;
import com.xyz.changefeed.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@Configuration
@ConfigurationProperties(prefix = "azure")
public class ChangefeedBuilder {
    @Autowired
    CosmosProperties cosmosProperties;

    @Autowired
    ItemService itemService;

    public ChangeFeedProcessor changeFeedProcessor(){
        CosmosAsyncClient client = new CosmosClientBuilder()
                .endpoint(cosmosProperties.getUri())
                .key(cosmosProperties.getPrimaryKey())
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();

        CosmosAsyncContainer feedContainer = client.getDatabase(cosmosProperties.getDatabaseName()).getContainer(cosmosProperties.getFeedContainer());
        CosmosAsyncContainer leaseContainer = client.getDatabase(cosmosProperties.getDatabaseName()).getContainer(cosmosProperties.getLeaseContainer());

        ChangeFeedProcessor processor = getChangeFeedProcessor(feedContainer,leaseContainer);

        processor.getEstimatedLag();
        return processor;
    }

    private ChangeFeedProcessor getChangeFeedProcessor(CosmosAsyncContainer feedContainer, CosmosAsyncContainer leaseContainer) {
        ChangeFeedProcessorOptions cfoptions = new ChangeFeedProcessorOptions();
        cfoptions.setFeedPollDelay(Duration.ofMillis(200));
        return new ChangeFeedProcessorBuilder()
                .options(cfoptions)
                .hostName(cosmosProperties.getHostName())
                .feedContainer(feedContainer)
                .leaseContainer(leaseContainer)
                .handleChanges(
                        (List<JsonNode> docs) ->{
                            for(JsonNode document: docs){
                                try{
                                    if(document.get("docType").asText().equals("ITEM"))
                                        handleChangeFeed(document);
                                }catch (Exception ex){
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                )
                .buildChangeFeedProcessor();
    }

    private void handleChangeFeed(JsonNode document){
        System.out.println(document.textValue());
        itemService.processDocument(document);
    }
}
