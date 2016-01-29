package com.bignerdranch.android.nerdmart.inject;

import android.content.Context;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmart.viewmodel.NerdMartViewModel;
import com.bignerdranch.android.nerdmartservice.service.NerdMartService;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This will define how the depenency injection framework will go about creating
 * the individual instances of objects in our application
 *
 * Created by Chris Hare on 10/20/2015.
 */
@Module
public class NerdMartApplicationModule {
    private Context mApplicationContext;

    public NerdMartApplicationModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    //Singleton annotation idicates you would like DataStore to be the sae instance each time it is
    //requested from Dagger 2.
    @Provides
    @Singleton
    DataStore providesDataStore() {
        return new DataStore();
    }
    @Provides
    NerdMartServiceInterface providesNerdMartServiceInterface() {
        return new NerdMartService();
    }

    @Provides
    NerdMartServiceManager providesNerdMartServiceManager(NerdMartServiceInterface serviceInterface,
                                                          DataStore dataStore) {
        return new NerdMartServiceManager(serviceInterface,dataStore);
    }

    @Provides
    NerdMartViewModel providesNerdMartViewModel(DataStore dataStore) {
        return new NerdMartViewModel(mApplicationContext,
                dataStore.getCachedCart(),
                dataStore.getCachedUser());
    }
}
