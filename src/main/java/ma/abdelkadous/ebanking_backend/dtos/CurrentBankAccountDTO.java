package ma.abdelkadous.ebanking_backend.dtos;

import lombok.Data;
import ma.abdelkadous.ebanking_backend.enums.AccountStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;


@Data
public class CurrentBankAccountDTO extends  BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private CustomerDTO customerDTO;
    private double overDraft;

}

