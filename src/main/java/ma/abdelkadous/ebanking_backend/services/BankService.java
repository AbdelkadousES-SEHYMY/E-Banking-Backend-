package ma.abdelkadous.ebanking_backend.services;

import lombok.AllArgsConstructor;
import ma.abdelkadous.ebanking_backend.entities.BankAccount;
import ma.abdelkadous.ebanking_backend.entities.CurrentBankAccount;
import ma.abdelkadous.ebanking_backend.entities.SavingBankAccount;
import ma.abdelkadous.ebanking_backend.repositories.BanckAcountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class BankService {
    private BanckAcountRepository banckAcountRepository;
    public void consulter(){
        BankAccount bankAccount=
                banckAcountRepository.findById("13c5e564-d9c2-474e-817e-9baa5a90830b").orElse(null);
        if(bankAccount!=null){

        }
        System.out.println("*************************");
        System.out.println(bankAccount.getId());
        System.out.println(bankAccount.getBalance());
        System.out.println(bankAccount.getAccountStatus());
        System.out.println(bankAccount.getCreatedAt());
        System.out.println(bankAccount.getCustomer().getName());
        if (bankAccount instanceof CurrentBankAccount){
            System.out.println("OverDraft ==> :"+((CurrentBankAccount) bankAccount).getOverDraft());
        } else if (bankAccount instanceof SavingBankAccount) {
            System.out.println("InterestRate ==> :"+((SavingBankAccount) bankAccount).getInterestRate());

        }
        bankAccount.getAccountOperations().forEach(accountOperation -> {
            System.out.println(accountOperation.getOperationType()+"\t"+accountOperation.getOperationDate());
        });


    };
    }

