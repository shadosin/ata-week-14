package com.kenzie.dynamodbscan.grocery;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "FoodItems")
public class FoodItem {

    private String id;
    private String expirationDate;
    private String foodName;
    private String foodSection;
    private Integer inventoryRemaining;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "expirationDate")
    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @DynamoDBAttribute(attributeName = "foodName")
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @DynamoDBAttribute(attributeName = "foodSection")
    public String getFoodSection() {
        return foodSection;
    }

    public void setFoodSection(String foodSection) {
        this.foodSection = foodSection;
    }

    @DynamoDBAttribute(attributeName = "inventoryRemaining")
    public Integer getInventoryRemaining() {
        return inventoryRemaining;
    }

    public void setInventoryRemaining(Integer inventoryRemaining) {
        this.inventoryRemaining = inventoryRemaining;
    }

}
