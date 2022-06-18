package com.xyz.changefeed.application.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "azure")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CosmosProperties {
    private String uri;
    private String hostName;
    private String feedContainer;
    private String leaseContainer;
    private String primaryKey;
    private String databaseName;
}
