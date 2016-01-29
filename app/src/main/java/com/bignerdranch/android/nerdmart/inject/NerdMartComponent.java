package com.bignerdranch.android.nerdmart.inject;

import com.bignerdranch.android.nerdmart.NerdMartAbstractActivity;
import com.bignerdranch.android.nerdmart.NerdMartAbstractFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * The @Component annotation marks the NerdMartComponent interface as a Component
 * in the Daggar 2 Framework. NerdMartApplicationModule will be a module used in
 * this component
 *
 * Created by Chris Hare on 10/20/2015.
 */
@Singleton
@Component(modules = {NerdMartApplicationModule.class})
public interface NerdMartComponent {
    void inject(NerdMartAbstractActivity activity);
    void inject(NerdMartAbstractFragment fragment);
}

