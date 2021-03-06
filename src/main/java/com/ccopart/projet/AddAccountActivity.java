package com.ccopart.projet;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddAccountActivity extends AppCompatActivity {


    public static final String EXTRA_ACCOUNT =
            "com.ccopart.projet.EXTRA_ACCOUNT";
    public static final String EXTRA_IBAN =
            "com.ccopart.projet.EXTRA_IBAN";
    public static final String EXTRA_AMOUNT =
            "com.ccopart.projet.EXTRA_AMOUNT";
    public static final String EXTRA_CURRENCY =
            "com.ccopart.projet.EXTRA_CURRENCY";


    private EditText editAccountName;
    private EditText editIban;
    private EditText editAmount;
    private EditText editCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        editAccountName = findViewById(R.id.edit_account_name);
        editIban = findViewById(R.id.edit_iban);
        editAmount = findViewById(R.id.edit_amount);
        editCurrency = findViewById(R.id.edit_currency);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Account");

        FloatingActionButton buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveAccount() {
        String account = editAccountName.getText().toString();
        String iban = editIban.getText().toString();
        String amount = editAmount.getText().toString();
        String currency = editCurrency.getText().toString();

        if (account.trim().isEmpty() || iban.trim().isEmpty() || amount.trim().isEmpty() || currency.trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ACCOUNT, account);
        data.putExtra(EXTRA_IBAN, iban);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_CURRENCY, currency);
        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_account_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_account:
                saveAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}