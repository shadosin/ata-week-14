package com.kenzie.dynamodbscan.grocery;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(limit);

      if(exclusiveStartKey != null){
          Map<String, AttributeValue> startKeyMap = new HashMap<>();
          startKeyMap.put("id", new AttributeValue().withS(exclusiveStartKey.getId()));
          scanExpression.setExclusiveStartKey(startKeyMap);
      }
      ScanResultPage<FoodItem> foodPage = mapper.scanPage(FoodItem.class, scanExpression);
        return foodPage.getResults();
    }
}
