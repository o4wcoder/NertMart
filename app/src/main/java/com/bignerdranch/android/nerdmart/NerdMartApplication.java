package com.bignerdranch.android.nerdmart;

import android.app.Application;
import android.content.Context;

import com.bignerdranch.android.nerdmart.inject.DaggerNerdMartComponent;
import com.bignerdranch.android.nerdmart.inject.NerdMartApplicationModule;
import com.bignerdranch.android.nerdmart.inject.NerdMartComponent;

import timber.log.Timber;

/**
 * Created by Chris Hare on 10/20/2015.
 */
public class NerdMartApplication extends Application {

    private NerdMartComponent martComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        buildComponentAndInject();

    }

    public void buildComponentAndInject() {
        martComponent = DaggerComponentInitializer.init(this);
    }

    public static NerdMartComponent component(Context context) {
        return ((NerdMartApplication)context.getApplicationContext()).getComponent();
    }

    public NerdMartComponent getComponent() {
        return martComponent;
    }
    public final static class DaggerComponentInitializer {
        public static NerdMartComponent init(NerdMartApplication app) {

            //DaggerNerdMartComponent class was generated by Dagger 2. It returns a working
            //implementation of the NerdMartComponent interface
            return DaggerNerdMartComponent.builder()
                    .nerdMartApplicationModule(new NerdMartApplicationModule(app))
                    .build();

        }
    }
}