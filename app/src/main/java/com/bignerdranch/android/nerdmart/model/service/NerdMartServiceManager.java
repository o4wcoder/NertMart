package com.bignerdranch.android.nerdmart.model.service;

import com.bignerdranch.android.nerdmart.model.DataStore;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.Product;
import com.bignerdranch.android.nerdmartservice.service.payload.User;

import java.util.UUID;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Chris Hare on 10/20/2015.
 */
public class NerdMartServiceManager {

    private NerdMartServiceInterface mServiceInterface;
    private DataStore mDataStore;
    private final Transformer<Observable, Observable> mSchedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

    public NerdMartServiceManager(NerdMartServiceInterface serviceInterface,
                                  DataStore dataStore) {
        mServiceInterface = serviceInterface;
        mDataStore = dataStore;
    }

    @SuppressWarnings("unchecked")
    private <T> Transformer<T, T> applySchedulers() {
        return (Transformer<T, T>) mSchedulersTransformer;
    }
    /**
     * Authentication. Will true of false based on if the API returned a User object or null for the user
     * @param username
     * @param password
     * @return
     */
    public Observable<Boolean> authenticate(String username, String password) {

//        return martServiceInterface.authenticate(username,password)
//                .map(new Func1<User, Boolean>() {
//                    @Override
//                    public Boolean call(User user) {
//                        return user != null;
//                    }
//                });
        //Redo with Java 8 and Lambda expressions using anonymous function
        return mServiceInterface.authenticate(username,password)
                .doOnNext(mDataStore::setCachedUser) //Cache the user on successful authentication
                .map(user -> user != null)
//                .subscribeOn(Schedulers.io()) //work off of main thread
//                .observeOn(AndroidSchedulers.mainThread()); //listen on main thread
                  .compose(applySchedulers());
    }

    private Observable<UUID> getToken() {

        Timber.e("getToken(): have cachedAuthToken() " + mDataStore.getCachedAuthToken().toString());
        return Observable.just(mDataStore.getCachedAuthToken());
    }
    public Observable<Product> getProducts() {

        return getToken().flatMap(mServiceInterface::requestProducts)
                .doOnNext(mDataStore::setCachedProducts)
                .flatMap(Observable::from)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
                 .compose(applySchedulers());
    }

    public Observable<Cart> getCart() {
        return getToken().flatMap(mServiceInterface::fetchUserCart)
                .doOnNext(mDataStore::setCachedCart)
                .compose(applySchedulers());
    }
    public Observable<Boolean> postProductToCart(final Product product) {
        Timber.e("postProductToCart(): with product " + product.getTitle());
        return getToken()
                .flatMap(uuid -> mServiceInterface.addProductToCart(uuid, product))
                .compose(applySchedulers());
    }
    public Observable<Boolean> signout() {
        mDataStore.clearCache();
        return mServiceInterface.signout();
    }
}
