package ma.abdelkadous.ebanking_backend.mappers;

import lombok.Data;
import ma.abdelkadous.ebanking_backend.dtos.AccountOperationDTO;
import ma.abdelkadous.ebanking_backend.dtos.CurrentBankAccountDTO;
import ma.abdelkadous.ebanking_backend.dtos.CustomerDTO;
import ma.abdelkadous.ebanking_backend.dtos.SavingBankAccountDTO;
import ma.abdelkadous.ebanking_backend.entities.AccountOperation;
import ma.abdelkadous.ebanking_backend.entities.CurrentBankAccount;
import ma.abdelkadous.ebanking_backend.entities.Customer;
import ma.abdelkadous.ebanking_backend.entities.SavingBankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Data
public class DtoMapper {
    public CustomerDTO fromCustomer(Customer customer){
       CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
       Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingBankAccount savingBankAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingBankAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingBankAccount.getCustomer()));
        savingBankAccountDTO.setType(SavingBankAccount.class.getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingBankAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO ){
        SavingBankAccount savingBankAccount=new SavingBankAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingBankAccount);
        savingBankAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingBankAccount;
    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentBankAccount currentBankAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentBankAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentBankAccount.getCustomer()));
        currentBankAccountDTO.setType(CurrentBankAccount.class.getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentBankAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO ){
        CurrentBankAccount currentBankAccount=new CurrentBankAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentBankAccount);
        currentBankAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentBankAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }

}
