package ma.abdelkadous.ebanking_backend.services;

import ma.abdelkadous.ebanking_backend.dtos.*;
import ma.abdelkadous.ebanking_backend.exceptions.BalanceNotSufficentException;
import ma.abdelkadous.ebanking_backend.exceptions.BankAccountNotFoundException;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface IBankAccountService {


    CurrentBankAccountDTO saveCurrentBankAccount(double InitialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double InitialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String descreption) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId,double amount,String descreption) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDTO> bankAccountList();

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
