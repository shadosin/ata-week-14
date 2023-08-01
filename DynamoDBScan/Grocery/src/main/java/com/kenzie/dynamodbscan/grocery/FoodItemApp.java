package com.kenzie.dynamodbscan.grocery;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.List;
import java.util.stream.Collectors;

public class FoodItemApp {

    /**
     * Test your methods on a real database connection. This makes three scan calls to show the effects of using the
     * LastEvaluatedKey.
     * @param args main method arguments
     */
    public static void main(String[] args) {
        //GIVEN (1st scan call)
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        FoodItemDao foodItemDao = new FoodItemDao(mapper);

        FoodItem exclusiveStartKey = null;
        int limit = 2;

        System.out.println("Verify that the following food items with ids were retrieved: G0216, A014, " +
                "S426, S623, G129, and C912");

        //WHEN (1st scan call)
        List<FoodItem> foodItemList = foodItemDao.scanFoodItemsWithLimit(exclusiveStartKey,
                limit);

        //THEN (1st scan call results)
        System.out.println("The first scan call when limit = 2 retrieves the 2 food items with ids: " +
                getFoodItemIds(foodItemList));


        //GIVEN (last item returned by previous call)
        exclusiveStartKey = foodItemList.get(foodItemList.size() - 1);

        //WHEN (2nd scan call)
        foodItemList = foodItemDao.scanFoodItemsWithLimit(exclusiveStartKey,
                limit);


        //THEN (2nd scan call results)
        System.out.println("The second scan call when limit = 2 retrieves the food item with ids: " +
                getFoodItemIds(foodItemList));


        //GIVEN (3rd scan call)
        exclusiveStartKey = foodItemList.get(foodItemList.size() - 1);

        //WHEN (last item returned by previous call)
        foodItemList = foodItemDao.scanFoodItemsWithLimit(exclusiveStartKey,
                limit);

        //THEN (3rd scan call results)
        System.out.println("The third scan call when limit = 2 retrieves the food item with ids: " +
                getFoodItemIds(foodItemList));
    }

    private static List<String> getFoodItemIds(List<FoodItem> foodItemList) {
        return foodItemList.stream().map(FoodItem::getId).collect(Collectors.toList());
    }
}
