package com.example.interview_project.apis;
import com.example.interview_project.models.ResponseDataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("users")
    Call<ResponseDataModel> getUsers(@Query("page") int page);

}
