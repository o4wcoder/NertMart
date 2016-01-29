package com.bignerdranch.android.nerdmart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import timber.log.Timber;

public class ProductsActivity extends NerdMartAbstractActivity{

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_products);
//
//        Timber.i("injected: " + mNerdMartServiceInterface);
//    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context,ProductsActivity.class);
        return intent;
    }

    @Override
    protected Fragment getFragment() {
        return new ProductsFragment();
    }

}
