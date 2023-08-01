package com.kenzie.dynamodbscan.icecream.dao;

import com.google.common.collect.ImmutableList;
import com.kenzie.dynamodbscan.icecream.model.Carton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Provides access to cartons of ice cream in storage.
 */
public class CartonDAO {
    private static final List<String> CARTONS = ImmutableList.of("Vanilla", "Chocolate", "Strawberry");
    private static final List<String> EMPTY_CARTONS = ImmutableList.of();

    @Inject
    public CartonDAO() {
    }

    public List<Carton> getCartonsByFlavorNames(List<String> flavorNames) {
        return flavorNames.stream()
                .map(this::getNullableCarton)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Carton getNullableCarton(String name) {
        if (CARTONS.contains(name)) {
            return Carton.getCarton(name);
        } else if (EMPTY_CARTONS.contains(name)) {
            return Carton.getEmptyCarton(name);
        }
        return null;
    }


}
