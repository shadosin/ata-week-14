package com.kenzie.serialization.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderSerializerTest {

    private static final String ORDER_ID = "order-12345";
    private static final String USER_ID = "user-a12b34";
    private static final BigDecimal TOTAL_COST = new BigDecimal(37.00);

    @Test
    void toJSON_failsToWrite_throwsException() {
        // GIVEN
        Order mockOrder = mock(Order.class);
        // A bit convoluted, but causes a stack overflow due to infinite recursion due to self-reference
        when(mockOrder.toString()).thenReturn(mockOrder.getClass().getName());

        // WHEN & THEN
        assertThrows(OrderSerializationException.class, () -> OrderSerializer.toJSON(mockOrder),
                "Expected a OrderSerializationException to be thrown when unable to serialize.");
    }

    @Test
    void toJSON_succeedsToWrite_returnsCorrectJson() throws JsonProcessingException {
        // GIVEN
        Order order = new Order();
        order.setOrderId(ORDER_ID);
        order.setUserId(USER_ID);
        order.setTotalCost(TOTAL_COST);

        // WHEN
        String json = OrderSerializer.toJSON(order);


        assertEquals(String.format("{\"userId\":\"%s\",\"orderId\":\"%s\",\"totalCost\":%s}",
                USER_ID, ORDER_ID, TOTAL_COST), json, "Expected order to be represented as JSON.");
    }

    @Test
    void toOrder_failsToRead_throwsException() {
        // GIVEN
        String invalidJson = String.format("{\"user\":\"%s\",\"orderId\":\"%s\",\"totalCost\":%s}",
                USER_ID, ORDER_ID, TOTAL_COST);

        // WHEN & THEN
        assertThrows(OrderSerializationException.class, () -> OrderSerializer.toOrder(invalidJson),
                "Expected a OrderSerializationException to be thrown when unable to deserialize.");
    }

    @Test
    void toOrder_allFieldsPresent_returnsOrder() throws JsonProcessingException {
        // GIVEN
        String json = String.format("{\"userId\":\"%s\",\"orderId\":\"%s\",\"totalCost\":%s}",
                USER_ID, ORDER_ID, TOTAL_COST);

        // WHEN
        Order order = OrderSerializer.toOrder(json);

        // THEN
        assertEquals(order.getOrderId(), ORDER_ID, "Expected orderId to match value in JSON.");
        assertEquals(order.getUserId(), USER_ID, "Expected userId to match value in JSON.");
        assertEquals(order.getTotalCost(), TOTAL_COST, "Expected totalCost to match value in JSON.");
    }

    @Test
    void toOrder_missingTotalCost_returnsOrder() throws JsonProcessingException {
        // GIVEN
        String json = String.format("{\"userId\":\"%s\",\"orderId\":\"%s\"}",
                USER_ID, ORDER_ID, TOTAL_COST);

        // WHEN
        Order order = OrderSerializer.toOrder(json);

        // THEN
        assertEquals(order.getOrderId(), ORDER_ID, "Expected orderId to match value in JSON.");
        assertEquals(order.getUserId(), USER_ID, "Expected userId to match value in JSON.");
        assertNull(order.getTotalCost(), "Expected totalCost to be null.");
    }
}
