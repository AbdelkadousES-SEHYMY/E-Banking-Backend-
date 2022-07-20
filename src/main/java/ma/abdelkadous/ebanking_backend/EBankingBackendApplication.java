package ma.abdelkadous.ebanking_backend;

import ma.abdelkadous.ebanking_backend.dtos.BankAccountDTO;
import ma.abdelkadous.ebanking_backend.dtos.CurrentBankAccountDTO;
import ma.abdelkadous.ebanking_backend.dtos.CustomerDTO;
import ma.abdelkadous.ebanking_backend.dtos.SavingBankAccountDTO;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;
import ma.abdelkadous.ebanking_backend.services.BankAccountService;
import ma.abdelkadous.ebanking_backend.services.CutomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(BankAccountService bankAccountService, CutomerService cutomerService){
{
        return args -> {
            Stream.of("Abdelkadous","Ahmed","Khadija").forEach(name->{
                CustomerDTO customerDTO=new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                cutomerService.saveCustomer(customerDTO);
            });
            cutomerService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*10000,8880, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*10000,2.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts= bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount:bankAccounts) {
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId =((SavingBankAccountDTO)  bankAccount).getId();
                    }else{
                        accountId =((CurrentBankAccountDTO)  bankAccount).getId();

                    }
                    bankAccountService.credit(accountId, 10000 + Math.random() * 1234, "Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*2000,"Debit");
                }
            }
        };
    }
    }
}
