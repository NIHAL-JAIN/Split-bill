package com.example.almighty.navigationdrawer;




import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestApi {
    @Headers( "Content-Type: application/json" )
    @POST("api.php")
    Call<Model>getJson(@Body User user);
}
