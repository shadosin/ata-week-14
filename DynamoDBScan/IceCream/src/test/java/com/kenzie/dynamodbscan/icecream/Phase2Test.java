package com.kenzie.dynamodbscan.icecream;

import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbscan.icecream.dependency.DaggerIceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.dependency.IceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.model.Receipt;
import com.kenzie.dynamodbscan.icecream.model.Sundae;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Phase2Test {
    private static final IceCreamParlorServiceComponent DAGGER = DaggerIceCreamParlorServiceComponent.create();
    private static final List<String> ONE_SCOOP = ImmutableList.of("Chocolate");
    private static final List<String> TWO_SCOOPS = ImmutableList.of("Chocolate", "Chocolate");

    private Sundae oneScoopSundae;
    private Sundae twoScoopSundae;

    private IceCreamParlorService service;
    private IceCreamParlorAdminService adminService;

    @BeforeEach
    private void setup() {
        service = DAGGER.provideIceCreamParlorService();
        adminService = DAGGER.provideIceCreamParlorAdminService();

        oneScoopSundae = service.serveSundae(ONE_SCOOP);
        twoScoopSundae = service.serveSundae(TWO_SCOOPS);
    }

    @Test
    void getReceipts_firstPage_returnsListSizeEqualToLimit() {
        // GIVEN - at least 2 receipts in table
        String customerId = UUID.randomUUID().toString();
        service.order(customerId, ImmutableList.of(ONE_SCOOP));
        service.order(customerId, ImmutableList.of(ONE_SCOOP));
        int limit = 2;

        // WHEN
        List<Receipt> result = adminService.getCustomerReceipts(limit, null);

        // THEN
        assertNotNull(result, "Expected a non-null list of receipts");
        assertEquals(result.size(), 2,
                "Expected result list to have size equal to the requested limit: " + limit);
    }

    @Test
    void getReceipts_allResults_resultsContainNewlyCreatedReceipts() {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        Receipt receipt1 = service.order(customerId, ImmutableList.of(ONE_SCOOP));
        Receipt receipt2 = service.order(customerId, ImmutableList.of(TWO_SCOOPS));
        int limit = 5;

        // WHEN
        List<Receipt> totalResults = new ArrayList<>();
        List<Receipt> tempResults = adminService.getCustomerReceipts(limit, null);

        while(tempResults.size() == limit) {
            totalResults.addAll(tempResults);
            tempResults = adminService.getCustomerReceipts(limit, tempResults.get(limit - 1));
        }
        totalResults.addAll(tempResults);

        // THEN
        assertTrue(totalResults.contains(receipt1), "Expected results to contain receipt: " + receipt1);
        assertTrue(totalResults.contains(receipt2), "Expected results to contain receipt: " + receipt2);
    }
}
