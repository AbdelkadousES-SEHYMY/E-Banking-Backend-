package ma.abdelkadous.ebanking_backend.dtos;

import lombok.Data;
import ma.abdelkadous.ebanking_backend.enums.OperationType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private String descreption;
}
