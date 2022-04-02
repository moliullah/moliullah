package com.example.myadminappbatchone.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myadminappbatchone.utils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
   private final FirebaseFirestore db=FirebaseFirestore.getInstance();
   public MutableLiveData<List<String>>catagoryLiveData=new MutableLiveData<>();

    public ProductViewModel() {
        getAllCatagories();
    }

    public void getAllCatagories(){
       db.collection(Constants.DbCollection.COLLECTION_CATEGORY).addSnapshotListener((value, error) -> {
           if (error !=null)return;
           final List<String>items=new ArrayList<>();
           for (DocumentSnapshot doc : value.getDocuments()){
               items.add(doc.get("name",String.class));
           }
           catagoryLiveData.postValue(items);
       });
   }
}
