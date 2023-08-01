package com.kenzie.dynamodbscan.icecream;

import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbscan.icecream.dependency.DaggerIceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.dependency.IceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.model.Receipt;
import com.kenzie.dynamodbscan.icecream.model.Sundae;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class Phase1Test {
    private static IceCreamParlorServiceComponent DAGGER;
    private static final List<String> ONE_SCOOP = ImmutableList.of("Chocolate");
    private static final List<String> TWO_SCOOPS = ImmutableList.of("Chocolate", "Chocolate");

    private Sundae oneScoopSundae;
    private Sundae twoScoopSundae;

    private IceCreamParlorService service;
    private IceCreamParlorAdminService adminService;

    @BeforeEach
    private void setup() {
        DAGGER = DaggerIceCreamParlorServiceComponent.create();
        service = DAGGER.provideIceCreamParlorService();
        adminService = DAGGER.provideIceCreamParlorAdminService();

        oneScoopSundae = service.serveSundae(ONE_SCOOP);
        twoScoopSundae = service.serveSundae(TWO_SCOOPS);
    }

    @Test
    void getSalesForTimePeriod_noReceipts_noSales() {
        // WHEN - look into the future where there should be no reciepts
        BigDecimal result = adminService.getSalesForTimePeriod(ZonedDateTime.now().plus(7, ChronoUnit.DAYS),
                ZonedDateTime.now().plus(8, ChronoUnit.DAYS));

        // THEN
        assertTrue(BigDecimal.ZERO.compareTo(result) == 0, String.format("Expected sales to total $0.00 for" +
                "a time period with no receipts, but was: %s.", result));
    }

    @Test
    void getSalesForTimePeriod_oneReceipt_salesMatch() {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        Receipt receipt1 = service.order(customerId, ImmutableList.of(ONE_SCOOP));
        BigDecimal totalSales = oneScoopSundae.getPrice();

        // WHEN
        BigDecimal result = adminService.getSalesForTimePeriod(receipt1.getPurchaseDate(), receipt1.getPurchaseDate());

        // THEN
        assertTrue(result.compareTo(totalSales) >= 0, String.format("Expected sales to total at least the " +
                "value of the sundae created by this test: %s, but was: %s.", totalSales, result));
    }

    @Test
    void getSalesForTimePeriod_twoReceipts_salesMatch() {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        Receipt receipt1 = service.order(customerId, ImmutableList.of(ONE_SCOOP));
        Receipt receipt2 = service.order(customerId, ImmutableList.of(TWO_SCOOPS));

        BigDecimal totalSales = oneScoopSundae.getPrice().add(twoScoopSundae.getPrice());

        // WHEN
        BigDecimal result = adminService.getSalesForTimePeriod(receipt1.getPurchaseDate(), receipt2.getPurchaseDate());

        // THEN
        assertTrue(result.compareTo(totalSales) >= 0, String.format("Expected sales to total at least the " +
                "value of the two sundaes created by this test: %s, but was: %s.", totalSales, result));
    }
}
