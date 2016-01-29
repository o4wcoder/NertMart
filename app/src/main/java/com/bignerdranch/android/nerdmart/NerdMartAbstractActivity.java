package com.bignerdranch.android.nerdmart;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.nerdmart.databinding.ActivityAbstractNerdmartBinding;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmart.viewmodel.NerdMartViewModel;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Class that the dependency injection framework will be configured to inject objects into
 *
 * Created by Chris Hare on 10/20/2015.
 */
public abstract class NerdMartAbstractActivity extends AppCompatActivity {

//    @Inject
  //  NerdMartServiceInterface mNerdMartServiceInterface;
    @Inject
    NerdMartServiceManager mNerdMartServiceManager;
    @Inject
    NerdMartViewModel mNerdMartViewModel;

    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeSubscription = new CompositeSubscription();
        NerdMartApplication.component(this).inject(this);
        //setContentView(R.layout.activity_abstract_nerdmart);
        ActivityAbstractNerdmartBinding binding = DataBindingUtil
                .setContentView(this,R.layout.activity_abstract_nerdmart);
        binding.setLogoutButtonClickListener(v -> signout());
        binding.setNerdMartViewModel(mNerdMartViewModel);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.activity_abstract_nerdmart_fragment_frame,
//                            getFragment())
                    .add(binding.activityNerdmartAbstractFragmentFrame.getId(),getFragment())
                    .commit();
        }
    }

    protected void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }
    private void signout() {
        addSubscription(mNerdMartServiceManager
                .signout()
                .subscribe(aBoolean -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }));
    }

    protected abstract Fragment getFragment();

    public void updateCartStatus(Cart cart) {
        mNerdMartViewModel.updateCartStatus(cart);
    }


}
