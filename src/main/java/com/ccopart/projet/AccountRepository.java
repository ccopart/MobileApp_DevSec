package com.ccopart.projet;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private AccountDao accountDao;
    private LiveData<List<Account>> allAccounts;
    public AccountRepository(Application application) {
        AccountDB database = AccountDB.getInstance(application);
        accountDao = database.accountDao();
        allAccounts = accountDao.getAllAccounts();
    }
    public void insert(Account account) {
        new InsertAccountAsyncTask(accountDao).execute(account);
    }
    public void update(Account account) {
        new UpdateAccountAsyncTask(accountDao).execute(account);
    }
    public void delete(Account account) {
        new DeleteAccountAsyncTask(accountDao).execute(account);
    }
    public void deleteAllAccounts() {
        new DeleteAllAccountsAsyncTask(accountDao).execute();
    }
    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }
    private static class InsertAccountAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;
        private InsertAccountAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }
    private static class UpdateAccountAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;
        private UpdateAccountAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.update(accounts[0]);
            return null;
        }
    }
    private static class DeleteAccountAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;
        private DeleteAccountAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.delete(accounts[0]);
            return null;
        }
    }
    private static class DeleteAllAccountsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AccountDao accountDao;
        private DeleteAllAccountsAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            accountDao.deleteAllAccounts();
            return null;
        }
    }
}
