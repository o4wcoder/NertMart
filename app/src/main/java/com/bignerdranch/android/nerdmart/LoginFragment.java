package com.bignerdranch.android.nerdmart;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.bignerdranch.android.nerdmart.databinding.FragmentLoginBinding;

import rx.Subscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends NerdMartAbstractFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_login,
                container,
                false);

        fragmentLoginBinding.setLoginButtonClickListener(v -> {
            String username = fragmentLoginBinding.fragmentLoginUsername
                    .getText().toString();

            String password = fragmentLoginBinding.fragmentLoginPassword
                    .getText().toString();

            addSubscription(mNerdMartServiceManager
                    .authenticate(username, password)
                    .compose(loadingTransformer())
                    .subscribe(authenticated -> {

                        if (!authenticated) {
                            Toast.makeText(getActivity(),
                                    R.string.auth_failure_toast,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getActivity(),
                                R.string.auth_success_toast,
                                Toast.LENGTH_SHORT).show();
                        Intent intent = ProductsActivity
                                .newIntent(getActivity());
                        getActivity().finish();
                        startActivity(intent);

                    }));
        });
            return fragmentLoginBinding.getRoot();

    }

//    //Create on click method to handle click veneds on login button
//    @OnClick(R.id.fragment_login_login_button)
//    public void handleLoginClick() {
//        String username = mUsernameEditText.getText().toString();
//        String password = mPasswordEditText.getText().toString();
//        addSubscription(mNerdMartServiceManager
//                .authenticate(username, password)
//                .compose(loadingTransformer())
//                .subscribe(authenticated -> {
//                    Toast.makeText(getActivity(),
//                            R.string.auth_success_toast,
//                            Toast.LENGTH_SHORT).show();
//                    Intent intent = ProductsActivity.newIntent(getActivity());
//                    startActivity(intent);
//                    getActivity().finish();
//                }));
//    }
}
