package com.kenzie.dynamodbscan.icecream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.dynamodbscan.icecream.utilities.DynamoDbClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Provides DynamoDBMapper instance to DAO classes.
 */
@Module
public class DAOModule {

    /**
     * Creates and returns a DynamoDBMapper instance for the appropriate region.
     * @return a DynamoDBMapper
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_1));
    }
}
