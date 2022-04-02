package com.example.myadminappbatchone.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    public enum AuthState{
        AUTHENTICATED,UNAUTHENTICATED
    }
    private MutableLiveData<AuthState>authStateMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String>errMsgLiveData=new MutableLiveData<>();
    private FirebaseAuth auth;
    private FirebaseUser user;

    public LoginViewModel() {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if (user==null){
            authStateMutableLiveData.postValue(AuthState.AUTHENTICATED);
        }else {
            authStateMutableLiveData.postValue(AuthState.UNAUTHENTICATED);
        }
    }

    public MutableLiveData<AuthState> getAuthStateMutableLiveData() {
        return authStateMutableLiveData;
    }

    public MutableLiveData<String> getErrMsgLiveData() {
        return errMsgLiveData;
    }

    public FirebaseUser getUser() {
        return user;
    }
    public void login(String email,String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
              authStateMutableLiveData.postValue(AuthState.AUTHENTICATED);
            }
        }).addOnFailureListener(e -> {
            errMsgLiveData.postValue(e.getLocalizedMessage());
        });
    }
}
