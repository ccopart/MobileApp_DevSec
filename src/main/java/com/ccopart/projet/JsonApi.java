package com.ccopart.projet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonApi {

    @GET("accounts")
    Call<List<Account>> getAccounts();

    @POST("accounts")
    Call<Account> createAccount(@Body Account account);
}
