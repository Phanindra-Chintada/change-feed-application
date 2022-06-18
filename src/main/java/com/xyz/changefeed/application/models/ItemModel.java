package com.xyz.changefeed.application.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(builder = ItemModel.ItemModelBuilder.class)
public class ItemModel {
    @Id
    @NotNull
    @JsonProperty("id")
    private String id;

    @NotNull
    @JsonProperty("ItemType")
    private String itemType;

    @NotNull
    @JsonProperty("docType")
    private String docType;

    @NotNull
    @JsonProperty("quantity")
    private int quantity;

    @NotNull
    @JsonProperty("location")
    private String location;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ItemModelBuilder{}

}
