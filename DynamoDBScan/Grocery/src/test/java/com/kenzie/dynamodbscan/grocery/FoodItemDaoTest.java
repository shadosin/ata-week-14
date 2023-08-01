package com.kenzie.dynamodbscan.grocery;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FoodItemDaoTest {
    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private ScanResultPage scanResult;

    @InjectMocks
    private FoodItemDao foodItemDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void scanFoodItemsWithLimit_exclusiveStartKeyIsNull_returnsFirstTwoFoodItems() {
        //GIVEN
        FoodItem foodItem1 = new FoodItem();
        foodItem1.setId("G0216");
        foodItem1.setExpirationDate("03/18/2020");
        foodItem1.setFoodName("green beans");
        foodItem1.setFoodSection("produce");
        foodItem1.setInventoryRemaining(18);

        FoodItem foodItem2 = new FoodItem();
        foodItem2.setId("A014");
        foodItem2.setExpirationDate("12/17/2019");
        foodItem2.setFoodName("apples");
        foodItem2.setFoodSection("produce");
        foodItem2.setInventoryRemaining(60);

        List<FoodItem> foodItemList = ImmutableList.of(foodItem1, foodItem2);

        FoodItem exclusiveStartKey = null;
        int limit = 2;

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(mapper.scanPage(eq(FoodItem.class), any())).thenReturn(scanResult);
        when(scanResult.getResults()).thenReturn(foodItemList);

        //WHEN
        List<FoodItem> scanList = foodItemDao.scanFoodItemsWithLimit(exclusiveStartKey, limit);

        //THEN
        verify(mapper).scanPage(eq(FoodItem.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();


        assertEquals(null, scanExpression.getExclusiveStartKey(), "The exclusiveStartKey should" +
                "be null.");
        assertEquals(limit, scanExpression.getLimit(), "The limit should be: " + limit);
        assertEquals(foodItemList, scanList, "Expected method to return the results of the scan");
    }

    @Test
    public void scanFoodItemsWithLimit_exclusiveStartKeyNotNull_returnsNextOneFoodItem() {
        //GIVEN
        FoodItem foodItem = new FoodItem();
        foodItem.setId("S623");
        foodItem.setExpirationDate("02/06/2020");
        foodItem.setFoodName("steak");
        foodItem.setFoodSection("meat");
        foodItem.setInventoryRemaining(63);

        List<FoodItem> foodItemList = ImmutableList.of(foodItem);

        FoodItem exclusiveStartKey = new FoodItem();
        exclusiveStartKey.setId("123");

        int limit = 2;

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(mapper.scanPage(eq(FoodItem.class), any())).thenReturn(scanResult);
        when(scanResult.getResults()).thenReturn(foodItemList);

        //WHEN
        List<FoodItem> scanList = foodItemDao.scanFoodItemsWithLimit(exclusiveStartKey, limit);

        //THEN
        verify(mapper).scanPage(eq(FoodItem.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
        Map<String, AttributeValue> valueMap = scanExpression.getExclusiveStartKey();

        assertNotNull(valueMap, "Expected the exclusive start key map to be set in the scan expression");
        assertEquals(valueMap.size(), 1, "Expected only id to be set in exclusive start key map");
        Map.Entry<String, AttributeValue> entry = valueMap.entrySet().iterator().next();
        assertEquals(entry.getKey(), "id", "Expected id to be set in exclusive start key map");
        assertEquals(entry.getValue().getS(), "123", "Expected id value to be set to '123' " +
                "in exclusive start key map");

        assertEquals(limit, scanExpression.getLimit(), "The limit should be: " + limit);

        assertEquals(foodItemList, scanList, "Expected method to return the results of the scan");
    }
}
