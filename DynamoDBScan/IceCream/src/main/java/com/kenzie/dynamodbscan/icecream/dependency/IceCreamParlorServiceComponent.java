package com.kenzie.dynamodbscan.icecream.dependency;

import com.kenzie.dynamodbscan.icecream.DAOModule;
import com.kenzie.dynamodbscan.icecream.IceCreamParlorAdminService;
import com.kenzie.dynamodbscan.icecream.IceCreamParlorService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DAOModule.class)
public interface IceCreamParlorServiceComponent {
    IceCreamParlorAdminService provideIceCreamParlorAdminService();
    IceCreamParlorService provideIceCreamParlorService();
}
