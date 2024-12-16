package program;

import api.ApiClient;
import model.users.User;
import model.users.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ApiClient apiClient = new ApiClient();

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        syncRequest(users);
        System.out.println(users.size());
        asyncTask(users);
        System.out.println(users.size());
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(users.size());

    }

    private static void syncRequest(List<User> list) {
        try {
            var response = apiClient.getApi().getUsers(2).execute();
            if (response.isSuccessful() && response.body() != null) {
                list.addAll(response.body().getData());
            } else System.out.println(response.code() + " : " + response.message());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void asyncTask(List<User> list) {
        apiClient.getApi().getUsers()
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        try {
                            if (response.body() != null) {
                                list.addAll(response.body().getData());
                            }
                            else System.out.println(response.code() + " : " + response.message());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
    }
}
