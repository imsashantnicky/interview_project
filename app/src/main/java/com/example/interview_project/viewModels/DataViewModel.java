package com.example.interview_project.viewModels;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.interview_project.apis.ApiServices;
import com.example.interview_project.apis.RetroInstance;
import com.example.interview_project.models.ResponseDataModel;
import com.example.interview_project.models.UserDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewModel extends ViewModel {
    private MutableLiveData<List<UserDataModel>> userDataList;
    private MutableLiveData<Boolean> isLoading;

    public DataViewModel() {
        userDataList = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<List<UserDataModel>> getUserDataListObserver() {
        return userDataList;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void getUsersData(int page) {
        isLoading.postValue(true);

        ApiServices apiServices = RetroInstance.getRetrofit().create(ApiServices.class);
        Call<ResponseDataModel> call = apiServices.getUsers(page);
        call.enqueue(new Callback<ResponseDataModel>() {
            @Override
            public void onResponse(Call<ResponseDataModel> call, Response<ResponseDataModel> response) {
                isLoading.postValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("apiResponse", response.body().toString());
                    ResponseDataModel responseDataModel = response.body();
                    if (responseDataModel != null && responseDataModel.getUsers() != null) {
                        userDataList.postValue(responseDataModel.getUsers());
                    } else {
                        Log.e("API Error", "Response data or users list is null");
                        userDataList.postValue(null);
                    }
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null");
                    userDataList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseDataModel> call, Throwable t) {
                isLoading.postValue(false);
                userDataList.postValue(null);
                Log.e("Error :", t.getMessage());
            }
        });
    }
}
