package com.kenzie.dynamodbscan.grocery;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Collections;
import java.util.List;

/**
 * Provides access to the FoodItems table.
 */
public class FoodItemDao {
    private final DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of FoodItem objects from the data store.
     * @param mapper access to DynamoDB
     */
    public FoodItemDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Uses the scanPage() method to scan the FoodItems retrieving only the provided limit number of items.
     * @param exclusiveStartKey the item to start the scan at
     * @param limit the upper limit of items scanned by the scan
     * @return the list of FoodItems that is returned from the database
     */
    public List<FoodItem> scanFoodItemsWithLimit(final FoodItem exclusiveStartKey, final int limit) {
        //TODO: replace the below code
        return Collections.emptyList();
    }
}
