package com.kenzie.dynamodbscan.clothingstore;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ClothingStore")
public class Clothing {
    private String id;
    private String clothingType;
    private String color;
    private Double cost;
    private String currentStatus;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "clothingType")
    public String getClothingType() {
        return clothingType;
    }
  
    public void setClothingType(String clothingType) {
        this.clothingType = clothingType;
    }

    @DynamoDBAttribute(attributeName = "color")
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    @DynamoDBAttribute(attributeName = "cost")
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @DynamoDBAttribute(attributeName = "currentStatus")
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}