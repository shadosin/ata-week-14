package com.kenzie.dynamodbscan.icecream;

import com.google.common.annotations.VisibleForTesting;
import com.kenzie.dynamodbscan.icecream.converter.RecipeConverter;
import com.kenzie.dynamodbscan.icecream.dao.CartonDAO;
import com.kenzie.dynamodbscan.icecream.dao.ReceiptDAO;
import com.kenzie.dynamodbscan.icecream.dao.RecipeDAO;
import com.kenzie.dynamodbscan.icecream.exception.CartonCreationFailedException;
import com.kenzie.dynamodbscan.icecream.exception.RecipeNotFoundException;
import com.kenzie.dynamodbscan.icecream.model.*;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 * Provides Ice Cream Parlor functionality.
 */
public class IceCreamParlorService {
    private final RecipeDAO recipeDao;
    private final ReceiptDAO receiptDao;
    private final CartonDAO cartonDao;
    private final IceCreamMaker iceCreamMaker;

    /**
     * Constructs service with the provided DAOs.
     * @param recipeDao the RecipeDao to use for accessing recipes
     * @param receiptDao the ReceiptDao to use for accessing receipts
     * @param cartonDao the CartonDao to use for accessing ice cream cartons
     * @param iceCreamMaker the machine that makes cartons of ice cream
     */
    @Inject
    public IceCreamParlorService(RecipeDAO recipeDao, ReceiptDAO receiptDao, CartonDAO cartonDao,
                                 IceCreamMaker iceCreamMaker) {
        this.recipeDao = recipeDao;
        this.receiptDao = receiptDao;
        this.cartonDao = cartonDao;
        this.iceCreamMaker = iceCreamMaker;
    }

    public Receipt order(String customerId, List<List<String>> sundaes) {
        List<Sundae> sundaeList = sundaes.stream().map(this::serveSundae).collect(Collectors.toList());
        return receiptDao.createCustomerReceipt(customerId, sundaeList);
    }

    /**
     * Creates and returns the sundae defined by the given ice cream flavors.
     * If a flavor is not found or we have none of that flavor left, the sundae
     * is returned, but without that flavor. (We'll only charge the customer for
     * the scoops they are returned)
     * @param flavorNames List of flavors to include in the sundae
     * @return The newly created Sundae
     */
    public Sundae serveSundae(List<String> flavorNames) {
        // This does the filtering out of any unknown flavors, so only
        // Cartons of known flavors will be returned.
        List<Carton> cartons = cartonDao.getCartonsByFlavorNames(flavorNames);

        cartons.removeIf(Carton::isEmpty);

        return buildSundae(cartons);
    }

    @VisibleForTesting
    Sundae buildSundae(List<Carton> cartons) {
        Sundae sundae = new Sundae();

        cartons.forEach(carton -> sundae.addScoop(carton.getFlavor()));

        return sundae;
    }

    /**
     * Prepares the specified flavors, creating 1 carton of each provided
     * flavor.
     *
     * A flavor name that doesn't correspond
     * to a known recipe will result in CartonCreationFailedException, and
     * no Cartons will be created.
     *
     * @param flavorNames List of names of flavors to create new batches of
     * @return the number of cartons produced by the ice cream maker
     */
    public int prepareFlavors(List<String> flavorNames) {
        List<Recipe> recipes = map(
                flavorNames,
                flavorName -> {
                    // trap the checked exception, RecipeNotFoundException, and
                    // wrap in a runtime exception because our lambda can't throw
                    // checked exceptions
                    try {
                        return recipeDao.getRecipe(flavorName);
                    } catch (RecipeNotFoundException e) {
                        throw new CartonCreationFailedException("Could not find recipe for " + flavorName, e);
                    }
                }
        );

        List<Queue<Ingredient>> ingredientQueues = map(
                recipes,
                RecipeConverter::fromRecipeToIngredientQueue
        );

        return makeIceCreamCartons(ingredientQueues);
    }

    @VisibleForTesting
    int makeIceCreamCartons(List<Queue<Ingredient>> ingredientQueues) {
        // don't change any of the lines that touch cartonsCreated.
        int cartonsCreated = 0;
        for (Queue<Ingredient> ingredients : ingredientQueues) {

            if (iceCreamMaker.prepareIceCreamCarton(() -> ingredients.poll())) {
                cartonsCreated++;
            }
        }

        return cartonsCreated;
    }

    /**
     * Converts input list of type T to a List of type R, where each entry in the return
     * value is the output of converter applied to each entry in input.
     *
     * (We will get to Java streams in a later lesson, at which point we won't need a helper method
     * like this.)
     */
    private <T, R> List<R> map(List<T> input, Function<T, R> converter) {
        return input.stream()
                .map(converter)
                .collect(Collectors.toList());
    }
}
