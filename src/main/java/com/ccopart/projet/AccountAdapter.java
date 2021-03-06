package com.ccopart.projet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {
    private List<Account> accounts = new ArrayList<>();

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);
        return new AccountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account currentAccount = accounts.get(position);
        holder.textViewAccountName.setText(currentAccount.getAccountName());
        holder.textViewAmount.setText(String.valueOf(currentAccount.getAmount()));
        holder.textViewCurrency.setText(String.valueOf(currentAccount.getCurrency()));
        holder.textViewIban.setText(String.valueOf(currentAccount.getIban()));
        holder.textViewId.setText(String.valueOf(currentAccount.getId()));
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    class AccountHolder extends RecyclerView.ViewHolder {
        private TextView textViewAccountName;
        private TextView textViewIban;
        private TextView textViewCurrency;
        private TextView textViewId;
        private TextView textViewAmount;

        public AccountHolder(View itemView) {
            super(itemView);
            textViewAccountName = itemView.findViewById(R.id.text_view_account_name);
            textViewIban = itemView.findViewById(R.id.text_view_iban);
            textViewCurrency = itemView.findViewById(R.id.text_view_currency);
            textViewId = itemView.findViewById(R.id.text_view_id);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
        }
    }

}
