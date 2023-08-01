package com.kenzie.dynamodbscan.icecream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbscan.icecream.converter.ZonedDateTimeConverter;
import com.kenzie.dynamodbscan.icecream.dependency.DaggerIceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.dependency.IceCreamParlorServiceComponent;
import com.kenzie.dynamodbscan.icecream.model.Receipt;
import com.kenzie.dynamodbscan.icecream.model.Sundae;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Phase3Test {
    private static final
    IceCreamParlorServiceComponent DAGGER = DaggerIceCreamParlorServiceComponent.create();
    private static final List<String> ONE_SCOOP = ImmutableList.of("Chocolate");
    private static final List<String> TWO_SCOOPS = ImmutableList.of("Chocolate", "Chocolate");
    private static final String RECEIPTS_TABLE_NAME = "DynamoDBScan-Receipts";

    private Sundae oneScoopSundae;
    private Sundae twoScoopSundae;

    private final DynamoDB client = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_EAST_1).build());
    private final Table receiptsTable = client.getTable(RECEIPTS_TABLE_NAME);
    private final ZonedDateTimeConverter dateTimeConverter = new ZonedDateTimeConverter();
    private final ObjectMapper mapper = new ObjectMapper();
    private IceCreamParlorService service;


    @BeforeEach
    private void setup() {
        service = DAGGER.provideIceCreamParlorService();

        oneScoopSundae = service.serveSundae(ONE_SCOOP);
        twoScoopSundae = service.serveSundae(TWO_SCOOPS);
    }

    @Test
    void order_oneSundae_receiptContainsSundae() {
        // GIVEN - at least 2 receipts in table
        String customerId = UUID.randomUUID().toString();

        // WHEN
        Receipt receipt = service.order(customerId, ImmutableList.of(ONE_SCOOP));

        // THEN
        List<Sundae> sundaes = receipt.getSundaes();
        assertNotNull(sundaes, "Expected a non-null list of sundaes to be set in a receipt.");
        assertEquals(sundaes.size(), 1,
                "Order contained 1 sundae, receipt contained: " + sundaes.size());
        assertEquals(sundaes.size(), 1, String.format(
                "Order contained sundae %s, receipt contained : %s", receipt.getSundaes().get(0), sundaes.get(0)));
    }

    @Test
    void order_twoSundaes_receiptContainsSundaes() {
        // GIVEN - at least 2 receipts in table
        String customerId = UUID.randomUUID().toString();

        // WHEN
        Receipt receipt = service.order(customerId, ImmutableList.of(ONE_SCOOP, TWO_SCOOPS));

        // THEN
        List<Sundae> sundaes = receipt.getSundaes();
        assertNotNull(sundaes, "Expected a non-null list of sundaes to be set in a receipt.");
        assertEquals(sundaes.size(), 2,
                "Order contained 2 sundaes, receipt contained: " + sundaes.size());
        assertTrue(sundaes.contains(receipt.getSundaes().get(0)), String.format(
                "Order contained sundae %s, but receipt sundaes did not: %s", receipt.getSundaes().get(0), sundaes));
        assertTrue(sundaes.contains(receipt.getSundaes().get(1)), String.format(
                "Order contained sundae %s, but receipt sundaes did not: %s", receipt.getSundaes().get(1), sundaes));
    }

    @Test
    void order_oneSundae_itemContainsCorrectJson() throws Exception {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        List<Sundae> sundaeList = ImmutableList.of(service.serveSundae(TWO_SCOOPS));

        // WHEN
        Receipt receipt = service.order(customerId, ImmutableList.of(TWO_SCOOPS));

        // THEN
        Item item = receiptsTable.getItem(new KeyAttribute("customerId", customerId),
                new KeyAttribute("purchaseDate", dateTimeConverter.convert(receipt.getPurchaseDate())));
        String sundaesDDB = item.getJSON("sundaes");
        assertNotNull(sundaesDDB, "Expected list of sundaes to be stored in the table");
        String sundaesJSON = mapper.readValue(sundaesDDB, String.class);
        List<Sundae> restoredSundaes =
                mapper.readValue(sundaesJSON, mapper.getTypeFactory().constructCollectionType(List.class, Sundae.class));
        assertTrue(restoredSundaes.containsAll(sundaeList),
                "Expected the table to store JSON for a single sundae in the receipt.");
        assertTrue(sundaeList.containsAll(restoredSundaes),
                "Expected the table to store JSON only for the sundae in the receipt.");
    }

    @Test
    void order_twoSundaes_itemContainsCorrectJson() throws Exception {
        // GIVEN
        String customerId = UUID.randomUUID().toString();
        List<Sundae> sundaeList = ImmutableList.of(service.serveSundae(ONE_SCOOP), service.serveSundae(TWO_SCOOPS));
        String sundaeListJson = mapper.writeValueAsString(sundaeList);

        // WHEN
        Receipt receipt = service.order(customerId, ImmutableList.of(ONE_SCOOP, TWO_SCOOPS));

        // THEN
        Item item = receiptsTable.getItem(new KeyAttribute("customerId", customerId),
                new KeyAttribute("purchaseDate", dateTimeConverter.convert(receipt.getPurchaseDate())));
        String sundaesDDB = item.getJSON("sundaes");
        assertNotNull(sundaesDDB, "Expected list of sundaes to be stored in the table");
        String sundaesJSON = mapper.readValue(sundaesDDB, String.class);
        List<Sundae> restoredSundaes =
                mapper.readValue(sundaesJSON, mapper.getTypeFactory().constructCollectionType(List.class, Sundae.class));
        assertTrue(restoredSundaes.containsAll(sundaeList),
                "Expected the table to store JSON for all sundaes in the receipt.");
        assertTrue(sundaeList.containsAll(restoredSundaes),
                "Expected the table to store JSON only for sundaes in the receipt.");
    }

}
