package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.registration.RegisterModel;
import model.registration.Success;
import model.users.UserResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public interface Api {
    @GET("users")
    Call<UserResponse> getUsers();
    @GET("users")
    Call<UserResponse> getUsers(@Query("page") int page);
    @POST("register")
    Call<Success> register(@Body RegisterModel registrationModel);


    static Api createApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(Api.class);
    }
}
