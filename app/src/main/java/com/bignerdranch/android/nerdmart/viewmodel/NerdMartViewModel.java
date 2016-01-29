package com.bignerdranch.android.nerdmart.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.bignerdranch.android.nerdmart.BR;
import com.bignerdranch.android.nerdmart.R;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;
import com.bignerdranch.android.nerdmartservice.service.payload.User;

import timber.log.Timber;

/**
 * Created by Chris Hare on 10/21/2015.
 */
public class NerdMartViewModel extends BaseObservable {

    private Context mContext;
    private Cart mCart;
    private User mUser;
    public NerdMartViewModel(Context context, Cart cart, User user) {
        mContext = context;
        mCart = cart;
        mUser = user;
    }
    public String formatCartItemsDisplay() {
        int numItems = 0;
        Timber.e("formatCartItemsDisplay(): Inside");
        if (mCart != null && mCart.getProducts() != null) {
            Timber.e("Number of items in car " + mCart.getProducts().size());
            numItems = mCart.getProducts().size();
        }
        return mContext.getResources().getQuantityString(R.plurals.cart,
                numItems,
                numItems);
    }
    public String getUserGreeting() {
        return mContext.getString(R.string.user_greeting, mUser.getName());
    }

    //The Bindable annotation will indicate a new field should be added to the BR class,
    //a binding reference file, to identify the field that can be marked as changed.
    @Bindable
    public String getCartDisplay() {
        return formatCartItemsDisplay();
    }

    public void updateCartStatus(Cart cart) {
        mCart = cart;
        Timber.e("updateCartSTatus: Inside");
        notifyPropertyChanged(BR.cartDisplay);
    }
}
