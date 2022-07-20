package ma.abdelkadous.ebanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.abdelkadous.ebanking_backend.dtos.*;
import ma.abdelkadous.ebanking_backend.entities.*;
import ma.abdelkadous.ebanking_backend.enums.OperationType;
import ma.abdelkadous.ebanking_backend.exceptions.BalanceNotSufficentException;
import ma.abdelkadous.ebanking_backend.exceptions.BankAccountNotFoundException;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;
import ma.abdelkadous.ebanking_backend.mappers.DtoMapper;
import ma.abdelkadous.ebanking_backend.repositories.AccountOperationRepository;
import ma.abdelkadous.ebanking_backend.repositories.BanckAcountRepository;
import ma.abdelkadous.ebanking_backend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountService implements IBankAccountService {
    private CustomerRepository customerRepository;
    private BanckAcountRepository banckAcountRepository;
    private AccountOperationRepository accountOperationRepository;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName())
    private DtoMapper dtoMapper;

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double InitialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentBankAccount currentBankAccount =new CurrentBankAccount();
        currentBankAccount.setId(UUID.randomUUID().toString());
        currentBankAccount.setCreatedAt(new Date());
        currentBankAccount.setBalance(InitialBalance);
        currentBankAccount.setOverDraft(overDraft);
        currentBankAccount.setCustomer(customer);
        CurrentBankAccount savedBankAccount = banckAcountRepository.save(currentBankAccount);

        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double InitialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingBankAccount savingBankAccount =new SavingBankAccount();
        savingBankAccount.setId(UUID.randomUUID().toString());
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setBalance(InitialBalance);
        savingBankAccount.setInterestRate(interestRate);
        savingBankAccount.setCustomer(customer);
        SavingBankAccount savedBankAccount = banckAcountRepository.save(savingBankAccount);

        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }




    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=
                banckAcountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account Exception"));
        if(bankAccount instanceof SavingBankAccount){
            SavingBankAccount savingBankAccount=(SavingBankAccount) bankAccount;

            return dtoMapper.fromSavingBankAccount(savingBankAccount);
        }
        else {
            CurrentBankAccount currentBankAccount=(CurrentBankAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentBankAccount);

        }
    }

    @Override
    public void debit(String accountId, double amount, String descreption) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount=
                banckAcountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account Exception"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance not sufficent");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescreption(descreption);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        banckAcountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String descreption) throws BankAccountNotFoundException {
        BankAccount bankAccount=
                banckAcountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank Account Exception"));        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescreption(descreption);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        banckAcountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(accountIdSource,amount,"transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
      List<BankAccount> accounts= banckAcountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = accounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingBankAccount) {
                return dtoMapper.fromSavingBankAccount((SavingBankAccount) bankAccount);
            } else {
                return dtoMapper.fromCurrentBankAccount((CurrentBankAccount) bankAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDTO> accountOperationsDTO = accountOperations.stream().map(accountOperation -> dtoMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
        return accountOperationsDTO;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=banckAcountRepository.findById(accountId).orElse(null);
        if (bankAccount==null)
            throw new BankAccountNotFoundException("Account not found");

        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(accountOperation -> dtoMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
