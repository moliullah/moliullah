package com.example.myadminappbatchone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myadminappbatchone.databinding.FragmentAdminLoginBinding;
import com.example.myadminappbatchone.viewmodels.LoginViewModel;

public class AdminLoginFragment extends Fragment {
    private FragmentAdminLoginBinding binding;
    private LoginViewModel loginViewModel;
    public AdminLoginFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding=FragmentAdminLoginBinding.inflate(inflater);
     loginViewModel=new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
     binding.loginBtnId.setOnClickListener(v->{
         authenticated();
     });
     loginViewModel.getAuthStateMutableLiveData().observe(getViewLifecycleOwner(), new Observer<LoginViewModel.AuthState>() {
         @Override
         public void onChanged(LoginViewModel.AuthState authState) {
             if (authState== LoginViewModel.AuthState.AUTHENTICATED){
                 Navigation.findNavController(container).navigate(R.id.alf_to_apf);
             }
         }
     });
     loginViewModel.getErrMsgLiveData().observe(getViewLifecycleOwner(),erMsg->{
         binding.errorMsgTV.setText(erMsg);
     });
        return binding.getRoot();
    }

    private void authenticated() {
        final String email=binding.emailET.getText().toString();
        final String pass=binding.passwordET.getText().toString();
        loginViewModel.login(email,pass);

    }

}