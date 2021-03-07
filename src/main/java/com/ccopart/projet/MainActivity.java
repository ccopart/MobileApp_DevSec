package com.ccopart.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_ACCOUNT_REQUEST = 1;
    private AccountViewModel accountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AccountAdapter adapter = new AccountAdapter();
        recyclerView.setAdapter(adapter);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        accountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });

        FloatingActionButton buttonRefresh = findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = new String(Base64.decode(getString(R.string.api_url),Base64.DEFAULT));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonApi jsonApi = retrofit.create(JsonApi.class);
                Call<List<Account>> call = jsonApi.getAccounts();

                call.enqueue(new Callback<List<Account>>() {
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        accountViewModel.deleteAllAccounts();
                        List<Account> accountList = response.body();



                        for (Account account : accountList){
                            Account accounttemp = new Account(account.getId(), account.getAccountName(), account.getAmount(), account.getIban(), account.getCurrency());
                            accountViewModel.insert(accounttemp);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Something went wrong, check if you're connected to internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        FloatingActionButton buttonAdd = findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection() == true){
                    Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
                    startActivityForResult(intent, ADD_ACCOUNT_REQUEST);
                }
                else{
                    Toast.makeText(MainActivity.this, "Something went wrong, check if you're connected to internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createAccount(Account account){
        String url = new String(Base64.decode(getString(R.string.api_url),Base64.DEFAULT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<Account> call = jsonApi.createAccount(account);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "An error has occurred !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Account accountResponse = response.body();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error : No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            String account_name = data.getStringExtra(AddAccountActivity.EXTRA_ACCOUNT);
            String iban = data.getStringExtra(AddAccountActivity.EXTRA_IBAN);
            String currency = data.getStringExtra(AddAccountActivity.EXTRA_CURRENCY);
            String amount = data.getStringExtra(AddAccountActivity.EXTRA_AMOUNT);
            Account account = new Account(account_name,Double.parseDouble(amount), iban, currency);
            createAccount(account);
            accountViewModel.insert(account);
            Toast.makeText(this, "New account created", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkConnection(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }
}