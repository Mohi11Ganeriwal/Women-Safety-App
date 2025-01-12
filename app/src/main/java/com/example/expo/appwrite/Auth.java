package com.example.expo.appwrite;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.appwrite.Client;
import io.appwrite.ID;
import io.appwrite.coroutines.CoroutineCallback;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.User;
import io.appwrite.services.Account;

public class Auth {
    public Account account;
    private String TAG = "Expo_Logs_AUTH";

    public User<Map<String, Object>> currentUser;
    public Auth(Client client){
        account = new Account(client);
    }

    public AppwriteResponse<Boolean> createAccount(String email, String password, String uname){
        CompletableFuture<AppwriteResponse<Boolean>> future = new CompletableFuture<>();

        try {
            account.create(
                    ID.Companion.unique(16),
                    email,
                    password,
                    uname,
                    new CoroutineCallback<>((result, error) -> {
                        if (error != null) {
                            Log.d(TAG, "createAccount: " + error);
                            return;
                        }

                        future.complete(new AppwriteResponse.Success<>(true));
                    })
            );
            return future.join();
        } catch (AppwriteException e){
            return new AppwriteResponse.Error<>(
                    e.getCode() != null ? e.getCode() : 0,
                    e.getMessage() != null ? e.getMessage() : "Login failed. Please check your credentials."
            );
        } catch (Exception e){
            return new AppwriteResponse.Error<>(
                    0,
                    "Something went wrong during account creation."
            );
        }
    }

    public AppwriteResponse<User<Map<String, Object>>> getUser(){
        CompletableFuture<AppwriteResponse<User<Map<String, Object>>>> future = new CompletableFuture<>();
        try {
            account.get(new CoroutineCallback<>((result, error) -> {
                if (error != null) {
                    Log.d(TAG, "getUser: " + error.getMessage());
                    return;
                }
                future.complete(new AppwriteResponse.Success<>(result));
            }));
            return future.join();
        } catch (AppwriteException e){
            return new AppwriteResponse.Error<>(
                    e.getCode() != null ? e.getCode() : 0,
                    e.getMessage() != null ? e.getMessage() : "Login failed. Please check your credentials."
            );
        } catch (Exception e){
            return new AppwriteResponse.Error<>(
                    0,
                    "Something went wrong while attempting to fetch the current user."
            );
        }
    }

    public AppwriteResponse<Boolean> login(String email, String password){
        CompletableFuture<AppwriteResponse<Boolean>> future = new CompletableFuture<>();

        try {
            account.createEmailPasswordSession(
                    email,
                    password,
                    new CoroutineCallback<>((result, error) -> {
                        if (error != null) {
                            Log.d(TAG, "createAccount: " + error);
                            return;
                        }

                        future.complete(new AppwriteResponse.Success<>(true));
                    })
            );
            return future.join();
        } catch (Exception e){
            return new AppwriteResponse.Error<>(
                    0,
                    "Something went wrong during login."
            );
        }
    }

}
