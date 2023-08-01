package com.kenzie.dynamodbscan.icecream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbscan.icecream.dependency.DaggerIceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.dependency.IceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class Phase0Test {
    private static final IceCreamParlorServiceComponent DAGGER = DaggerIceCreamParlorServiceComponent.create();

    private static final String TABLE_NAME_PREFIX = "DynamoDBScan-";
    private static final String RECEIPTS_TABLE_NAME = TABLE_NAME_PREFIX + "Receipts";

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1).build());
    private IceCreamParlorService service;

    @BeforeEach
    private void setup() {
        service = DAGGER.provideIceCreamParlorService();
    }

    @ParameterizedTest
    @ValueSource(strings = {RECEIPTS_TABLE_NAME})
    void expectedTable_exists(String tableName) {
        assertTableExists(tableName);
    }

    private void assertTableExists(String tableName) {
        for (Table table : client.listTables()) {
            if (table.getTableName().equals(tableName)) {
                return;
            }
        }
        fail(String.format("Did not find expected table, '%s'", tableName));
    }

    @Test
    void orderSundae_succeeds() {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        List<String> sundae = ImmutableList.of("Chocolate", "Chocolate", "Strawberry");

        // WHEN
        Receipt receipt = service.order(customerId, ImmutableList.of(sundae));

        // THEN
        assertEquals(receipt.getCustomerId(), customerId, "Expected receipt to contain ordering customer.");
        assertTrue(receipt.getSalesTotal().compareTo(new BigDecimal(5.50)) == 0,
                "Expected a 3-scoop sundae to cost $5.50");
    }
}