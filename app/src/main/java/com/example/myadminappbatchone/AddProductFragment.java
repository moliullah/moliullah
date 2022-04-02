package com.example.myadminappbatchone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.myadminappbatchone.databinding.FragmentAddProductBinding;
import com.example.myadminappbatchone.pickers.DatePickerDialogeFragment;
import com.example.myadminappbatchone.utils.Constants;
import com.example.myadminappbatchone.viewmodels.LoginViewModel;
import com.example.myadminappbatchone.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddProductFragment extends Fragment {
    private String TAG=ProductViewModel.class.getSimpleName();
    private FragmentAddProductBinding binding;
    private ProductViewModel productViewModel;
    private LoginViewModel loginViewModel;
    private List<String> categories = new ArrayList<>();
   // private String[]catagory={"Wallet","MoneyBag","samsungTV","Men Shoes"};
    String dateString,category;
    int year,month,day;
    private ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Uri photoUri = result.getData().getData();
                                if (photoUri != null) {
                                    binding.productImageView.setImageURI(photoUri);
                                }else {
                                    final Bitmap bitmap = (Bitmap) result.getData()
                                            .getExtras().get("data");
                                    binding.productImageView.setImageBitmap(bitmap);
                                }

                                binding.savaBtnId.setText("Please wait");
                                binding.savaBtnId.setEnabled(false);
                                //uploadImage(photoUri);
                            }
                        }
                    });
    public AddProductFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categories.add("Select Catogories");
        binding=FragmentAddProductBinding.inflate(inflater);
        productViewModel=new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        loginViewModel=new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding.dateBtnId.setOnClickListener(v->{
            new DatePickerDialogeFragment().show(getChildFragmentManager(),null);
        });
       getChildFragmentManager().setFragmentResultListener(Constants.REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (result.containsKey(Constants.DATE_KEY)){
                   // Log.e("TAG","OK");
                    dateString=result.getString(Constants.DATE_KEY);
                    Log.e("Date",dateString);
                    year=result.getInt(Constants.YEAR);
                    month=result.getInt(Constants.MONTH);
                    day=result.getInt(Constants.DAY);
                    //Log.e("Date",dateString);
                    binding.dateBtnId.setText(dateString);
                }
            }
        });

       binding.cameraBtnId.setOnClickListener(v->{
           Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           launcher.launch(takePictureIntent);
       });
       binding.galleryBtnId.setOnClickListener(v->{
           final Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType("image/*");
           launcher.launch(intent);
       });
    productViewModel.catagoryLiveData.observe(getViewLifecycleOwner(), catList-> {
           // Log.e(TAG,"");
            categories.addAll(catList);
            final ArrayAdapter<String>adapter=new ArrayAdapter<>
                    (getActivity(), android.R.layout.simple_dropdown_item_1line,categories);
            binding.spinnerId.setAdapter(adapter);
        });
        binding.spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position > 0) {
                    category = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

  /* final ArrayAdapter<String>adapter=new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_dropdown_item_1line,catagory);
        binding.spinnerId.setAdapter(adapter);*/
        loginViewModel.getAuthStateMutableLiveData().observe(getViewLifecycleOwner(), new Observer<LoginViewModel.AuthState>() {
            @Override
            public void onChanged(LoginViewModel.AuthState authState) {
                if (authState== LoginViewModel.AuthState.UNAUTHENTICATED){
                    Navigation.findNavController(container).navigate(R.id.apf_to_alf);
                }
            }
        });
        return binding.getRoot();
    }
}