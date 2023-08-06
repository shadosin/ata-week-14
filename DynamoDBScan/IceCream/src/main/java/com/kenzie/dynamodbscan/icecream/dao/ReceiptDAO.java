package com.kenzie.dynamodbscan.icecream.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.kenzie.dynamodbscan.icecream.converter.ZonedDateTimeConverter;
import com.kenzie.dynamodbscan.icecream.model.Receipt;
import com.kenzie.dynamodbscan.icecream.model.Sundae;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import javax.inject.Inject;

/**
 * Provides access to receipts in the datastore.
 */
public class ReceiptDAO {

    private final ZonedDateTimeConverter converter;
    private final DynamoDBMapper mapper;

    /**
     * Constructs a DAO with the given mapper.
     * @param mapper The DynamoDBMapper to use
     */
    @Inject
    public ReceiptDAO(DynamoDBMapper mapper) {
        this.mapper = mapper;
        this.converter = new ZonedDateTimeConverter();
    }

    /**
     * Generates and persists a customer receipt. The salesTotal is the sum of the price of the
     * provided sundaes.
     * @param customerId - the id of the ordering customer
     * @param sundaeList - the sundaes ordered by the customer
     * @return the receipt stored in the database
     */
    public Receipt createCustomerReceipt(String customerId, List<Sundae> sundaeList) {
        Receipt receipt = new Receipt();
        receipt.setCustomerId(customerId);
        receipt.setPurchaseDate(ZonedDateTime.now());
        receipt.setSalesTotal(sundaeList.stream().map(Sundae::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        receipt.setSundaes(sundaeList);
        mapper.save(receipt);
        return receipt;
    }

    /**
     * Calculates the total sales for the time period between fromDate and toDate (inclusive).
     * @param fromDate - the date (inclusive of) to start tracking sales
     * @param toDate - the date (inclusive of) to stop tracking sales
     * @return the total values of sundae sales for the requested time period
     */

    public BigDecimal getSalesBetweenDates(ZonedDateTime fromDate, ZonedDateTime toDate) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":startDate", new AttributeValue().withS(converter.convert(fromDate)));
        valueMap.put(":endDate", new AttributeValue().withS(converter.convert(toDate)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("purchaseDate between :startDate and :endDate")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Receipt> result = mapper.scan(Receipt.class, scanExpression);

        BigDecimal totalSales = BigDecimal.ZERO;
        for (Receipt receipt : result) {
            BigDecimal salesTotal = receipt.getSalesTotal();
            totalSales = totalSales.add(salesTotal);
        }

        return totalSales;
    }

    /**
     * Retrieves a subset of the receipts stored in the database. At least limit number of records will be retrieved
     * unless the end of the table has been reached, and instead only the remaining records will be returned. An
     * exclusive start key can be provided to start reading the table from this record, but excluding it from results.
     * @param limit - the number of Receipts to return
     * @param exclusiveStartKey - an optional value provided to designate the start of the read
     * @return a list of Receipts
     */
    public List<Receipt> getReceiptsPaginated(int limit, Receipt exclusiveStartKey) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(limit);

        if(exclusiveStartKey != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put("customerId", new AttributeValue()
                    .withS(exclusiveStartKey.getCustomerId()));
            startKey.put("purchaseDate", new AttributeValue()
                    .withS(converter.convert(exclusiveStartKey.getPurchaseDate())));
            scanExpression.setExclusiveStartKey(startKey);
        }
        ScanResultPage<Receipt> receiptPage = mapper.scanPage(Receipt.class, scanExpression);
        return receiptPage.getResults();
    }
}
