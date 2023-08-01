package com.kenzie.dynamodbscan.clothingstore;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClothingApp {

    /**
     * Test your method on a real database connection. Should print the following: [SH_1248, SH_2345, SH_4872, SH_7845]
     * @param args main method arguments
     */
    public static void main(String[] args) {
        //GIVEN
        DynamoDBMapper mapper = new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
        String clothingType = "shoes";

        //WHEN
        ClothingDao clothingDao = new ClothingDao(mapper);
        List<Clothing> clothingList = clothingDao.scanByClothingType(clothingType);

        //THEN
        System.out.println("Scan result retrieves the 4 clothing items with id's SH_1248, SH_2345, SH_4872, and " +
                "SH_7845: " + clothingList.stream().map(Clothing::getId).collect(Collectors.toList()));
    }
}
